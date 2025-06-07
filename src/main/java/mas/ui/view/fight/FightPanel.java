package mas.ui.view.fight;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import mas.model.Fight;
import mas.model.Fighter;
import mas.ui.theme.Colors;
import mas.ui.theme.Fonts;
import mas.ui.view.layout.*;
import mas.ui.view.util.Dialogs;

public class FightPanel extends JPanel {

  private JTable fighterTable;
  private FightTableModel tableModel;
  private JButton addFightButton;

  public FightPanel(Consumer<String> switchView) {
    setLayout(new BorderLayout());
    setBackground(Colors.PANEL_BACKGROUND);

    // Title
    JLabel title = new JLabel("Manage Fights");
    title.setFont(Fonts.TITLE);
    title.setForeground(Colors.TITLE);
    title.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));

    // Init Ui
    tableModel = new FightTableModel();
    fighterTable = new FightTable(tableModel);
    addFightButton = new JButton("Add Fight");

    // AddFight Button
    addFightButton.setFont(Fonts.BUTTON);
    addFightButton.setForeground(Colors.BUTTON_TEXT);
    addFightButton.addActionListener(
        _ -> {
          MainScreen.getInstance().toggleEditing();
          switchView.accept("addFight");
        });

    JScrollPane scrollPane = new JScrollPane(fighterTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));

    // Add to layout
    add(title, BorderLayout.NORTH);
    add(addFightButton, BorderLayout.SOUTH);
    add(scrollPane, BorderLayout.CENTER);
  }

  class FightTable extends JTable {
    public FightTable(FightTableModel tableModel) {
      super(tableModel);

      addMouseListener(
          new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
              int row = fighterTable.rowAtPoint(e.getPoint());

              if (e.getClickCount() == 2 && row != -1) {
                Fight fight = tableModel.getFight(row);
                showFightDetails(fight);
              }
            }
          });

      setRowSorter(new TableRowSorter<FightTableModel>(tableModel));

      setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

      setFillsViewportHeight(true);
      setFont(Fonts.BODY);
      setRowHeight(28);
      setShowGrid(false);
      setIntercellSpacing(new Dimension(0, 0));
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
      Component c = super.prepareRenderer(renderer, row, column);
      if (!isRowSelected(row)) {
        c.setBackground(row % 2 == 0 ? Color.WHITE : Colors.BUTTON_HOVER);
      } else {
        c.setBackground(Colors.BUTTON_SELECTED);
      }
      c.setForeground(Colors.TEXT);
      return c;
    }
  }

  private void showFightDetails(Fight fight) {
    Fighter winner = fight.getWinner();
    String winnerName =
        (winner != null) ? winner.getName() + " " + winner.getSurname() : "No winner";

    String participants =
        fight.getParticipants().stream()
            .map(
                participant -> {
                  Fighter f = participant.getFighter();
                  return (f != null) ? f.getName() + " " + f.getSurname() : "[Unknown]";
                })
            .reduce((a, b) -> a + ", " + b)
            .orElse("No Participants");

    Dialogs.showInfoDialog(
        "Fight Details:\n" + "Winner: " + winnerName + "\n" + "Participants: " + participants);
  }

  public void refreshTable() {
    tableModel.refresh();
  }

  public FightTableModel getTableModel() {
    return tableModel;
  }

  public JTable getTable() {
    return fighterTable;
  }
}
