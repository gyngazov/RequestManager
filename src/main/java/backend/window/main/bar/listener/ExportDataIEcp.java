package backend.window.main.bar.listener;

import backend.exception.BadRequestException;
import backend.iEcp.JSON.JSONChange;
import backend.iEcp.JSON.JSONCreate;
import backend.iEcp.POSTRequest;
import backend.window.main.form.FormData;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import backend.window.main.form.constant.TypeEnum;
import backend.window.settings.Options;
import com.google.gson.GsonBuilder;
import frontend.controlElement.Label;
import frontend.controlElement.TextField;
import frontend.window.main.MainForm;
import frontend.window.optionDialog.InputPanel;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

public final class ExportDataIEcp extends DataManipulation {
    private final TextField requestIDTextField;

    public ExportDataIEcp(MainForm mainForm, TextField requestIDTextField) {
        super(mainForm);

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

    private void showNotification(@NotNull Map<Integer, String> badRequestIDMap, int requestIDCount) {
        int badRequestIDCount = badRequestIDMap.size();

        if (badRequestIDCount == requestIDCount || badRequestIDCount != 0) {
            PlainDocument plainDocument = new PlainDocument();
            try {
                for (Map.Entry<Integer, String> entry : badRequestIDMap.entrySet()) {
                    StringBuilder stringBuilder = new StringBuilder(32);
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append(": ");
                    stringBuilder.append(entry.getValue());
                    stringBuilder.append(System.lineSeparator());
                    try {
                        plainDocument.insertString(plainDocument.getLength(), stringBuilder.toString(), null);
                    } catch (BadLocationException ignored) {
                    }
                }
                if (plainDocument.getLength() == 0) {
                    plainDocument.insertString(0,
                            "Ошибка при выполнении операции, повторите попытку."
                                    + System.lineSeparator(),
                            null);
                }
                plainDocument.replace(plainDocument.getLength() - System.lineSeparator().length(),
                        System.lineSeparator().length(), null, null);
            } catch (BadLocationException ignored) {
            }

            JTextArea textArea = new JTextArea(plainDocument, null, Math.min(badRequestIDCount, 10), 0);
            textArea.setEditable(false);
            textArea.setMargin(new Insets(0, 4, 0, 4));
            new MessageDialog.Warning(new JComponent[]{
                    new Label("<html><body>Успешно изменено заявок: "
                            + (requestIDCount - badRequestIDCount)
                            + " из "
                            + requestIDCount + ".<br>Не удалось внести изменения в следующие заявки:</body></html>"),
                    new JScrollPane(textArea)});
        } else {
            new MessageDialog.Info("Запрос успешно обработан!");
        }
    }

    private void showNotification(int badRequestIDCount, int requestIDCount) {
        if (badRequestIDCount == requestIDCount) {
            new MessageDialog.Error("Ошибка при выполнении операции, повторите попытку.");
        } else if (badRequestIDCount == 0) {
            new MessageDialog.Info("Запрос успешно обработан!");
        } else {
            new MessageDialog.Warning("Успешно создано заявок: "
                    + (requestIDCount - badRequestIDCount)
                    + " из "
                    + requestIDCount + ".");
        }
    }

    private void sendChangeRequestID(FormData data, @NotNull String requestIDString) {
        int[] requestIDs = Arrays.stream(requestIDString.split("[^\\d]+"))
                .mapToInt(Integer::parseInt)
                .filter(requestID -> requestID >= DataManipulation.MIN_REQUEST_ID)
                .distinct()
                .toArray();
        Map<Integer, String> badRequestIDMap = new TreeMap<>();

        for (int requestID : requestIDs) {
            try {
                String JSON = generateJSONChange(data, requestID);
                if (Options.read().isVerifiedOrgInn()) {
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

        showNotification(badRequestIDMap, requestIDs.length);
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

                    showNotification(badRequestIDCount, requestIDCount);
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
            if (data.getTypeEnum() == TypeEnum.FID_DOC) {
                data.setSeries(TypeEnum.FID_DOC_SERIES);
                data.setNumber(TypeEnum.FID_DOC_NUMBER);
                data.setIssueId(TypeEnum.FID_DOC_ISSUE_ID);
            }

            FormData copied = FormData.copy(data);
            if (copied.getEntrepreneurshipEnum() != EntrepreneurshipEnum.JURIDICAL_PERSON) {
                copied.setOrgINN(data.getPersonINN());
            }

            String requestIDString = requestIDTextField.getText();
            if (requestIDString != null && !requestIDString.isBlank()) {
                sendChangeRequestID(copied, requestIDString);
            } else {
                sendCreateRequestID(copied);
            }
        }
    }
}
