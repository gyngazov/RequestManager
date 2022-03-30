package frontend.window.optionDialog;

import backend.util.Constants;
import frontend.controlElement.Label;
import frontend.controlElement.TextField;

import javax.swing.*;

public final class InputPanel extends JPanel {
    private final Label title;
    private final TextField userInput = new TextField();

    public InputPanel(String title) {
        this.title = new Label(title);
        setLayout();
    }

    private void setLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(false);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addComponent(userInput, Constants.TEXT_FIELD_WIDTH / 3, Constants.TEXT_FIELD_WIDTH / 3, Constants.TEXT_FIELD_WIDTH / 3));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(title, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(userInput, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT));
    }

    public TextField getUserInput() {
        return userInput;
    }
}
