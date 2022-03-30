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
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public final class ExportDataIEcp extends DataManipulation {
    private final TextField requestIDTextField;

    public ExportDataIEcp(MainForm mainForm, TextField requestIDTextField) {
        super(mainForm);

        this.requestIDTextField = requestIDTextField;
    }

    private boolean isRequestIDTextFieldFull() {
        return !requestIDTextField.getText().isBlank();
    }

    private String generateJSONChange(FormData data, int requestID) {
        return new GsonBuilder()
                .serializeNulls()
                .create()
                .toJson(new JSONChange(requestID, data), JSONChange.class);
    }

    private String generateJSONCreate(FormData data) {
        return new GsonBuilder()
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

    private void showNotification(int requestIDCount, Map<String, String> badRequestIDMap) {
        int badRequestIDCount = badRequestIDMap.size();

        if (badRequestIDCount == 0) {
            new MessageDialog.Info("Запрос успешно обработан!");
        } else {
            PlainDocument plainDocument = new PlainDocument();
            try {
                for (Map.Entry<String, String> entry : badRequestIDMap.entrySet()) {
                    StringBuilder stringBuilder = new StringBuilder(entry.getKey());
                    stringBuilder.append(": ");
                    stringBuilder.append(entry.getValue());
                    stringBuilder.append(System.lineSeparator());
                    try {
                        plainDocument.insertString(plainDocument.getLength(), stringBuilder.toString(), null);
                    } catch (BadLocationException ignored) {}
                }
                plainDocument.replace(plainDocument.getLength() - System.lineSeparator().length(),
                        System.lineSeparator().length(), null, null);
            } catch (BadLocationException ignored) {}

            JTextArea textArea = new JTextArea(plainDocument, null, Math.min(badRequestIDCount, 10), 0);
            textArea.setEditable(false);
            textArea.setMargin(new Insets(0, 4, 0, 4));
            new MessageDialog.Warning(new JComponent[]{
                    new Label("<html><body>Успешно изменено заявок: "
                            + (requestIDCount - badRequestIDCount)
                            + " из "
                            + requestIDCount + ".<br>Не удалось внести изменения в следующие заявки:</body></html>"),
                    new JScrollPane(textArea)});
        }
    }

    private void showNotification(int requestIDCount, int badRequestIDCount) {
        if (badRequestIDCount == 0) {
            new MessageDialog.Info("Запрос успешно обработан!");
        } else if (badRequestIDCount == requestIDCount) {
            new MessageDialog.Error("Ошибка при выполнении операции, повторите попытку.");
        } else {
            new MessageDialog.Warning("Успешно создано заявок: "
                    + (requestIDCount - badRequestIDCount)
                    + " из "
                    + requestIDCount + ".");
        }
    }

    private void sendChangeRequestID(FormData data) {
        String[] requestIDs = requestIDTextField.getText().split("[ ,;]+");
        Map<String, String> badRequestIDMap = new TreeMap<>();

        for (String ID : requestIDs) {
            try {
                int requestID = Integer.parseInt(ID);
                String JSON = generateJSONChange(data, requestID);
                if (Options.read().isVerifiedOrgInn()) {
                    FormData dataValidation = FormData.generateOnRequestId(requestID);
                    if (!Objects.equals(dataValidation.getOrgINN(), data.getOrgINN())) {
                        badRequestIDMap.put(ID, "Заявка принадлежит другому юридическому лицу.");
                        continue;
                    }
                }

                POSTRequest postRequest = new POSTRequest(POSTRequest.CHANGE_REQUEST, JSON);
                if (postRequest.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    badRequestIDMap.put(ID, postRequest.getResponse());
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

        showNotification(requestIDs.length, badRequestIDMap);
    }

    private void sendCreateRequestID(FormData data) {
        InputPanel userInput = new InputPanel("Укажите количество создаваемых заявок");
        String JSON = generateJSONCreate(data);
        while (true) {
            if (showOptionDialog(userInput) == 0) {
                try {
                    int requestIDCount = Integer.parseInt(userInput.getUserInput().getText());
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

                    showNotification(requestIDCount, badRequestIDCount);
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
            if (isRequestIDTextFieldFull()) {
                sendChangeRequestID(data);
            } else {
                sendCreateRequestID(data);
            }
        }
    }
}
