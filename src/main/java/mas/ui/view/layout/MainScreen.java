package mas.ui.view.layout;

import java.awt.*;
import javax.swing.*;
import mas.ui.view.component.MenuPanel;

public class MainScreen extends JFrame {

  private MenuPanel menuPanel;

  public MainScreen() {
    // Layout
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);

    // Init UI

    menuPanel = new MenuPanel();

    // Add Components
    add(menuPanel, BorderLayout.WEST);

    // Set Visible
    setVisible(true);
  }
}
