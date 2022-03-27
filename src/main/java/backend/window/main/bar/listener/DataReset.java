package backend.window.main.bar.listener;

import backend.window.main.form.FormData;
import backend.window.main.form.constant.DataTypeEnum;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import frontend.window.main.MainForm;

import java.awt.event.ActionEvent;
import java.util.Objects;

public final class DataReset extends DataManipulation {
    private final MainForm mainForm;
    private final DataTypeEnum dataTypeEnum;

    public DataReset(MainForm mainForm, DataTypeEnum dataTypeEnum) {
        super(mainForm);
        this.mainForm = mainForm;
        this.dataTypeEnum = dataTypeEnum;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FormData data = FormData.generateByDefault((EntrepreneurshipEnum) Objects.requireNonNullElse(mainForm.getEntrepreneurshipComboBox().getSelectedItem(), EntrepreneurshipEnum.JURIDICAL_PERSON));
        switch (dataTypeEnum) {
            case ORGANIZATION_DATA -> displayOrganizationData(data);
            case APPLICANT_DATA -> displayApplicantData(data);
            case ALL_DATA -> displayData(FormData.generateByDefault(EntrepreneurshipEnum.JURIDICAL_PERSON));
        }
    }
}
