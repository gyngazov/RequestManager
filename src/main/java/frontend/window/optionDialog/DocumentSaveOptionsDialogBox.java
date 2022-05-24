package frontend.window.optionDialog;

import backend.window.main.bar.listener.ImportDocumentIEcp;
import frontend.controlElement.CheckBox;
import frontend.controlElement.Label;
import frontend.controlElement.RadioButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class DocumentSaveOptionsDialogBox extends JDialog {
    private final CheckBox[] documentType = {
            new CheckBox("КСКПЭП", true),                                                   // 0
            new CheckBox("КСКПЭП на бумажном носителе", true),                              // 1
            new CheckBox("Руководство по обеспечению безопасности", false)                  // 2
    };
    private final RadioButton[] saveType = {
            new RadioButton("Разложить файлы по папкам в соответствии с запросами", true),  // 0
            new RadioButton("Разложить файлы по папкам с номером заявки", false)            // 1
    };
    private final JButton submitButton = new JButton("Сохранить как...");

    public DocumentSaveOptionsDialogBox() {
        createGroupRadioButtons();
        setListener();
        setLayout();
    }

    private void createGroupRadioButtons() {
        ButtonGroup radioButtonGroup = new ButtonGroup();
        for (JRadioButton radioButton : saveType) {
            radioButtonGroup.add(radioButton);
        }
    }

    private @NotNull JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();

        Label whatSave = new Label("Выберите файлы для сохранения:");
        Label howSave = new Label("Укажите способ сохранения:");
        howSave.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(whatSave)
                .addComponent(documentType[0])
                .addComponent(documentType[1])
                .addComponent(documentType[2])
                .addComponent(howSave)
                .addComponent(saveType[0])
                .addComponent(saveType[1]));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(whatSave)
                .addComponent(documentType[0])
                .addComponent(documentType[1])
                .addComponent(documentType[2])
                .addComponent(howSave)
                .addComponent(saveType[0])
                .addComponent(saveType[1]));

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
        for (CheckBox checkBox : documentType) {
            checkBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    submitButton.setEnabled(true);
                } else {
                    boolean isEnabled = false;
                    for (CheckBox cB : documentType)
                        isEnabled |= cB.isSelected();
                    submitButton.setEnabled(isEnabled);
                }
            });
        }
        submitButton.addActionListener(new ImportDocumentIEcp());
    }

    public void buildFrame(Component parentComponent) {
        setModal(true);
        pack();
        setMinimumSize(new Dimension(getSize().width, getSize().height));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Параметры сохранения");
        setResizable(true);
        setLocationRelativeTo(parentComponent);
        setVisible(true);
    }
}
