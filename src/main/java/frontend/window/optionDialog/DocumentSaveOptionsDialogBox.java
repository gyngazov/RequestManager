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
            new CheckBox("КСКПЭП (*.cer)", true),   // [0]
            new CheckBox("КСКПЭП (*.pdf)", true)    // [1]
    };
    private final RadioButton[] saveMethod = {
            new RadioButton("Сохранить файлы в папку, содержащую соответствующий запрос", false),   // [0]
            new RadioButton("Сохранить файлы в папку с номером заявки", true)                       // [1]
    };
    private final JButton submitButton = new JButton("Сохранить...");

    public DocumentSaveOptionsDialogBox() {
        createGroupRadioButtons();
        setLayout();
        setListener();
        setDefaults();
    }

    private void createGroupRadioButtons() {
        ButtonGroup saveMethodButtonGroup = new ButtonGroup();
        for (JRadioButton radioButton : saveMethod) {
            saveMethodButtonGroup.add(radioButton);
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
                .addComponent(howSave)
                .addComponent(saveMethod[0])
                .addComponent(saveMethod[1]));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(whatSave)
                .addComponent(documentType[0])
                .addComponent(documentType[1])
                .addComponent(howSave)
                .addComponent(saveMethod[0])
                .addComponent(saveMethod[1]));

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
        for (CheckBox type : documentType) {
            type.addItemListener(e -> {
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

        saveMethod[0].addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getSource() instanceof RadioButton) {
                    for (CheckBox type : documentType) {
                        type.setEnabled(false);
                        type.setSelected(true);
                    }
                }
            } else {
                for (CheckBox type : documentType) {
                    type.setEnabled(true);
                }
            }
        });

        submitButton.addActionListener(new ImportDocumentIEcp(documentType, saveMethod));
    }

    private void setDefaults() {
        saveMethod[0].setSelected(true);
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
