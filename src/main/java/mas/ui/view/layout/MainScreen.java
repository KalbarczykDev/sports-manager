package mas.ui.view.layout;

import java.awt.*;
import javax.swing.*;
import mas.model.data.ObjectExtent;
import mas.ui.view.component.MenuPanel;
import mas.ui.view.fight.AddFightPanel;
import mas.ui.view.fight.FightPanel;
import mas.ui.view.fighter.FighterPanel;
import mas.ui.view.util.Dialogs;

/**
 * MainScreen is the main application window that contains a menu and a card layout for different
 * views.
 */
public class MainScreen extends JFrame {

  private static MainScreen instance;

  private CardLayout cardLayout;
  private JPanel cardPanel;
  private MenuPanel menuPanel;

  private boolean editing = true; // TODO: Switch to false for prod

  /**
   * Returns the singleton instance of MainScreen.
   *
   * @return the instance of MainScreen
   */
  public static MainScreen getInstance() {
    if (instance == null) {
      instance = new MainScreen();
    }
    return instance;
  }

  /**
   * Private constructor to initialize the main screen. Sets up the layout, menu, and card panel for
   * different views.
   */
  public MainScreen() {
    // Layout
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addWindowListener(
        new java.awt.event.WindowAdapter() {
          @Override
          public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            try {
              // Save the current state of the application
              ObjectExtent.saveExtent();
            } catch (Exception e) {
              Dialogs.showWarning("Error saving data: " + e.getMessage());
            }
            System.exit(0);
          }
        });
    setSize(800, 600);
    setLocationRelativeTo(null);

    // Card
    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    // Create Views
    AddFightPanel addFightPanel = new AddFightPanel(this::switchView);
    JPanel fighterPanel = new FighterPanel();
    JPanel fightPanel = new FightPanel();
    cardPanel.add(addFightPanel, "addFight");
    cardPanel.add(fighterPanel, "fighters");
    cardPanel.add(fightPanel, "fights");

    menuPanel = new MenuPanel(this::switchView);

    // Add Components
    add(menuPanel, BorderLayout.WEST);
    add(cardPanel, BorderLayout.CENTER);

    // Set Visible
    setVisible(true);
  }

  /**
   * Switches the view in the card panel to the specified view name.
   *
   * @param viewName the name of the view to switch to
   */
  private void switchView(String viewName) {
    cardLayout.show(cardPanel, viewName);
  }

  /** Toggles the editing mode of the application. */
  public void toggleEditing() {
    this.editing = !this.editing;
  }

  /**
   * Checks if the application is in editing mode.
   *
   * @return true if in editing mode, false otherwise
   */
  public boolean isEditing() {
    return editing;
  }
}
