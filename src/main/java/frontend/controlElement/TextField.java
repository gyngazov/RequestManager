package frontend.controlElement;

import backend.util.Constants;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
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

    public void setPredicate(Predicate<String> predicate) {
        this.predicate = predicate;
    }

    public void updateBackground() {
        String txt = getText();
        setBackground(isEnabled() && isEditable() ?
                txt == null || txt.isEmpty() ?
                        Constants.TEXT_FIELD_ACTIVE_BACKGROUND
                        : predicate == null ?
                        Constants.TEXT_FIELD_ACTIVE_BACKGROUND
                        : predicate.test(txt) ?
                        Constants.TEXT_FIELD_VALID_BACKGROUND
                        : Constants.TEXT_FIELD_INVALID_BACKGROUND
                : Constants.TEXT_FIELD_INACTIVE_BACKGROUND);
    }

    @Override
    public @Nullable String getText() {
        Document doc = getDocument();
        String txt;
        try {
            txt = doc.getText(0, doc.getLength());
        } catch (Exception e) {
            txt = null;
        }
        return txt;
    }

    @Override
    public void setEditable(boolean b) {
        super.setEditable(b);
        updateBackground();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            setText(null);
        }
        updateBackground();
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
