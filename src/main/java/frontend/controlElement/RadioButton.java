package frontend.controlElement;

import backend.util.Constants;

import javax.swing.*;

public final class RadioButton extends JRadioButton {

    public RadioButton(String text, boolean selected) {
        super(text, selected);

        setFocusPainted(false);

        setBorder(null);
        setFont(Constants.LABEL_FONT);
        setForeground(Constants.LABEL_FOREGROUND);

        setEnabled(true);
    }
}
