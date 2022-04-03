package backend.window.main.form.listener;

import backend.util.Validation;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import frontend.controlElement.ComboBox;
import frontend.window.main.MainForm;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public record EntrepreneurshipListener(MainForm mainForm) implements ItemListener {

    private void setEnabledTextField(boolean enabled) {
        mainForm.getHeadApplicantCheckBox().setSelected(enabled && mainForm.getHeadApplicantCheckBox().isSelected());
        mainForm.getHeadApplicantCheckBox().setEnabled(enabled);

        mainForm.getCommonNameTextField().setEnabled(enabled);
        mainForm.getDepartmentTextField().setEnabled(enabled);
        mainForm.getKPPTextField().setEnabled(enabled);
        mainForm.getOrgINNTextField().setEnabled(enabled);
        mainForm.getIndexTextField().setEnabled(enabled);
        mainForm.getStreetAddressTextField().setEnabled(enabled);
        mainForm.getHeadLastNameTextField().setEnabled(enabled);
        mainForm.getHeadFirstNameTextField().setEnabled(enabled);
        mainForm.getHeadMiddleNameTextField().setEnabled(enabled);
        mainForm.getHeadPersonINNTextField().setEnabled(enabled);
        mainForm.getHeadTitleTextField().setEnabled(enabled);

        mainForm.getTitleTextField().setEnabled(enabled);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            final Object source = e.getSource();
            final ComboBox<?> entrepreneurshipComboBox;
            if (source instanceof ComboBox) {
                entrepreneurshipComboBox = (ComboBox<?>) source;
                Object value = entrepreneurshipComboBox.getSelectedItem();
                if (value instanceof EntrepreneurshipEnum) {
                    switch ((EntrepreneurshipEnum) value) {
                        case JURIDICAL_PERSON -> {
                            setEnabledTextField(true);
                            mainForm.getOGRNTextField().setPredicate(Validation::isCorrectOGRN);
                            mainForm.getOGRNTextField().setEnabled(true);
                            mainForm.getOrgPhoneTextField().setEnabled(true);
                            mainForm.getOGRNLabel().setText("ОГРН");
                        }
                        case SOLE_PROPRIETOR -> {
                            setEnabledTextField(false);
                            mainForm.getOGRNTextField().setPredicate(Validation::isCorrectOGRNIP);
                            mainForm.getOGRNTextField().setEnabled(true);
                            mainForm.getOrgPhoneTextField().setEnabled(true);
                            mainForm.getOGRNLabel().setText("ОГРНИП");
                        }
                        case NATURAL_PERSON -> {
                            setEnabledTextField(false);
                            mainForm.getOGRNTextField().setPredicate(Validation::isCorrectOGRN);
                            mainForm.getOGRNTextField().setEnabled(false);
                            mainForm.getOrgPhoneTextField().setEnabled(false);
                            mainForm.getOGRNLabel().setText("ОГРН");
                        }
                    }
                }
            }
        }
    }
}
