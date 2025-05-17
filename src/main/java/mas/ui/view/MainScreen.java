package mas.ui.view;

import java.awt.*;
import javax.swing.*;
import mas.ui.view.util.FighterTableModel;

public class MainScreen extends JFrame {
  private JPanel mainPanel;
  private JTable fightersTable;
  private JScrollPane fightersScrollPane;

  public MainScreen() {

    // Inicjalizacja UI
    mainPanel = new JPanel(new BorderLayout());
    fightersTable = new JTable();
    fightersScrollPane = new JScrollPane(fightersTable);

    fightersTable.setTableHeader(null);

    FighterTableModel tableModel = new FighterTableModel();
    fightersTable.setModel(tableModel);

    mainPanel.add(fightersScrollPane, BorderLayout.CENTER);

    // Setup JFrame
    setContentPane(mainPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);
    setVisible(true);

    // addFighterButton.addActionListener(_ -> {
    // try {
    // new Fighter(
    // "testoniusz",
    // "testowski",
    // Address.of(
    // 2,
    // "testowa",
    // "testowo",
    // "testow",
    // "21-37"
    // ));
    //
    // repaint();
    // revalidate();
    //
    // } catch (Exception ex) {
    // JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
    // JOptionPane.ERROR_MESSAGE);
    // }
    // });
  }
}
