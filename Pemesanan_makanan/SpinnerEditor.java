package Pemesanan_makanan;

import javax.swing.*;
import javax.swing.table.TableCellEditor;

public class SpinnerEditor extends AbstractCellEditor
        implements TableCellEditor {

    private final JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    @Override
    public java.awt.Component getTableCellEditorComponent(
            JTable table, Object value,
            boolean isSelected, int row, int column) {
        spinner.setValue(value);
        return spinner;
    }
}
