package backend.window.main.filter.listener;

import backend.window.main.filter.constant.StatusEnum;
import org.jetbrains.annotations.NotNull;
import window.main.filter.Options;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public record RequestListener(Options options) implements DocumentListener {

    private void setEditable(@NotNull DocumentEvent e) {
        if (e.getDocument().getLength() == 0) {
            options.getCreationDateTextField().setEditable(true);
            options.getStatusComboBox().setEnabled(true);
            options.getCommonNameTextField().setEditable(true);
            options.getOrgINNTextField().setEditable(true);
            options.getLastNameTextField().setEditable(true);
            options.getSNILSTextField().setEditable(true);
        } else {
            options.getCreationDateTextField().setEditable(false);
            options.getStatusComboBox().setEnabled(false);
            options.getCommonNameTextField().setEditable(false);
            options.getOrgINNTextField().setEditable(false);
            options.getLastNameTextField().setEditable(false);
            options.getSNILSTextField().setEditable(false);

            options.getCreationDateTextField().setText("");
            options.getStatusComboBox().setSelectedItem(StatusEnum.WITHOUT_STATUS);
            options.getCommonNameTextField().setText("");
            options.getOrgINNTextField().setText("");
            options.getLastNameTextField().setText("");
            options.getSNILSTextField().setText("");
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
