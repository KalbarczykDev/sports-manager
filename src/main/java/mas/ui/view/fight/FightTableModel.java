package mas.ui.view.fight;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import mas.model.Fight;
import mas.model.Fighter;
import mas.model.Gala;
import mas.model.data.ObjectExtent;

public class FightTableModel extends AbstractTableModel {

  private List<Fight> fights;
  private String[] columnNames = {"Winner", "Participants", "Gala", "Date"};

  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

  public FightTableModel() {
    this.fights = ObjectExtent.getExtent(Fight.class);
  }

  public Fight getFight(int rowIndex) {
    return fights.get(rowIndex);
  }

  public void refresh() {
    fights = ObjectExtent.getExtent(Fight.class);
    fireTableDataChanged();
  }

  @Override
  public int getRowCount() {
    return ObjectExtent.getExtent(Fight.class).size();
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
    Fight fight = fights.get(rowIndex);
    Gala gala = fight.getGala();

    return switch (columnNames[columnIndex]) {
      case "Winner" -> {
        Fighter winner = fight.getWinner();
        yield winner != null ? winner.getName() : "No winner yet";
      }
      case "Participants" ->
          fight.getParticipants().stream()
              .map(
                  participant ->
                      participant.getFighter().getName()
                          + " "
                          + participant.getFighter().getSurname())
              .reduce((a, b) -> a + ", " + b)
              .orElse("No Participants");

      case "Gala" -> {
        String galaName = gala != null ? gala.getEventName() : "No Gala";
        yield galaName;
      }

      case "Date" -> {
        String date = gala != null ? gala.getDate().format(DATE_FORMATTER) : "No Date";
        yield date;
      }

      default -> null;
    };
  }
}
