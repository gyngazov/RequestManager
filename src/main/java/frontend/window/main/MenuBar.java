package frontend.window.main;

import backend.window.main.bar.listener.DataReset;
import backend.window.main.bar.listener.ImportDataIEcp;
import backend.window.main.bar.listener.ImportDataPDF;
import backend.window.main.form.constant.DataTypeEnum;
import frontend.window.main.filter.Table;
import frontend.window.optionDialog.Setting;
import frontend.window.optionDialog.About;

import javax.swing.*;

final class MenuBar extends JMenuBar {
    private final Table table;
    private final MainForm mainForm;
    private final ToolBar toolBar;

    private final JMenuItem settings = new JMenuItem("Настройки");
    private final JMenuItem exit = new JMenuItem("Выход");

    private final JMenuItem importDataPDF = new JMenuItem("Выгрузить сведения из ЕГРЮЛ/ЕГРИП");

    private final JMenuItem importOrganizationDataIEcp = new JMenuItem("Заполнить карточку организации по № заявки");
    private final JMenuItem importApplicantDataIEcp = new JMenuItem("Заполнить ПДн заявителя по № заявки");
    private final JMenuItem importDataIEcp = new JMenuItem("Выгрузить все сведения по № заявки");

    private final JMenuItem importOrganizationDataCfg = new JMenuItem("Заполнить карточку организации из CFG");
    private final JMenuItem importApplicantDataCfg = new JMenuItem("Заполнить ПДн заявителя из CFG");
    private final JMenuItem importDataCfg = new JMenuItem("Выгрузить все сведения из CFG");

    private final JMenuItem deleteOrganizationData = new JMenuItem("Очистить карточку организации");
    private final JMenuItem deleteApplicantData = new JMenuItem("Очистить ПДн заявителя");
    private final JMenuItem deleteData = new JMenuItem("Очистить всё");

    private final JMenuItem attachDocument = new JMenuItem("Документы заявителя");
    private final JMenuItem attachRequest = new JMenuItem("Запрос");

    private final JMenuItem importDocument = new JMenuItem("Документы");

    private final JMenuItem about = new JMenuItem("О программе");

    public MenuBar(Table table,
                   MainForm mainForm,
                   ToolBar toolBar) {
        this.table = table;
        this.mainForm = mainForm;
        this.toolBar = toolBar;

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
        JMenu download = new JMenu("Скачать");

        file.add(settings);
        file.addSeparator();
        file.add(exit);

        insert.add(importDataPDF);
        insert.addSeparator();
        insert.add(importOrganizationDataIEcp);
        insert.add(importApplicantDataIEcp);
        insert.add(importDataIEcp);
        insert.addSeparator();
        insert.add(importOrganizationDataCfg);
        insert.add(importApplicantDataCfg);
        insert.add(importDataCfg);

        delete.add(deleteOrganizationData);
        delete.add(deleteApplicantData);
        delete.add(deleteData);

        edit.add(insert);
        edit.add(delete);

        attach.add(attachDocument);
        attach.add(attachRequest);

        download.add(importDocument);

        iEcp.add(attach);
        iEcp.add(download);

        help.add(about);

        add(file);
        add(edit);
        add(iEcp);
        add(help);
    }

    private void setListener() {
        settings.addActionListener(e -> new Setting());
        exit.addActionListener(e -> System.exit(0));

        importDataPDF.addActionListener(new ImportDataPDF(mainForm));

        importOrganizationDataIEcp.addActionListener(new ImportDataIEcp(mainForm, toolBar.getRequestIDTextField(), DataTypeEnum.ORGANIZATION_DATA));
        importApplicantDataIEcp.addActionListener(new ImportDataIEcp(mainForm, toolBar.getRequestIDTextField(), DataTypeEnum.APPLICANT_DATA));
        importDataIEcp.addActionListener(new ImportDataIEcp(mainForm, toolBar.getRequestIDTextField(), DataTypeEnum.ALL_DATA));

        deleteOrganizationData.addActionListener(new DataReset(mainForm, DataTypeEnum.ORGANIZATION_DATA));
        deleteApplicantData.addActionListener(new DataReset(mainForm, DataTypeEnum.APPLICANT_DATA));
        deleteData.addActionListener(new DataReset(mainForm, DataTypeEnum.ALL_DATA));

        about.addActionListener(e -> new About());
    }
}
