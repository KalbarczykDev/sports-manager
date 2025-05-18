package mas.ui.view.fighter;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import mas.ui.theme.Colors;
import mas.ui.theme.Fonts;

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

    // Table setup
    tableModel = new FighterTableModel();
    fighterTable = new JTable(tableModel);
    fighterTable.setFillsViewportHeight(true);
    fighterTable.setFont(Fonts.BODY);
    fighterTable.setRowHeight(24);
    fighterTable.setShowGrid(false);
    fighterTable.setSelectionBackground(Colors.BUTTON_HOVER);
    fighterTable.setSelectionForeground(Colors.BUTTON_TEXT);

    JTableHeader header = fighterTable.getTableHeader();
    header.setFont(Fonts.SECTION);
    header.setBackground(Colors.PANEL_BACKGROUND);
    header.setForeground(Colors.TEXT);

    JScrollPane scrollPane = new JScrollPane(fighterTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));

    // Add to layout
    add(title, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
  }

  public void refreshTable() {
    tableModel.refresh();
  }

  public FighterTableModel getTableModel() {
    return tableModel;
  }

  public JTable getTable() {
    return fighterTable;
  }
}
