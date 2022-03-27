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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ExportDataIEcp extends DataManipulation {
    private final MainForm mainForm;
    private final TextField requestID;

    private final Map<Integer, String> badRequestIDMap = new HashMap<>();
    private int requestIDCount;


    public ExportDataIEcp(MainForm mainForm, TextField requestID) {
        super(mainForm);

        this.mainForm = mainForm;
        this.requestID = requestID;
    }

    private boolean selectedRequestID() {
        return !requestID.getText().isBlank();
    }

    private String generateJSONCreate(FormData data) throws NumberFormatException {
        return new GsonBuilder()
                .serializeNulls()
                .create()
                .toJson(new JSONCreate(data), JSONCreate.class);
    }

    private String generateJSONChange(FormData data, String requestID) throws NumberFormatException {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            final FormData data = getDisplayData();
            if (data == null) throw new Exception("Укажите форму организации бизнеса.");

            String URL = getURL();
            String JSON;

            if (selectedRequestID()) {
                String[] requestIDs = requestID.getText().split("[ ,;]+");
                requestIDCount = requestIDs.length;
                for (String requestID : requestIDs) {
                    JSON = generateJSONChange(data, requestID);
                    FormData dataValidation = FormData.generateOnRequestId(requestID);
                    if (Options.read().isVerifiedOrgInn()) {
                        if (Objects.equals(dataValidation.getOrgINN(), data.getOrgINN())) {
                            // TODO: send to server
                        } else {
                            // TODO: mark an error "Указанная заявка принадлежит другому лицу."
                            break;
                        }
                    } else {
                        // TODO: send to server
                    }
                }
            } else {
                InputPanel userInput = new InputPanel("Укажите количество создаваемых заявок");
                JSON = generateJSONCreate(data);
                if (showOptionDialog(userInput) == 0) {
                    requestIDCount = Integer.parseInt(userInput.getIn().getText());
                    for (int i = 1; i <= requestIDCount; i++) {
                        // TODO: send to server
                    }
                } else return;
            }

            if (badRequestIDMap.size() == 0) {
                new MessageDialog.Info("Успешно!");
            } else {
                new MessageDialog.Warning("Успешно создано " + (requestIDCount - badRequestIDMap.size()) + " из " + requestIDCount + ".");
            }

        } catch (NumberFormatException exception) {
            new MessageDialog.Error("Проверьте правильность написания номера заявки.");
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
}
