package frontend.window.main;

import backend.window.main.bar.listener.*;
import backend.window.main.form.constant.DataTypeEnum;
import frontend.controlElement.ButtonToolBar;
import frontend.controlElement.TextField;

import javax.swing.*;
import java.awt.*;

public final class ToolBar extends JToolBar {
    private final TextField requestIDTextField = new TextField();

    private final MainForm mainForm;

    private JButton importDataPDF;
    private JButton importDataIEcp;
    private JButton exportDataIEcp;
    private JButton importDataCFG;
    private JButton exportDataCFG;
    private JButton deleteData;

    public ToolBar(MainForm mainForm) {
        this.mainForm = mainForm;

        setIconButton();
        buildToolBar();
        setToolTipText();
        setListener();
    }

    private Icon getIcon(String name) {
        int size = 24;
        return new ImageIcon(
                new ImageIcon(ClassLoader.getSystemResource(name))
                        .getImage()
                        .getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    private void setIconButton() {
        importDataPDF = new ButtonToolBar(getIcon("importDataPDF.png"));
        importDataPDF.setPressedIcon(getIcon("importDataPDF_pressed.png"));

        importDataIEcp = new ButtonToolBar(getIcon("importDataIEcp.png"));
        importDataIEcp.setPressedIcon(getIcon("importDataIEcp_pressed.png"));
        exportDataIEcp = new ButtonToolBar(getIcon("exportDataIEcp.png"));
        exportDataIEcp.setPressedIcon(getIcon("exportDataIEcp_pressed.png"));

        importDataCFG = new ButtonToolBar(getIcon("importDataCFG.png"));
        importDataCFG.setPressedIcon(getIcon("importDataCFG_pressed.png"));
        exportDataCFG = new ButtonToolBar(getIcon("exportDataCFG.png"));
        exportDataCFG.setPressedIcon(getIcon("exportDataCFG_pressed.png"));

        deleteData = new ButtonToolBar(getIcon("basket.png"));
        deleteData.setPressedIcon(getIcon("basket_pressed.png"));
    }

    private void buildToolBar() {
        addSeparator();
        add(importDataPDF);
        addSeparator();
        add(new JSeparator(JSeparator.VERTICAL));
        addSeparator();
        add(importDataIEcp);
        add(exportDataIEcp);
        add(requestIDTextField);
        addSeparator();
        add(new JSeparator(JSeparator.VERTICAL));
        addSeparator();
        add(importDataCFG);
        add(exportDataCFG);
        addSeparator();
        add(new JSeparator(JSeparator.VERTICAL));
        addSeparator();
        add(deleteData);
        addSeparator();

        setBorderPainted(false);
        setFloatable(false);
        setOrientation(SwingConstants.HORIZONTAL);
        setRollover(false);
    }

    private void setToolTipText() {
        importDataPDF.setToolTipText("Выгрузить сведения из ЕГРЮЛ/ЕГРИП");
        importDataIEcp.setToolTipText("Выгрузить все сведения по № заявки");
        exportDataIEcp.setToolTipText("Создать/изменить заявку");
        importDataCFG.setToolTipText("Выгрузить все сведения из CFG");
        exportDataCFG.setToolTipText("Создать CFG");
        deleteData.setToolTipText("Очистить всё");
    }

    private void setListener() {
        importDataPDF.addActionListener(new ImportDataPDF(mainForm));
        importDataIEcp.addActionListener(new ImportDataIEcp(mainForm, requestIDTextField, DataTypeEnum.ALL_DATA));
        exportDataIEcp.addActionListener(new ExportDataIEcp(mainForm, requestIDTextField));
        deleteData.addActionListener(new DataReset(mainForm, DataTypeEnum.ALL_DATA));
    }

    public TextField getRequestIDTextField() {
        return requestIDTextField;
    }
}
