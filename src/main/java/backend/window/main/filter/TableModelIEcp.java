package backend.window.main.filter;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class TableModelIEcp extends AbstractTableModel {
    private ArrayList<? extends ArrayList<?>> data;
    private ArrayList<?> columnNames;

    public TableModelIEcp(Object[][] data) {
        setDataArrayList(convertToArrayList(data));
    }

    public TableModelIEcp(ArrayList<? extends ArrayList<?>> data) {
        setDataArrayList(data);
    }

    public void setDataArrayList(ArrayList<? extends ArrayList<?>> data) {
        this.data = nonNullArrayList(data);
        this.columnNames = new ArrayList<>(
                Arrays.asList(
                        "№ заявки",
                        "Наименование организации",
                        "ИНН организации",
                        "Фамилия заявителя",
                        "СНИЛС заявителя",
                        "Дата создания заявки",
                        "Статус заявки",
                        "Комментарий"));
        fireTableStructureChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int column) {
        Object id = null;
        if (column < columnNames.size() && column >= 0) {
            id = columnNames.get(column);
        }
        return id == null ? super.getColumnName(column) : id.toString();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Integer.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column) {
        ArrayList<?> rowArrayList = data.get(row);
        return rowArrayList.get(column);
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
    }

    private static ArrayList<Object> convertToArrayList(Object[] anArray) {
        if (anArray == null) {
            return null;
        }
        ArrayList<Object> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, anArray);
        return arrayList;
    }

    private static ArrayList<? extends ArrayList<Object>> convertToArrayList(Object[][] anArray) {
        if (anArray == null) {
            return null;
        }
        ArrayList<ArrayList<Object>> arrayList = new ArrayList<>();
        for (Object[] obj : anArray) {
            arrayList.add(convertToArrayList(obj));
        }
        return arrayList;
    }

    private static <E> @NotNull ArrayList<E> nonNullArrayList(ArrayList<E> arrayList) {
        return (arrayList != null) ? arrayList : new ArrayList<>();
    }
}
