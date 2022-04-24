package frontend.controlElement;

import backend.util.Constants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Predicate;

public class PasswordField extends JPasswordField implements DocumentListener {
    private Predicate<String> predicate;

    public PasswordField(Predicate<String> predicate) {
        this.predicate = predicate;

        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Constants.BORDER_COLOR),
                BorderFactory.createEmptyBorder(0, 4, 0, 4)));
        setFont(Constants.TEXT_FIELD_FONT);
        setForeground(Constants.TEXT_FIELD_FOREGROUND);

        getDocument().addDocumentListener(this);
    }

    public PasswordField() {
        this(null);
    }

    public void setPredicate(Predicate<String> predicate) {
        this.predicate = predicate;
    }

    public void updateBackground() {
        char[] chars = getPassword();
        setBackground(isEnabled() && isEditable() ?
                chars == null || chars.length == 0 ?
                        Constants.TEXT_FIELD_ACTIVE_BACKGROUND
                        : predicate == null ?
                        Constants.TEXT_FIELD_ACTIVE_BACKGROUND
                        : predicate.test(String.valueOf(chars)) ?
                        Constants.TEXT_FIELD_VALID_BACKGROUND
                        : Constants.TEXT_FIELD_INVALID_BACKGROUND
                : Constants.TEXT_FIELD_INACTIVE_BACKGROUND);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateBackground();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateBackground();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
