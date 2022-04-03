package frontend.window.main.filter;

import backend.window.main.filter.TableModelIEcp;
import frontend.controlElement.Label;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Table extends JTable {
    private final Label numberOfSelectedRequestIDLabel = new Label("0");
    private final ArrayList<String> columnNames = new ArrayList<>(
            Arrays.asList(
                    "№ заявки",
                    "Наименование организации",
                    "ИНН организации",
                    "Фамилия заявителя",
                    "СНИЛС заявителя",
                    "Дата создания заявки",
                    "Статус заявки",
                    "Комментарий"));
    private final TableModelIEcp dataModel = new TableModelIEcp(null, columnNames);

    private Integer[] selectedRequestID;

    public Table() {
        super();
        setModel(dataModel);
        setHeight();
        setComponentPopupMenu(createPopupMenu());
        setListener();
    }

    private void setHeight() {
        int numberOfVisibleRows = 5;
        getPreferredScrollableViewportSize().height = getRowHeight() * numberOfVisibleRows;
    }

    private @NotNull JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem importApplication = new JMenuItem("Заявление");
        JMenuItem importProcuration = new JMenuItem("Доверенность");
        JMenuItem importCertificate = new JMenuItem("Сертификат");

        JMenu download = new JMenu("Скачать");
        download.add(importApplication);
        download.add(importProcuration);
        download.add(importCertificate);

        JMenuItem attachDocument = new JMenuItem("Документы заявителя");
        JMenuItem attachRequest = new JMenuItem("Запрос");

        JMenu send = new JMenu("Прикрепить");
        send.add(attachDocument);
        send.add(attachRequest);

        popupMenu.add(download);
        popupMenu.add(send);

        return popupMenu;
    }

    private void setListener() {
        getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int[] selectedRows = getSelectedRows();
                int countRows = selectedRows.length;
                selectedRequestID = new Integer[countRows];
                for (int i = 0; i < countRows; i++) {
                    selectedRequestID[i] = (Integer) getModel().getValueAt(selectedRows[i], 0);
                }
                System.out.println(Arrays.toString(selectedRequestID));
                numberOfSelectedRequestIDLabel.setText(String.valueOf(countRows));
            }
        });
    }

    public Label getNumberOfSelectedRequestIDLabel() {
        return numberOfSelectedRequestIDLabel;
    }

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public TableModelIEcp getDataModel() {
        return dataModel;
    }

    public Integer[] getSelectedRequestID() {
        return selectedRequestID;
    }
}
