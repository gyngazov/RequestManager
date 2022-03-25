package frontend;

import javax.swing.*;
import java.awt.*;

public final class ButtonToolBar extends JButton {

    public ButtonToolBar(Icon icon) {
        super(icon);

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
    }
}