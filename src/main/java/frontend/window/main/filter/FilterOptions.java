package frontend.window.main.filter;

import backend.util.Constants;
import backend.util.Validatable;
import backend.window.main.filter.listener.ImportFilteredDataIEcp;
import backend.window.main.filter.listener.RequestListener;
import frontend.controlElement.ComboBox;
import frontend.controlElement.Label;
import frontend.controlElement.TextField;
import backend.window.main.filter.constant.StatusEnum;

import javax.swing.*;
import java.awt.*;

public final class FilterOptions extends JPanel {
    private static FilterOptions filterOptions;

    private final TextField requestIDTextField = new TextField();
    private final TextField creationDateTextField = new TextField();
    private final ComboBox<StatusEnum> statusComboBox = new ComboBox.Status();
    private final TextField commonNameTextField = new TextField();
    private final TextField orgINNTextField = new TextField(Validatable::isCorrectOrgINN);
    private final TextField lastNameTextField = new TextField();
    private final TextField SNILSTextField = new TextField(Validatable::isCorrectSNILS);
    private final Label selectedRequestIDLabel = new Label("0");
    private final Label foundRequestIDLabel = new Label("0");
    private final JButton filterButton = new JButton("Фильтр");

    private FilterOptions() {
        setLayout();
        setPanelBorder();
        setListener();
    }

    private void setLayout() {
        Label requestIDLabel = new Label("№ заявки");
        Label creationDateLabel = new Label("Дата создания заявки");
        Label statusLabel = new Label("Статус заявки");
        Label commonNameLabel = new Label("Название организации");
        Label orgINNLabel = new Label("ИНН организации");
        Label lastNameLabel = new Label("Фамилия заявителя");
        Label SNILSLabel = new Label("СНИЛС заявителя");

        JPanel score = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        score.add(selectedRequestIDLabel);
        score.add(new Label("/"));
        score.add(foundRequestIDLabel);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(requestIDLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                .addComponent(requestIDTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
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
                .addComponent(score, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                .addComponent(filterButton, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(requestIDLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(requestIDTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
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
                .addComponent(score, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(filterButton, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT));
    }

    private void setPanelBorder() {
        setBorder(Constants.createDefaultTitledBorder("ПАРАМЕТРЫ ФИЛЬТРА"));
    }

    private void setListener() {
        requestIDTextField.getDocument().addDocumentListener(new RequestListener(this));
        filterButton.addActionListener(new ImportFilteredDataIEcp(this));
    }

    public TextField getRequestIDTextField() {
        return requestIDTextField;
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

    public Label getSelectedRequestIDLabel() {
        return selectedRequestIDLabel;
    }

    public Label getFoundRequestIDLabel() {
        return foundRequestIDLabel;
    }

    public static FilterOptions getInstance() {
        if (filterOptions == null) {
            filterOptions = new FilterOptions();
        }
        return filterOptions;
    }
}
