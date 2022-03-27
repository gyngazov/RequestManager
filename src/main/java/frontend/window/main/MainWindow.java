package frontend.window.main;

import frontend.window.main.filter.Table;
import frontend.window.main.filter.Options;

import java.awt.*;
import javax.swing.*;

public final class MainWindow extends JFrame {
    private final Table table = new Table();
    private final Options options = new Options(table);
    private final MainForm mainForm = new MainForm();
    private final ToolBar toolBar = new ToolBar(mainForm);

    public MainWindow() {
        localizeFileChooser();
        setJMenuBar(new MenuBar(table, mainForm, toolBar));
        setLayout();
        buildFrame();
    }

    private void localizeFileChooser() {
        UIManager.put("FileChooser.acceptAllFileFilterText", "Все файлы");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Отмена");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Таблица");
        UIManager.put("FileChooser.directoryOpenButtonText", "Открыть");
        UIManager.put("FileChooser.directoryOpenButtonToolTipText", "Открыть выбранную папку");
        UIManager.put("FileChooser.fileDateHeaderText", "Дата изменения");
        UIManager.put("FileChooser.fileNameHeaderText", "Имя");
        UIManager.put("FileChooser.fileNameLabelText", "Имя файла:");
        UIManager.put("FileChooser.fileSizeHeaderText", "Размер");
        UIManager.put("FileChooser.fileTypeHeaderText", "Тип");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файла:");
        UIManager.put("FileChooser.homeFolderToolTipText", "Рабочий стол");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
        UIManager.put("FileChooser.lookInLabelText", "Папка:");
        UIManager.put("FileChooser.newFolderToolTipText", "Создание новой папки");
        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.openButtonToolTipText", "Открыть выбранный файл");
        UIManager.put("FileChooser.openDialogTitleText", "Открытие");
        UIManager.put("FileChooser.saveButtonText", "Сохранить");
        UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить файл");
        UIManager.put("FileChooser.saveDialogTitleText", "Сохранение");
        UIManager.put("FileChooser.saveInLabelText", "Папка:");
        UIManager.put("FileChooser.upFolderToolTipText", "На один уровень вверх");
        UIManager.put("FileChooser.win32.newFolder", "Новая папка");
        UIManager.put("FileChooser.win32.newFolder.subsequent", "Новая папка ({0})");
        UIManager.put("FileChooser.win32.updateButtonText", "Обновить");
    }

    private void setLayout() {
        JScrollPane scrollPaneTable = new JScrollPane(table);

        JPanel panel = new JPanel();

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(mainForm)
                        .addComponent(options))
                .addComponent(scrollPaneTable));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainForm)
                        .addComponent(options))
                .addComponent(scrollPaneTable));

        add(toolBar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    private void buildFrame() {
        pack();
        setMinimumSize(new Dimension(getWidth(), getHeight()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Менеджер по обработке заявок");
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
