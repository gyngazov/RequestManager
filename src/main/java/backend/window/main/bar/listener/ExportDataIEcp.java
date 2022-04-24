package backend.window.main.bar.listener;

import backend.exception.BadRequestException;
import backend.iEcp.JSON.JSONChange;
import backend.iEcp.JSON.JSONCreate;
import backend.iEcp.POSTRequest;
import backend.window.main.form.FormData;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import backend.window.main.form.constant.TypeEnum;
import backend.window.settings.SoftwareConfiguration;
import com.google.gson.GsonBuilder;
import frontend.controlElement.Label;
import frontend.controlElement.TextField;
import frontend.window.optionDialog.InputPanel;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

public final class ExportDataIEcp extends DataConverter implements ActionListener {
    private final TextField requestIDTextField;

    public ExportDataIEcp(TextField requestIDTextField) {
        this.requestIDTextField = requestIDTextField;
    }

    private String generateJSONChange(FormData data, int requestID) {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create()
                .toJson(new JSONChange(data, requestID), JSONChange.class);
    }

    private String generateJSONCreate(FormData data) {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create()
                .toJson(new JSONCreate(data), JSONCreate.class);
    }

    private int showOptionDialog(JPanel userInput) {
        String[] options = {"Создать", "Отмена"};
        return JOptionPane.showOptionDialog(null,
                new JComponent[]{new Label("Создать новую заявку?"), userInput},
                "Создание заявки",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
    }

    private void sendChangeRequestID(FormData data, @NotNull String requestIDString) {
        int[] requestIDs = Arrays.stream(requestIDString.split("[^\\d]+"))
                .mapToInt(Integer::parseInt)
                .filter(requestID -> requestID >= POSTRequest.MIN_REQUEST_ID)
                .distinct()
                .toArray();
        Map<Integer, String> badRequestIDMap = new TreeMap<>();

        for (int requestID : requestIDs) {
            try {
                String JSON = generateJSONChange(data, requestID);
                if (SoftwareConfiguration.getInstance().isVerifiedOrgInn()) {
                    FormData dataValidation = FormData.generateOnRequestID(requestID);
                    if (!Objects.equals(dataValidation.getOrgINN(), data.getOrgINN())) {
                        badRequestIDMap.put(requestID, "Заявка принадлежит другому юридическому лицу.");
                        continue;
                    }
                }

                POSTRequest postRequest = new POSTRequest(POSTRequest.CHANGE_REQUEST, JSON);
                if (postRequest.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    badRequestIDMap.put(requestID, postRequest.getResponse());
                }
            } catch (BadRequestException exception) {
                badRequestIDMap.put(requestID, exception.getMessage());
            } catch (SocketTimeoutException exception) {
                badRequestIDMap.put(requestID, "Время ожидания операции истекло.");
            } catch (IOException exception) {
                badRequestIDMap.put(requestID, "Ошибка получения данных.");
            } catch (Exception exception) {
                badRequestIDMap.put(requestID, exception.getMessage());
            }
        }

        new Notification<Integer>(requestIDs.length).showNotificationDisplay(badRequestIDMap);
    }

    private void sendCreateRequestID(FormData data) {
        InputPanel userInput = new InputPanel("Укажите количество создаваемых заявок");
        String JSON = generateJSONCreate(data);
        while (true) {
            if (showOptionDialog(userInput) == 0) {
                try {
                    int requestIDCount = Integer.parseInt(Objects.requireNonNullElse(userInput.getUserInput().getText(), "0"));
                    int badRequestIDCount = 0;

                    for (int i = 0; i < requestIDCount; i++) {
                        try {
                            POSTRequest postRequest = new POSTRequest(POSTRequest.CREATE_REQUEST, JSON);
                            if (postRequest.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                                throw new BadRequestException();
                            }
                        } catch (Exception exception) {
                            badRequestIDCount += 1;
                        }
                    }

                    new Notification<Integer>(requestIDCount).showNotificationDisplay(badRequestIDCount);
                    break;
                } catch (NumberFormatException exception) {
                    new MessageDialog.Error("Ошибка ввода данных, повторите ввод.");
                }
            } else {
                return;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FormData data = getDisplayData(true);
        if (data == null) {
            new MessageDialog.Error("В карточке организации укажите её форму и повторите попытку.");
        } else {
            FormData clone = data.clone(false);
            if (clone.getEntrepreneurshipEnum() != EntrepreneurshipEnum.JURIDICAL_PERSON) {
                clone.setOrgINN(data.getPersonINN());
            }
            if (clone.getTypeEnum() == TypeEnum.FID_DOC) {
                clone.setSeries(TypeEnum.FID_DOC_SERIES);
                clone.setNumber(TypeEnum.FID_DOC_NUMBER);
                clone.setIssueId(TypeEnum.FID_DOC_ISSUE_ID);
            }

            String requestIDString = requestIDTextField.getText();
            if (requestIDString != null && !requestIDString.isBlank()) {
                sendChangeRequestID(clone, requestIDString);
            } else {
                sendCreateRequestID(clone);
            }
        }
    }
}
