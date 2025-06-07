package mas.ui.view.fighter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import mas.model.Fighter;
import mas.model.data.ObjectExtent;

/**
 * FighterTableModel is an AbstractTableModel that provides data for a JTable displaying fighters.
 * It retrieves fighter data from the ObjectExtent and formats it for display in the table.
 */
public class FighterTableModel extends AbstractTableModel {

  private List<Fighter> fighters;
  private String[] columnNames = {
    "Name", "Surname", "Joined", "Titles", "Salary",
  };

  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

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

    return switch (columnNames[columnIndex]) {
      case "Name" -> fighter.getName();
      case "Surname" -> fighter.getSurname();
      case "Joined" -> fighter.getDateOfJoining().format(DATE_FORMATTER);
      case "Titles" -> fighter.getTitles().size();
      case "Salary" -> fighter.getSalary();
      default -> null;
    };
  }
}
