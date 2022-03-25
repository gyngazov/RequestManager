package window.optionDialog;

import backend.util.Constants;
import frontend.Label;
import frontend.TextField;

import javax.swing.*;

public final class InputPanel extends JPanel {
    private final Label title;
    private final TextField in = new TextField();

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
                .addComponent(in, Constants.TEXT_FIELD_WIDTH / 4, Constants.TEXT_FIELD_WIDTH / 4, Constants.TEXT_FIELD_WIDTH / 4));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(title, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(in, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT));
    }

    public TextField getIn() {
        return in;
    }
}