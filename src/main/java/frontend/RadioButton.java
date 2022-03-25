package frontend;

import javax.swing.*;

public final class RadioButton extends JRadioButton {

    public RadioButton(String text, boolean selected) {
        super(text, selected);

        setContentAreaFilled(false);
        setFocusPainted(false);

        setBorder(null);
    }
}