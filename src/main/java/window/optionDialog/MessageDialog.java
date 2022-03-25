package window.optionDialog;

import frontend.Label;

import javax.swing.*;

public class MessageDialog {

    public static class Error extends MessageDialog {

        public Error(String message) {
            super(message, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static class Info extends MessageDialog {

        public Info(String message) {
            super(message, "Сообщение", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static class Warning extends MessageDialog {

        public Warning(String message) {
            super(message, "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

    private MessageDialog(Object[] message, String title, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    private MessageDialog(String message, String title, int messageType) {
        this(new Object[]{new Label("<html>"
                        + "<body>"
                        + "<p align=\"center\">" + message + "</p>"
                        + "</body>"
                        + "</html>")},
                title,
                messageType);
    }
}