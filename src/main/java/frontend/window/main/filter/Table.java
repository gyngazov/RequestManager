package frontend.window.main.filter;

import backend.window.main.bar.listener.ExportAttachedDocumentIEcp;
import backend.window.main.filter.TableModelIEcp;
import frontend.window.main.MainWindow;
import frontend.window.optionDialog.DocumentSaveOptionsDialogBox;
import frontend.window.optionDialog.SendRequestDialogBox;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;

public class Table extends JTable {

    static class PopupMenu {
        private final JMenuItem importingApplication = new JMenuItem("Заявление");
        private final JMenuItem importingProcuration = new JMenuItem("Доверенность");
        private final JMenuItem importingDocument = new JMenuItem("Сертификат...");

        private final JMenuItem attachedDocument = new JMenuItem("Документы...");
        private final JMenuItem attachedRequest = new JMenuItem("Запрос...");

        PopupMenu() {
            setListener();
        }

        private void setListener() {
            importingDocument.addActionListener(e -> new DocumentSaveOptionsDialogBox().buildFrame(MainWindow.getInstance()));
            attachedDocument.addActionListener(new ExportAttachedDocumentIEcp());
            attachedRequest.addActionListener(e -> new SendRequestDialogBox().buildFrame(MainWindow.getInstance()));
        }

        @NotNull JPopupMenu createPopupMenu() {
            JPopupMenu popupMenu = new JPopupMenu();

            JMenu download = new JMenu("Скачать");
            download.add(importingApplication);
            download.add(importingProcuration);
            download.add(importingDocument);

            JMenu send = new JMenu("Прикрепить");
            send.add(attachedDocument);
            send.add(attachedRequest);

            popupMenu.add(download);
            popupMenu.add(send);

            return popupMenu;
        }
    }

    private static Table table;

    private Integer[] selectedRequestID;

    private Table() {
        setModel(new TableModelIEcp((ArrayList<? extends ArrayList<?>>) null));
        setHeightRow();
        setComponentPopupMenu(new PopupMenu().createPopupMenu());
        setListener();
    }

    private void setHeightRow() {
        int numberOfVisibleRows = 5;
        getPreferredScrollableViewportSize().height = getRowHeight() * numberOfVisibleRows;
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
