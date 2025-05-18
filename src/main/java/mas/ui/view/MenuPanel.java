package mas.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import mas.ui.view.util.IconLoader;

public class MenuPanel extends JPanel {

  AppLogoPanel appLogoPanel;

  public MenuPanel() {

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(new EmptyBorder(8, 8, 8, 8));
    setBackground(Color.WHITE);

    // Init UI
    appLogoPanel = new AppLogoPanel();
    appLogoPanel.setAlignmentX(CENTER_ALIGNMENT);

    // Separator
    JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
    separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
    separator.setForeground(Color.GRAY);

    // Init navigation buttons
    ImageIcon menuIcon = IconLoader.load("/icons/icon.png", 24, 24);
    JButton goToFightersViewButton = new JButton("Manage Fighters", menuIcon);
    goToFightersViewButton.setAlignmentX(CENTER_ALIGNMENT);

    add(appLogoPanel);
    add(Box.createVerticalStrut(8));
    add(separator);
    add(Box.createVerticalStrut(8));
    add(goToFightersViewButton);
  }

  class AppLogoPanel extends JPanel {
    public AppLogoPanel() {
      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      setBackground(Color.WHITE);

      ImageIcon logo = IconLoader.load("/icons/app-logo.png", 50, 50);

      JLabel logoLabel = new JLabel(logo);
      JLabel appName = new JLabel("FEDMAN 2000");
      appName.setBorder(new EmptyBorder(8, 8, 8, 8));
      appName.setFont(new Font("Arial", Font.BOLD, 16));
      appName.setForeground(Color.DARK_GRAY);

      add(logoLabel);
      add(appName);
    }

    @Override
    public Dimension getMaximumSize() {
      return getPreferredSize();
    }
  }
}
