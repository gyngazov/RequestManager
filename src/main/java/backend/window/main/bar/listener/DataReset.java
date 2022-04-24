package backend.window.main.bar.listener;

import backend.window.main.form.FormData;
import backend.window.main.form.constant.DataTypeEnum;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import frontend.window.main.MainForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public final class DataReset extends DataConverter implements ActionListener {
    private final DataTypeEnum dataTypeEnum;

    public DataReset(DataTypeEnum dataTypeEnum) {
        this.dataTypeEnum = dataTypeEnum;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (dataTypeEnum == DataTypeEnum.ALL_DATA) {
            displayData(FormData.generateByDefault(EntrepreneurshipEnum.JURIDICAL_PERSON));
        } else {
            FormData data = FormData.generateByDefault((EntrepreneurshipEnum) Objects.requireNonNullElse(MainForm.getInstance().getEntrepreneurshipComboBox().getSelectedItem(),
                    EntrepreneurshipEnum.JURIDICAL_PERSON));
            switch (dataTypeEnum) {
                case ORGANIZATION_DATA -> displayOrganizationData(data);
                case APPLICANT_DATA -> displayApplicantData(data);
            }
        }
    }
}
