package backend.window.main.bar.listener;

import backend.exception.BadRequestException;
import backend.window.main.form.FormData;
import backend.window.main.form.constant.DataTypeEnum;
import frontend.controlElement.TextField;
import frontend.window.main.MainForm;
import frontend.window.optionDialog.MessageDialog;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.SocketTimeoutException;

public final class ImportDataIEcp extends DataManipulation {
    private final TextField requestIDTextField;
    private final DataTypeEnum dataTypeEnum;

    public ImportDataIEcp(MainForm mainForm,
                          TextField requestIDTextField,
                          DataTypeEnum dataTypeEnum) {
        super(mainForm);

        this.requestIDTextField = requestIDTextField;
        this.dataTypeEnum = dataTypeEnum;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            FormData data = FormData.generateOnRequestId(Integer.parseInt(requestIDTextField.getText()));
            switch (dataTypeEnum) {
                case ORGANIZATION_DATA -> displayOrganizationData(data);
                case APPLICANT_DATA -> displayApplicantData(data);
                case ALL_DATA -> displayData(data);
            }
            new MessageDialog.Info("Запрос успешно обработан!");
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
