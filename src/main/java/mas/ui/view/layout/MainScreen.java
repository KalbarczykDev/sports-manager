package mas.ui.view.layout;

import java.awt.*;
import javax.swing.*;
import mas.ui.view.component.MenuPanel;
import mas.ui.view.fighter.FighterPanel;

public class MainScreen extends JFrame {

  private MenuPanel menuPanel;

  private FighterPanel fighterPanel;

  public MainScreen() {
    // Layout
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);

    // Init UI

    menuPanel = new MenuPanel();
    fighterPanel = new FighterPanel();

    // Add Components
    add(menuPanel, BorderLayout.WEST);
    add(fighterPanel, BorderLayout.CENTER);

    // Set Visible
    setVisible(true);
  }
}
