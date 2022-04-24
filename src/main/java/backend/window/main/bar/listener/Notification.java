package backend.window.main.bar.listener;

import frontend.controlElement.Label;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.Map;

public record Notification<T>(int numberOfOperations) {

    private @NotNull PlainDocument createModel(@NotNull Map<T, String> notifications) {
        PlainDocument plainDocument = new PlainDocument();
        try {
            for (Map.Entry<T, String> entry : notifications.entrySet()) {
                try {
                    plainDocument.insertString(plainDocument.getLength(),
                            entry.getKey() + ": " + entry.getValue() + System.lineSeparator(),
                            null);
                } catch (BadLocationException ignored) {}
            }
            plainDocument.replace(plainDocument.getLength() - System.lineSeparator().length(),
                    System.lineSeparator().length(), null, null);
        } catch (BadLocationException ignored) {}

        return plainDocument;
    }

    public void showNotificationDisplay(int notifications) {
        if (notifications == numberOfOperations) {
            new MessageDialog.Error("Ошибка при выполнении операции, повторите попытку.");
        } else if (notifications == 0) {
            new MessageDialog.Info("Операция успешно завершена!");
        } else {
            new MessageDialog.Warning("Не удалось завершить операцию! Завершено "
                    + (numberOfOperations - notifications) + " из " + numberOfOperations + ".");
        }
    }

    public void showNotificationDisplay(@NotNull Map<T, String> notificationMap) {
        int notifications = notificationMap.size();

        if (notifications == numberOfOperations) {
            new MessageDialog.Error("Ошибка при выполнении операции, повторите попытку.");
        } else if (notifications == 0) {
            new MessageDialog.Info("Операция успешно завершена!");
        } else {
            JTextArea textArea = new JTextArea(createModel(notificationMap), null, Math.min(notificationMap.size(), 10), 0);
            textArea.setEditable(false);
            textArea.setMargin(new Insets(0, 4, 0, 4));

            new MessageDialog.Warning(new JComponent[]{
                    new Label("<html><body>Не удалось завершить операцию! Завершено "
                            + (numberOfOperations - notifications) + " из " + numberOfOperations
                            + ".<br>Необработанные элементы:</body></html>"),
                    new JScrollPane(textArea)});
        }
    }
}
