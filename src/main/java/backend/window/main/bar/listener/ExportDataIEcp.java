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

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ExportDataIEcp extends DataManipulation {
    private final MainForm mainForm;
    private final TextField requestIDTextField;

    private Map<String, String> badRequestIDMap;
    private int requestIDCount;


    public ExportDataIEcp(MainForm mainForm,
                          TextField requestIDTextField) {
        super(mainForm);

        this.mainForm = mainForm;
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
        return JOptionPane.showOptionDialog(mainForm,
                new JComponent[]{new Label("Создать новую заявку?"), userInput},
                "Создание заявки",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
    }

    private void sendRequestToServer(String URL, String JSON, String requestID) throws IOException {
        POSTRequest postRequest = new POSTRequest(URL, JSON);
        if (postRequest.getResponseCode() != HttpsURLConnection.HTTP_OK) {

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
                                badRequestIDMap.put(ID, "Некорректный номер заявки");
                            }
                        } else {
                            POSTRequest postRequest = new POSTRequest(URL, JSON);
                            if (postRequest.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                                // TODO
                            }
                        }
                    } catch (NumberFormatException exception) {
                        badRequestIDMap.put(ID, "Некорректный номер заявки");
                    } catch (BadRequestException exception) {
                        // TODO 2
                    } catch (SocketTimeoutException exception) {
                        badRequestIDMap.put(ID, "Ошибка получения данных");
                    } catch (IOException exception) {
                        // TODO 4
                    } catch (Exception exception) {
                        // TODO 5
                    }
                }
            } else {
                InputPanel userInput = new InputPanel("Укажите количество создаваемых заявок");
                JSON = generateJSONCreate(data);
                if (showOptionDialog(userInput) == 0) {
                    requestIDCount = Integer.parseInt(userInput.getIn().getText());
                    for (int i = 1; i <= requestIDCount; i++) {
                        try {
                            POSTRequest postRequest = new POSTRequest(URL, JSON);
                            if (postRequest.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                                // TODO
                            }
                        } catch (BadRequestException exception) {
                            new MessageDialog.Error(exception.getMessage());
                        } catch (SocketTimeoutException exception) {
                            new MessageDialog.Error("Время ожидания операции истекло, попробуйте повторить запрос позже.");
                        } catch (IOException exception) {
                            new MessageDialog.Error("Ошибка получения данных, попробуйте повторить запрос позже.");
                        } catch (Exception exception) {
                            new MessageDialog.Error(exception.getMessage());
                        }
                    }
                } else return;
            }

            if (badRequestIDMap.size() == 0) {
                new MessageDialog.Info("Успешно!");
            } else {
                new MessageDialog.Warning("Успешно создано " + (requestIDCount - badRequestIDMap.size()) + " из " + requestIDCount + ".");
            }

//            try {
//            } catch (NumberFormatException exception) {
//                new MessageDialog.Error("Проверьте правильность написания номера заявки.");
//            } catch (BadRequestException exception) {
//                new MessageDialog.Error(exception.getMessage());
//            } catch (SocketTimeoutException exception) {
//                new MessageDialog.Error("Время ожидания операции истекло, попробуйте повторить запрос позже.");
//            } catch (IOException exception) {
//                new MessageDialog.Error("Ошибка получения данных, попробуйте повторить запрос позже.");
//            } catch (Exception exception) {
//                new MessageDialog.Error(exception.getMessage());
//            }
        }
    }
}
