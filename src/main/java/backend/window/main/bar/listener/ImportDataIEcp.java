package backend.window.main.bar.listener;

import backend.exception.BadRequestException;
import backend.window.main.form.FormData;
import backend.window.main.form.constant.DataTypeEnum;
import frontend.controlElement.TextField;
import frontend.window.main.MainForm;
import frontend.window.optionDialog.InputPanel;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;

public final class ImportDataIEcp extends DataManipulation {
    private final MainForm mainForm;
    private final TextField requestIDTextField;
    private final DataTypeEnum dataTypeEnum;

    public ImportDataIEcp(@NotNull MainForm mainForm,
                          @Nullable TextField requestIDTextField,
                          @NotNull DataTypeEnum dataTypeEnum) {
        super(mainForm);

        this.mainForm = mainForm;
        this.requestIDTextField = requestIDTextField;
        this.dataTypeEnum = dataTypeEnum;
    }

    private int showOptionDialog(JPanel userInput) {
        String[] options = {"Заполнить", "Отмена"};
        return JOptionPane.showOptionDialog(null, userInput, "Ввод данных",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
    }

    private @Nullable String getRequestID(ActionEvent e) {
        String requestID;

        if (requestIDTextField == null) {
            String title = "Введите № заявки";
            Object object = e.getSource();
            if (object instanceof AbstractButton button) {
                title = Objects.requireNonNullElse(button.getText().replace("...", ""), title);
            }

            InputPanel inputPanel = new InputPanel(title);
            if (showOptionDialog(inputPanel) == 0) {
                requestID = inputPanel.getUserInput().getText();
            } else {
                return null;
            }
        } else {
            requestID = requestIDTextField.getText();
        }

        return requestID;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String requestID = getRequestID(e);
        if (requestID == null) return;

        mainForm.getHeadApplicantCheckBox().setSelected(false);

        try {
            FormData data = FormData.generateOnRequestId(Integer.parseInt(requestID));
            switch (dataTypeEnum) {
                case ORGANIZATION_DATA -> displayOrganizationData(data);
                case APPLICANT_DATA -> displayApplicantData(data);
                case ALL_DATA -> displayData(data);
            }
            new MessageDialog.Info("Запрос успешно обработан!");
        } catch (NumberFormatException exception) {
            new MessageDialog.Error("Некорректный ввод номера заявки.");
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
