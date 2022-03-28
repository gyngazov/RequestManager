package backend.window.main.bar.listener;

import backend.exception.BadRequestException;
import backend.iEcp.JSON.JSONChange;
import backend.iEcp.JSON.JSONCreate;
import backend.iEcp.POSTRequest;
import backend.window.main.form.FormData;
import backend.window.settings.Options;
import com.google.gson.GsonBuilder;
import frontend.controlElement.Label;
import frontend.controlElement.TextField;
import frontend.window.main.MainForm;
import frontend.window.optionDialog.InputPanel;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ExportDataIEcp extends DataManipulation {
    private final TextField requestIDTextField;

    private Map<String, String> badRequestIDMap;
    private int requestIDCount;


    public ExportDataIEcp(MainForm mainForm,
                          TextField requestIDTextField) {
        super(mainForm);

        this.requestIDTextField = requestIDTextField;
    }

    private boolean selectedRequestID() {
        return !requestIDTextField.getText().isBlank();
    }

    private String generateJSONCreate(FormData data) {
        return new GsonBuilder()
                .serializeNulls()
                .create()
                .toJson(new JSONCreate(data), JSONCreate.class);
    }

    private String generateJSONChange(FormData data, int requestID) {
        return new GsonBuilder()
                .serializeNulls()
                .create()
                .toJson(new JSONChange(requestID, data), JSONChange.class);
    }

    private String getURL() {
        if (selectedRequestID()) {
            return POSTRequest.CHANGE_REQUEST;
        } else {
            return POSTRequest.CREATE_REQUEST;
        }
    }

    private int showOptionDialog(JPanel userInput) {
        String[] options = {"Создать", "Отмена"};
        return JOptionPane.showOptionDialog(null,
                new JComponent[]{new Label("Создать новую заявку?"), userInput},
                "Создание заявки",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
    }

    private void sendRequestToServer(String URL, String JSON, @Nullable String requestID) throws IOException {
        POSTRequest postRequest = new POSTRequest(URL, JSON);
        if (postRequest.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            badRequestIDMap.put(requestID, postRequest.getResponse());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FormData data = getDisplayData(true);
        if (data == null) {
            new MessageDialog.Error("В карточке организации укажите её форму.");
        } else {
            badRequestIDMap = new HashMap<>();
            requestIDCount = 0;

            String URL = getURL();
            String JSON;

            if (selectedRequestID()) {
                String[] requestIDs = requestIDTextField.getText().split("[ ,;]+");
                requestIDCount = requestIDs.length;
                for (String ID : requestIDs) {
                    try {
                        int requestID = Integer.parseInt(ID);
                        JSON = generateJSONChange(data, requestID);
                        if (Options.read().isVerifiedOrgInn()) {
                            FormData dataValidation = FormData.generateOnRequestId(requestID);
                            if (Objects.equals(dataValidation.getOrgINN(), data.getOrgINN())) {
                                sendRequestToServer(URL, JSON, ID);
                            } else {
                                badRequestIDMap.put(ID, "Заявка принадлежит другому юридическому лицу.");
                            }
                        } else {
                            sendRequestToServer(URL, JSON, ID);
                        }
                    } catch (NumberFormatException exception) {
                        badRequestIDMap.put(ID, "Некорректный номер заявки.");
                    } catch (BadRequestException exception) {
                        badRequestIDMap.put(ID, exception.getMessage());
                    } catch (SocketTimeoutException exception) {
                        badRequestIDMap.put(ID, "Время ожидания операции истекло.");
                    } catch (IOException exception) {
                        badRequestIDMap.put(ID, "Ошибка получения данных.");
                    } catch (Exception exception) {
                        badRequestIDMap.put(ID, exception.getMessage());
                    }
                }
            } else {
                InputPanel userInput = new InputPanel("Укажите количество создаваемых заявок");
                JSON = generateJSONCreate(data);
                while (true) {
                    if (showOptionDialog(userInput) == 0) {
                        try {
                            int badRequestIDCount = 0;
                            requestIDCount = Integer.parseInt(userInput.getIn().getText());
                            for (int i = 0; i < requestIDCount; i++) {
                                try {
                                    sendRequestToServer(URL, JSON, null);
                                } catch (Exception exception) {
                                    badRequestIDCount += 1;
                                }
                            }

                            if (badRequestIDCount == 0) {
                                new MessageDialog.Info("Запрос успешно обработан!");
                            } else if (requestIDCount == badRequestIDCount) {
                                new MessageDialog.Error("Ошибка при выполнении операции, повторите попытку.");
                            } else {
                                new MessageDialog.Warning("Успешно создано заявок: "
                                        + (requestIDCount - badRequestIDCount)
                                        + " из "
                                        + requestIDCount
                                        + ".");
                            }

                            break;
                        } catch (NumberFormatException exception) {
                            new MessageDialog.Error("Ошибка ввода данных, повторите ввод.");
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }
}
