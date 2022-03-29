package frontend.window.optionDialog;

import frontend.controlElement.Label;

import javax.swing.*;
import java.awt.*;

public class MessageDialog {

    public static class Error extends MessageDialog {

        public Error(Object message) {
            super(message, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }

        public Error(String message) {
            this(new Label(message));
        }
    }

    public static class Info extends MessageDialog {

        public Info(Object message) {
            super(message, "Сообщение", JOptionPane.INFORMATION_MESSAGE);
        }

        public Info(String message) {
            this(new Label(message));
        }
    }

    public static class Warning extends MessageDialog {

        public Warning(Object message) {
            super(message, "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }

        public Warning(String message) {
            this(new Label(message));
        }
    }

    public MessageDialog(Component parentComponent, Object message, String title, int messageType, Icon icon) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType, icon);
    }

    public MessageDialog(Object message, String title, int messageType) {
        this(null, message, title, messageType, null);
    }
}
