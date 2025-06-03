package mas.ui.view.layout;

import java.awt.*;
import javax.swing.*;
import mas.ui.view.component.MenuPanel;
import mas.ui.view.fight.AddFightPanel;
import mas.ui.view.fight.FightPanel;
import mas.ui.view.fighter.FighterPanel;
import mas.ui.viewmodel.ManageFightsViewModel;

public class MainScreen extends JFrame {

  private static MainScreen instance;

  private CardLayout cardLayout;
  private JPanel cardPanel;
  private MenuPanel menuPanel;

  private boolean editing = true; // TODO: Switch to false for prod

  public static MainScreen getInstance() {
    if (instance == null) {
      instance = new MainScreen();
    }
    return instance;
  }

  public MainScreen() {
    // Layout
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);

    // Card
    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    // Create Views
    AddFightPanel addFightPanel = new AddFightPanel(this::switchView);
    addFightPanel.setViewModel(new ManageFightsViewModel());
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

  private void switchView(String viewName) {
    cardLayout.show(cardPanel, viewName);
  }

  public void toggleEditing() {
    this.editing = !this.editing;
  }

  public boolean isEditing() {
    return editing;
  }
}
