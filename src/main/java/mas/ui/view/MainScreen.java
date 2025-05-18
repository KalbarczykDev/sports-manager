package mas.ui.view;

import java.awt.*;
import javax.swing.*;

public class MainScreen extends JFrame {

  private MenuPanel menuPanel;

  public MainScreen() {

    // Init UI
    menuPanel = new MenuPanel();

    // Layout
    setLayout(new BorderLayout());

    // Add Components
    add(menuPanel, BorderLayout.WEST);

    // Setup JFrame

    setBackground(Color.BLACK);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
