package frontend.window.optionDialog;

import backend.util.Constants;
import backend.util.Validatable;
import backend.window.settings.SoftwareConfiguration;
import frontend.controlElement.CheckBox;
import frontend.controlElement.Label;
import frontend.controlElement.PasswordField;
import frontend.controlElement.TextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public final class SoftwareConfigurationDialog extends JDialog {
    private static SoftwareConfigurationDialog softwareConfigurationDialog;

    private final TextField currentDirectoryTextField = new TextField(Validatable::isCorrectCurrentDirectory);
    private final TextField loginTextField = new TextField(Validatable::isCorrectEmailAddress);
    private final PasswordField passwordPasswordField = new PasswordField(Validatable::isCorrectPassword);
    private final CheckBox verifiedOrgInnCheckBox = new CheckBox("Проверять ИНН организации при внесении изменений в заявку", true);

    private final JButton okButton = new JButton("ОК");
    private final JButton cancelButton = new JButton("Отмена");

    private SoftwareConfigurationDialog() {
        setListener();
        setLayout();
        readOptions();
    }

    private void setListener() {
        okButton.addActionListener(e -> {
            try {
                SoftwareConfiguration softwareConfiguration = new SoftwareConfiguration.Builder()
                        .setCurrentDirectory(currentDirectoryTextField.getText())
                        .setLogin(loginTextField.getText())
                        .setPassword(passwordPasswordField.getPassword())
                        .setVerifiedOrgInn(verifiedOrgInnCheckBox.isSelected())
                        .build();
                softwareConfiguration.save();
                dispose();
            } catch (IllegalArgumentException exception) {
                new MessageDialog.Error(exception.getMessage());
            } catch (IOException exception) {
                new MessageDialog.Error("Ошибка при сохранении настроек программы.");
            }
        });
        cancelButton.addActionListener(e -> {
            readOptions();
            dispose();
        });
    }

    private @NotNull JPanel createMainPanel() {
        Label currentDirectoryLabel = new Label("Рабочий каталог:");
        Label loginLabel = new Label("Логин:");
        Label passwordLabel = new Label("Пароль:");

        JPanel mainPanel = new JPanel();

        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(currentDirectoryLabel)
                                .addComponent(loginLabel)
                                .addComponent(passwordLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(currentDirectoryTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                .addComponent(loginTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                .addComponent(passwordPasswordField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)))
                .addComponent(verifiedOrgInnCheckBox));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(currentDirectoryLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(currentDirectoryTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(loginLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(loginTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(passwordLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(passwordPasswordField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addComponent(verifiedOrgInnCheckBox, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT));

        return mainPanel;
    }

    private @NotNull JPanel createButtonBar() {
        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
        grid.add(okButton);
        grid.add(cancelButton);

        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flow.add(grid);

        return flow;
    }

    private void setLayout() {
        setLayout(new BorderLayout());
        add(createMainPanel(), BorderLayout.CENTER);
        add(createButtonBar(), BorderLayout.SOUTH);
    }

    private void readOptions() {
        SoftwareConfiguration softwareConfiguration = SoftwareConfiguration.getInstance();
        currentDirectoryTextField.setText(softwareConfiguration.getCurrentDirectory());
        loginTextField.setText(softwareConfiguration.getLogin());
        passwordPasswordField.setText(softwareConfiguration.getPassword());
        verifiedOrgInnCheckBox.setSelected(softwareConfiguration.isVerifiedOrgInn());
    }

    public void buildFrame(Component parentComponent) {
        setModal(true);
        pack();
        setMinimumSize(new Dimension(getSize().width, getSize().height));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Настройки");
        setResizable(true);
        setLocationRelativeTo(parentComponent);
        setVisible(true);
    }

    public static SoftwareConfigurationDialog getInstance() {
        if (softwareConfigurationDialog == null) {
            softwareConfigurationDialog = new SoftwareConfigurationDialog();
        }
        return softwareConfigurationDialog;
    }
}
