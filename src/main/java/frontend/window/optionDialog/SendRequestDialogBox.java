package frontend.window.optionDialog;

import backend.iEcp.JSON.JSONAttachFile;
import backend.iEcp.POSTRequest;
import backend.util.Constants;
import backend.window.main.bar.listener.ExportAttachedRequestIEcp;
import com.google.gson.Gson;
import frontend.controlElement.Label;
import frontend.controlElement.TextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

public final class SendRequestDialogBox extends JDialog {

    private static class ManualMode extends JPanel {
        private final TextField requestIDTextField = new TextField();
        private final JTextArea requestContentTextArea = new JTextArea(12, 0);
        private final JButton submitButton = new JButton("Отправить");

        ManualMode() {
            setListener();
            setLayout();
        }

        private int showOptionDialog() {
            String[] options = {"Да", "Отмена"};
            return JOptionPane.showOptionDialog(null, "Прикрепить запрос к указанной заявке?", "Ввод данных",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[1]);
        }

        private void setListener() {
            submitButton.addActionListener(e -> {
                try {
                    int requestID = Integer.parseInt(requestIDTextField.getText()); // Проверка на null обеспечена parseInt()
                    String requestContent = requestContentTextArea.getText();

                    String json = new Gson().toJson(new JSONAttachFile("request.req", requestContent, 0, requestID));
                    if (showOptionDialog() == 0) {
                        try {
                            POSTRequest request = new POSTRequest(POSTRequest.ATTACH_FILE, json);
                            if (request.getResponseCode() != HttpURLConnection.HTTP_OK) {
                                new MessageDialog.Error(request.getResponse());
                            }
                        } catch (SocketTimeoutException exception) {
                            new MessageDialog.Error("Время ожидания операции истекло, попробуйте повторить запрос позже.");
                        } catch (IOException exception) {
                            new MessageDialog.Error("Ошибка получения данных, попробуйте повторить запрос позже.");
                        } catch (Exception exception) {
                            new MessageDialog.Error(exception.getMessage());
                        }

                        requestIDTextField.setText(null);
                        requestContentTextArea.setText(null);
                    }
                } catch (NumberFormatException exception) {
                    new MessageDialog.Error("Некорректный ввод номера заявки.");
                }
            });
        }

        private @NotNull JPanel createMainPanel() {
            Label requestIDLabel = new Label("№ заявки");
            Label requestContentLabel = new Label("Содержимое запроса (*.req), закодированное в base64");

            JScrollPane scrollPane = new JScrollPane(requestContentTextArea);

            JPanel mainPanel = new JPanel();

            GroupLayout layout = new GroupLayout(mainPanel);
            mainPanel.setLayout(layout);

            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(requestIDLabel)
                    .addComponent(requestIDTextField)
                    .addComponent(requestContentLabel)
                    .addComponent(scrollPane));

            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addComponent(requestIDLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                    .addComponent(requestIDTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                    .addComponent(requestContentLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                    .addComponent(scrollPane));

            return mainPanel;
        }

        private @NotNull JPanel createButtonBar() {
            JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            flow.add(submitButton);

            return flow;
        }

        private void setLayout() {
            setLayout(new BorderLayout());
            add(createMainPanel(), BorderLayout.CENTER);
            add(createButtonBar(), BorderLayout.SOUTH);
        }
    }

    private static class AutoMode extends JPanel {
        private final JButton submitButton = new JButton("Выбрать папку с запросами");

        AutoMode() {
            setListener();
            setLayout();
        }

        private @NotNull JPanel createMainPanel() {
            Label info = new Label("<html>" +
                    "<body>" +
                    "<p align=\"justify\">" +
                    "Для автоматической отправки запросов на сертификат в УЦ \"Основание\" требуется:" +
                    "<ul>" +
                    "<li>используя фильтр заявок в правой части главного экрана, отфильтровать заявки по требуемым параметрам,</li>" +
                    "<li>выбрать в таблице номера заявок посредством комбинаций клавиш CTRL + ЛКМ, или SHIFT + ЛКМ, или CTRL + А,</li>" +
                    "<li>указать путь к директории с запросами.</li>" +
                    "</ul>" +
                    "По завершении работы программы будет отображена информация о результатах выполнения." +
                    "</p>" +
                    "</body>" +
                    "</html>");

            JPanel mainPanel = new JPanel();

            GroupLayout layout = new GroupLayout(mainPanel);
            mainPanel.setLayout(layout);

            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(info, 598, 598, Integer.MAX_VALUE));

            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addComponent(info));

            return mainPanel;
        }

        private @NotNull JPanel createButtonBar() {
            JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            flow.add(submitButton);

            return flow;
        }

        private void setLayout() {
            setLayout(new BorderLayout());
            add(createMainPanel(), BorderLayout.CENTER);
            add(createButtonBar(), BorderLayout.SOUTH);
        }

        private void setListener() {
            submitButton.addActionListener(new ExportAttachedRequestIEcp());
        }

    }

    public SendRequestDialogBox() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.add("Автоматический режим", new AutoMode());
        tabbedPane.add("Ручной режим", new ManualMode());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public void buildFrame(Component parentComponent) {
        setModal(true);
        pack();
        setMinimumSize(new Dimension(getSize().width, getSize().height));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Отправка запроса");
        setResizable(true);
        setLocationRelativeTo(parentComponent);
        setVisible(true);
    }
}
