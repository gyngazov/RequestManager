package backend.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public final class Constants {
    public static final Color BORDER_COLOR = new Color(166, 166, 166);

    public static final Color LABEL_FOREGROUND = new Color(118, 118, 118);
    public static final Color TEXT_FIELD_FOREGROUND = new Color(70, 70, 70);

    public static final Color TEXT_FIELD_ACTIVE_BACKGROUND = UIManager.getColor("TextField.background");
    public static final Color TEXT_FIELD_INACTIVE_BACKGROUND = UIManager.getColor("TextField.inactiveBackground");
    public static final Color TEXT_FIELD_VALID_BACKGROUND = new Color(238, 255, 238);
    public static final Color TEXT_FIELD_INVALID_BACKGROUND = new Color(255, 238, 238);

    public static final String LABEL_FONT_NAME = "Arial";
    public static final String TEXT_FIELD_FONT_NAME = LABEL_FONT_NAME;
    public static final int LABEL_FONT_SIZE = 13;
    public static final int TEXT_FIELD_FONT_SIZE = LABEL_FONT_SIZE;
    public static final Font LABEL_FONT = new Font(LABEL_FONT_NAME, Font.BOLD, LABEL_FONT_SIZE);
    public static final Font TEXT_FIELD_FONT = new Font(TEXT_FIELD_FONT_NAME, Font.BOLD, TEXT_FIELD_FONT_SIZE);

    public static final int LABEL_WIDTH = 172;
    public static final int LABEL_HEIGHT = 18;
    public static final int TEXT_FIELD_WIDTH = LABEL_WIDTH;
    public static final int TEXT_FIELD_HEIGHT = 24;

    public static Border createDefaultTitledBorder(final String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Constants.BORDER_COLOR),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                Constants.LABEL_FONT,
                Constants.LABEL_FOREGROUND);
    }

    private Constants() {
    }
}