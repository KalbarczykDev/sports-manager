package mas.ui.view.fighter;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import mas.model.Fighter;
import mas.ui.theme.Colors;
import mas.ui.theme.Fonts;
import mas.ui.view.util.Dialogs;

/**
 * FighterPanel is a JPanel that displays a table of fighters and allows users to manage them. It
 * includes functionality to view fighter details on double-click.
 */
public class FighterPanel extends JPanel {

  private JTable fighterTable;
  private FighterTableModel tableModel;

  public FighterPanel() {
    setLayout(new BorderLayout());
    setBackground(Colors.PANEL_BACKGROUND);

    // Title
    JLabel title = new JLabel("Manage Fighters");
    title.setFont(Fonts.TITLE);
    title.setForeground(Colors.TITLE);
    title.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));

    // Init Ui
    tableModel = new FighterTableModel();
    fighterTable = new FighterTable(tableModel);

    JScrollPane scrollPane = new JScrollPane(fighterTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));

    // Add to layout
    add(title, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
  }

  /**
   * FighterTable is a custom JTable that displays fighters. It allows double-clicking on a fighter
   * to view its details.
   */
  class FighterTable extends JTable {
    public FighterTable(FighterTableModel tableModel) {
      super(tableModel);

      addMouseListener(
          new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
              int row = fighterTable.rowAtPoint(e.getPoint());

              if (e.getClickCount() == 2 && row != -1) {
                Fighter fighter = tableModel.getFighter(row);
                showFighterDetails(fighter);
              }
            }
          });

      setRowSorter(new TableRowSorter<FighterTableModel>(tableModel));

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

  /**
   * Displays the details of a fighter in a dialog.
   *
   * @param fighter the Fighter object whose details are to be displayed
   */
  private void showFighterDetails(Fighter fighter) {
    Dialogs.showInfoDialog(
        "Fighter Details:\n"
            + "Name: "
            + fighter.getName()
            + "\n"
            + "Surname: "
            + fighter.getSurname()
            + "\n"
            + "Joined: "
            + fighter.getDateOfJoining().format(FighterTableModel.DATE_FORMATTER)
            + "\n"
            + "Titles: "
            + fighter.getTitles().size()
            + "\n"
            + "Salary: "
            + fighter.getSalary());
  }

  /** Refreshes the fighter table data by reloading the fighters from the ObjectExtent. */
  public void refreshTable() {
    tableModel.refresh();
  }

  /**
   * Returns the FighterTableModel associated with this panel.
   *
   * @return the FighterTableModel
   */
  public FighterTableModel getTableModel() {
    return tableModel;
  }

  /**
   * Returns the JTable that displays the fighters.
   *
   * @return the JTable instance
   */
  public JTable getTable() {
    return fighterTable;
  }
}
