package frontend.window.main;

import backend.window.main.bar.listener.*;
import backend.window.main.form.constant.DataTypeEnum;
import frontend.window.optionDialog.SoftwareConfigurationDialog;
import frontend.window.optionDialog.About;

import javax.swing.*;

final class MenuBar extends JMenuBar {
    private static MenuBar menuBar;

    private final JMenuItem configuration = new JMenuItem("Настройки...");
    private final JMenuItem exit = new JMenuItem("Выход");

    private final JMenuItem importingDataPDF = new JMenuItem("Выгрузить сведения из ЕГРЮЛ/ЕГРИП...");

    private final JMenuItem importingOrganizationDataIEcp = new JMenuItem("Заполнить карточку организации по № заявки...");
    private final JMenuItem importingApplicantDataIEcp = new JMenuItem("Заполнить ПДн заявителя по № заявки...");
    private final JMenuItem importingDataIEcp = new JMenuItem("Выгрузить все сведения по № заявки...");

    private final JMenuItem importingOrganizationDataCfg = new JMenuItem("Заполнить карточку организации из CFG...");
    private final JMenuItem importingApplicantDataCfg = new JMenuItem("Заполнить ПДн заявителя из CFG...");
    private final JMenuItem importingDataCfg = new JMenuItem("Выгрузить все сведения из CFG...");

    private final JMenuItem deletingOrganizationData = new JMenuItem("Очистить карточку организации");
    private final JMenuItem deletingApplicantData = new JMenuItem("Очистить ПДн заявителя");
    private final JMenuItem deletingData = new JMenuItem("Очистить всё");

    private final JMenuItem packingFiles = new JMenuItem("Запаковать документы...");
    private final JMenuItem attachedFile = new JMenuItem("Прикрепить документы...");
    private final JMenuItem attachedRequest = new JMenuItem("Прикрепить запрос...");

    private final JMenuItem importingDocument = new JMenuItem("Скачать документы...");

    private final JMenuItem about = new JMenuItem("О программе...");

    private MenuBar() {
        addMenuBar();
        setListener();
    }

    private void addMenuBar() {
        JMenu file = new JMenu("Файл");
        JMenu edit = new JMenu("Правка");
        JMenu iEcp = new JMenu("iEcp");
        JMenu help = new JMenu("Помощь");

        JMenu insert = new JMenu("Вставить");
        JMenu delete = new JMenu("Очистить");

        JMenu attach = new JMenu("Прикрепить");

        file.add(configuration);
        file.addSeparator();
        file.add(exit);

        insert.add(importingDataPDF);
        insert.addSeparator();
        insert.add(importingOrganizationDataIEcp);
        insert.add(importingApplicantDataIEcp);
        insert.add(importingDataIEcp);
        insert.addSeparator();
        insert.add(importingOrganizationDataCfg);
        insert.add(importingApplicantDataCfg);
        insert.add(importingDataCfg);

        delete.add(deletingOrganizationData);
        delete.add(deletingApplicantData);
        delete.add(deletingData);

        edit.add(insert);
        edit.add(delete);

        attach.add(attachedFile);
        attach.add(attachedRequest);

        iEcp.add(packingFiles);
        iEcp.addSeparator();
        iEcp.add(attach);
        iEcp.add(importingDocument);

        help.add(about);

        add(file);
        add(edit);
        add(iEcp);
        add(help);
    }

    private void setListener() {
        configuration.addActionListener(e -> SoftwareConfigurationDialog.getInstance().buildFrame(MainWindow.getInstance()));
        exit.addActionListener(e -> MainWindow.getInstance().dispose());

        importingDataPDF.addActionListener(new ImportDataPDF());

        importingOrganizationDataIEcp.addActionListener(new ImportDataIEcp(null, DataTypeEnum.ORGANIZATION_DATA));
        importingApplicantDataIEcp.addActionListener(new ImportDataIEcp(null, DataTypeEnum.APPLICANT_DATA));
        importingDataIEcp.addActionListener(new ImportDataIEcp(null, DataTypeEnum.ALL_DATA));

        deletingOrganizationData.addActionListener(new DataReset(DataTypeEnum.ORGANIZATION_DATA));
        deletingApplicantData.addActionListener(new DataReset(DataTypeEnum.APPLICANT_DATA));
        deletingData.addActionListener(new DataReset(DataTypeEnum.ALL_DATA));

        packingFiles.addActionListener(new PackingFiles());
        attachedFile.addActionListener(new ExportAttachedFileIEcp());

        about.addActionListener(e -> new About());
    }

    public static MenuBar getInstance() {
        if (menuBar == null) {
            menuBar = new MenuBar();
        }
        return menuBar;
    }
}
