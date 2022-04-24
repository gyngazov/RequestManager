package backend.window.main.filter.listener;

import backend.window.main.filter.constant.StatusEnum;
import org.jetbrains.annotations.NotNull;
import frontend.window.main.filter.FilterOptions;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public record RequestListener(FilterOptions filterOptions) implements DocumentListener {

    private void setEditable(@NotNull DocumentEvent e) {
        if (e.getDocument().getLength() == 0) {
            filterOptions.getCreationDateTextField().setEditable(true);
            filterOptions.getStatusComboBox().setEnabled(true);
            filterOptions.getCommonNameTextField().setEditable(true);
            filterOptions.getOrgINNTextField().setEditable(true);
            filterOptions.getLastNameTextField().setEditable(true);
            filterOptions.getSNILSTextField().setEditable(true);
        } else {
            filterOptions.getCreationDateTextField().setEditable(false);
            filterOptions.getStatusComboBox().setEnabled(false);
            filterOptions.getCommonNameTextField().setEditable(false);
            filterOptions.getOrgINNTextField().setEditable(false);
            filterOptions.getLastNameTextField().setEditable(false);
            filterOptions.getSNILSTextField().setEditable(false);

            filterOptions.getCreationDateTextField().setText("");
            filterOptions.getStatusComboBox().setSelectedItem(StatusEnum.WITHOUT_STATUS);
            filterOptions.getCommonNameTextField().setText("");
            filterOptions.getOrgINNTextField().setText("");
            filterOptions.getLastNameTextField().setText("");
            filterOptions.getSNILSTextField().setText("");
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        setEditable(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        setEditable(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
