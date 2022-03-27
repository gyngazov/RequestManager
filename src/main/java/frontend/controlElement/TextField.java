package frontend.controlElement;

import backend.util.Constants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Predicate;

public class TextField extends JTextField implements DocumentListener {
    private Predicate<String> predicate;

    public static final class CountryName extends TextField {

        public CountryName() {
            super();

            setEditable(false);
            setText("Россия");
        }
    }

    public static final class Division extends TextField {

        public Division() {
            super();

            setEnabled(false);
        }
    }

    public TextField(Predicate<String> predicate) {
        super();

        this.predicate = predicate;

        getDocument().addDocumentListener(this);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Constants.BORDER_COLOR),
                BorderFactory.createEmptyBorder(0, 4, 0, 4)));
        setFont(Constants.TEXT_FIELD_FONT);
        setForeground(Constants.TEXT_FIELD_FOREGROUND);
    }

    public TextField() {
        this(null);
    }

    private Color getBackground(String text) {
        return predicate == null
                ? Constants.TEXT_FIELD_ACTIVE_BACKGROUND
                : predicate.test(text) ? Constants.TEXT_FIELD_VALID_BACKGROUND : Constants.TEXT_FIELD_INVALID_BACKGROUND;
    }

    private void updateBackground(String text) {
        setBackground(isEnabled() && isEditable()
                ? text.isEmpty() ? Constants.TEXT_FIELD_ACTIVE_BACKGROUND : getBackground(text)
                : Constants.TEXT_FIELD_INACTIVE_BACKGROUND);
    }

    public void setPredicate(Predicate<String> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void setEditable(boolean b) {
        super.setEditable(b);
        if (getDocument() != null && b) updateBackground(getText());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            updateBackground(getText());
        } else {
            setText(null);
            setBackground(Constants.TEXT_FIELD_INACTIVE_BACKGROUND);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateBackground(getText());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateBackground(getText());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}