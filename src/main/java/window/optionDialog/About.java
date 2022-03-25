package window.optionDialog;

import frontend.Label;

import javax.swing.*;
import java.awt.*;

public final class About extends JDialog {
    private final JButton OKButton = new JButton("OK");

    public About() {
        setListener();
        setLayout();
        buildFrame();
    }

    private void setListener() {
        OKButton.addActionListener(e -> dispose());
    }

    private void setLayout() {
        Label titleLabel = new Label("<html><body><h2><b>REQUEST MANAGER</b></h2></body></html>", SwingConstants.CENTER);
        Label aboutLabel = new Label("Вячеслав Магергут");
        Label versionLabel = new Label("Версия 2.0.0");
        Label sourceCodeLabel = new Label("https://github.com/Vjaches1av/RequestManager.git");

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        infoPanel.add(titleLabel);
        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        infoPanel.add(Box.createVerticalStrut(2));
        infoPanel.add(aboutLabel);
        infoPanel.add(Box.createVerticalStrut(2));
        infoPanel.add(versionLabel);
        infoPanel.add(Box.createVerticalStrut(2));
        infoPanel.add(sourceCodeLabel);

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        navigationPanel.add(OKButton);

        add(infoPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    private void buildFrame() {
        setModal(true);
        pack();
        setMinimumSize(new Dimension(getSize().width, getSize().height));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("О программе");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(About::new);
    }
}
