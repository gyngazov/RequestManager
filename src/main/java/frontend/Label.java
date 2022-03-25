package frontend;

import backend.util.Constants;

import javax.swing.*;

public final class Label extends JLabel {

    public Label(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);

        setFont(Constants.LABEL_FONT);
        setForeground(Constants.LABEL_FOREGROUND);
    }

    public Label(String text, int horizontalAlignment) {
        this(text, null, horizontalAlignment);
    }

    public Label(String text) {
        this(text, null, SwingConstants.LEADING);
    }
}
