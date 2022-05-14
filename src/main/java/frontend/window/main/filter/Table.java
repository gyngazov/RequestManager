package frontend.window.main.filter;

import backend.window.main.filter.TableModelIEcp;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;

public class Table extends JTable {
    private static Table table;

    private Integer[] selectedRequestID;

    private Table() {
        setModel(new TableModelIEcp((ArrayList<? extends ArrayList<?>>) null));
        setHeightRow();
        setComponentPopupMenu(createPopupMenu());
        setListener();
    }

    private void setHeightRow() {
        int numberOfVisibleRows = 5;
        getPreferredScrollableViewportSize().height = getRowHeight() * numberOfVisibleRows;
    }

    private @NotNull JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem importApplication = new JMenuItem("Заявление");
        JMenuItem importProcuration = new JMenuItem("Доверенность");
        JMenuItem importCertificate = new JMenuItem("Сертификат...");

        JMenu download = new JMenu("Скачать");
        download.add(importApplication);
        download.add(importProcuration);
        download.add(importCertificate);

        JMenuItem attachDocument = new JMenuItem("Документы...");
        JMenuItem attachRequest = new JMenuItem("Запрос...");

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
                FilterOptions.getInstance().getSelectedRequestIDLabel().setText(String.valueOf(countRows));
            }
        });
    }

    public Integer[] getSelectedRequestID() {
        return selectedRequestID;
    }

    public static Table getInstance() {
        if (table == null) {
            table = new Table();
        }
        return table;
    }
}
