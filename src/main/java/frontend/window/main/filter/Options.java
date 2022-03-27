package frontend.window.main.filter;

import backend.util.Constants;
import backend.util.Validation;
import backend.window.main.filter.listener.ImportFilteredDataIEcp;
import backend.window.main.filter.listener.RequestListener;
import frontend.controlElement.ComboBox;
import frontend.controlElement.Label;
import frontend.controlElement.TextField;
import backend.window.main.filter.constant.StatusEnum;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public final class Options extends JPanel {
    private final Table table;

    private final TextField requestIdTextField = new TextField();
    private final TextField creationDateTextField = new TextField();
    private final ComboBox<StatusEnum> statusComboBox = new ComboBox.Status();
    private final TextField commonNameTextField = new TextField();
    private final TextField orgINNTextField = new TextField(Validation::isCorrectOrgINN);
    private final TextField lastNameTextField = new TextField();
    private final TextField SNILSTextField = new TextField(Validation::isCorrectSNILS);
    private final Label numberOfSelectedRequestIdLabel;
    private final Label numberOfFoundRequestIdLabel = new Label("0");
    private final JButton filterButton = new JButton("Фильтр");

    public Options(@NotNull Table table) {
        this.table = table;
        this.numberOfSelectedRequestIdLabel = table.getNumberOfSelectedRequestIdLabel();

        setLayout();
        setPanelBorder();
        setListener();
    }

    private void setLayout() {
        Label requestIdLabel = new Label("№ заявки");
        Label creationDateLabel = new Label("Дата создания заявки");
        Label statusLabel = new Label("Статус заявки");
        Label commonNameLabel = new Label("Название организации");
        Label orgINNLabel = new Label("ИНН организации");
        Label lastNameLabel = new Label("Фамилия заявителя");
        Label SNILSLabel = new Label("СНИЛС заявителя");

        Label slashLabel = new Label("/");

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.add(numberOfSelectedRequestIdLabel);
        panel.add(slashLabel);
        panel.add(numberOfFoundRequestIdLabel);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(requestIdLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                .addComponent(requestIdTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                .addComponent(creationDateLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                .addComponent(creationDateTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                .addComponent(statusLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                .addComponent(statusComboBox, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                .addComponent(commonNameLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                .addComponent(commonNameTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                .addComponent(orgINNLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                .addComponent(orgINNTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                .addComponent(lastNameLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                .addComponent(lastNameTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                .addComponent(SNILSLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                .addComponent(SNILSTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                .addComponent(panel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                .addComponent(filterButton, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(requestIdLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(requestIdTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addComponent(creationDateLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(creationDateTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addComponent(statusLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(statusComboBox, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addComponent(commonNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(commonNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addComponent(orgINNLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(orgINNTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addComponent(lastNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(lastNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addComponent(SNILSLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(SNILSTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addComponent(panel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(filterButton, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT));
    }

    private void setPanelBorder() {
        setBorder(Constants.createDefaultTitledBorder("ПАРАМЕТРЫ ФИЛЬТРА"));
    }

    private void setListener() {
        requestIdTextField.getDocument().addDocumentListener(new RequestListener(this));
        filterButton.addActionListener(new ImportFilteredDataIEcp(this));
    }

    public Table getTable() {
        return table;
    }

    public TextField getRequestIdTextField() {
        return requestIdTextField;
    }

    public TextField getCreationDateTextField() {
        return creationDateTextField;
    }

    public ComboBox<StatusEnum> getStatusComboBox() {
        return statusComboBox;
    }

    public TextField getCommonNameTextField() {
        return commonNameTextField;
    }

    public TextField getOrgINNTextField() {
        return orgINNTextField;
    }

    public TextField getLastNameTextField() {
        return lastNameTextField;
    }

    public TextField getSNILSTextField() {
        return SNILSTextField;
    }

    public Label getNumberOfSelectedRequestIdLabel() {
        return numberOfSelectedRequestIdLabel;
    }

    public Label getNumberOfFoundRequestIdLabel() {
        return numberOfFoundRequestIdLabel;
    }

    public JButton getFilterButton() {
        return filterButton;
    }
}
