package frontend.window.optionDialog;

import backend.util.Constants;
import backend.util.Validation;
import backend.window.settings.Options;
import frontend.controlElement.CheckBox;
import frontend.controlElement.Label;
import frontend.controlElement.PasswordField;
import frontend.controlElement.TextField;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public final class Setting extends JDialog {
    private final TextField currentDirectoryTextField = new TextField(Validation::isCorrectCurrentDirectory);
    private final TextField loginTextField = new TextField(Validation::isCorrectEmailAddress);
    private final PasswordField passwordPasswordField = new PasswordField();
    private final CheckBox verifiedOrgInnCheckBox = new CheckBox("Проверять ИНН организации при внесении изменений в заявку", true);

    private final JButton okButton = new JButton("ОК");
    private final JButton cancelButton = new JButton("Отмена");

    public Setting() {
        setListener();
        setLayout();
        readOptions();
        buildFrame();
    }

    private void setListener() {
        passwordPasswordField.getDocument().addDocumentListener(passwordPasswordField);
        okButton.addActionListener(e -> {
            Options options = new Options();
            options.setCurrentDirectory(currentDirectoryTextField.getText());
            options.setLogin(loginTextField.getText());
            options.setPassword(String.valueOf(passwordPasswordField.getPassword()));
            options.setVerifiedOrgInn(verifiedOrgInnCheckBox.isSelected());
            try {
                options.save();
                this.dispose();
            } catch (IOException exception) {
                new MessageDialog.Error("Ошибка при сохранении настроек программы.");
            }
        });
        cancelButton.addActionListener(e -> dispose());
    }

    private void setLayout() {
        JPanel form = new JPanel();

        Label currentDirectoryLabel = new Label("Рабочий каталог:");
        Label loginLabel = new Label("Логин:");
        Label passwordLabel = new Label("Пароль:");

        GroupLayout layout = new GroupLayout(form);
        form.setLayout(layout);

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

        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonBar.add(okButton);
        buttonBar.add(cancelButton);

        add(form, BorderLayout.CENTER);
        add(buttonBar, BorderLayout.SOUTH);
    }

    private void readOptions() {
        Options options = Options.read();
        currentDirectoryTextField.setText(options.getCurrentDirectory());
        loginTextField.setText(options.getLogin());
        passwordPasswordField.setText(options.getPassword());
        verifiedOrgInnCheckBox.setSelected(options.isVerifiedOrgInn());
    }

    private void buildFrame() {
        setModal(true);
        pack();
        setMinimumSize(new Dimension(getSize().width, getSize().height));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Настройки");
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
