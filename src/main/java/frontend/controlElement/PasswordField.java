package frontend.controlElement;

import backend.util.Constants;
import backend.util.Validation;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Predicate;

public final class PasswordField extends JPasswordField implements DocumentListener, Predicate<String> {

    public PasswordField() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Constants.BORDER_COLOR),
                BorderFactory.createEmptyBorder(0, 4, 0, 4)));
        setFont(Constants.TEXT_FIELD_FONT);
        setForeground(Constants.TEXT_FIELD_FOREGROUND);
    }

    public void setAutoBackground(String t) {
        if (isEnabled() && isEditable()) {
            if (getPassword().length == 0) {
                setBackground(Constants.TEXT_FIELD_ACTIVE_BACKGROUND);
            } else if (test(t)) {
                setBackground(Constants.TEXT_FIELD_VALID_BACKGROUND);
            } else {
                setBackground(Constants.TEXT_FIELD_INVALID_BACKGROUND);
            }
        } else {
            setBackground(Constants.TEXT_FIELD_INACTIVE_BACKGROUND);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        setAutoBackground(String.valueOf(getPassword()));
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        setAutoBackground(String.valueOf(getPassword()));
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    @Override
    public boolean test(String t) {
        return Validation.isCorrectPassword(t);
    }
}