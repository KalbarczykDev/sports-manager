package mas.ui.view.util;

import mas.model.Fighter;
import mas.model.data.ObjectExtent;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class FighterTableModel extends AbstractTableModel {


    private List<Fighter> fighters;
    private String[] columnNames = { "Name", "Surname" };


    public FighterTableModel() {
        this.fighters = ObjectExtent.getExtent(Fighter.class);
    }

    public Fighter getFighter(int rowIndex) {
        return fighters.get(rowIndex);
    }

    public void refresh() {
        fighters = ObjectExtent.getExtent(Fighter.class);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return ObjectExtent.getExtent(Fighter.class).size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

     @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Fighter fighter = fighters.get(rowIndex);

        return switch (columnIndex){
            case 0 -> fighter.getName();
            case 1 -> fighter.getSurname();
            default -> null;
        };

    }
}
