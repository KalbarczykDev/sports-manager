package mas.ui.view.component;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import mas.ui.theme.Colors;
import mas.ui.theme.Fonts;
import mas.ui.view.util.IconLoader;

public class MenuPanel extends JPanel {

  AppLogoPanel appLogoPanel;
  NavigationMenu navigationMenu;

  public MenuPanel(Consumer<String> switchView) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(new EmptyBorder(16, 16, 16, 16));
    setBackground(Colors.BACKGROUND);

    // Init panels
    appLogoPanel = new AppLogoPanel();
    appLogoPanel.setAlignmentX(CENTER_ALIGNMENT);
    navigationMenu = new NavigationMenu(switchView);
    navigationMenu.setAlignmentX(CENTER_ALIGNMENT);

    // Separator
    JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
    separator.setAlignmentX(CENTER_ALIGNMENT);

    separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
    separator.setForeground(Colors.BORDER);

    // Add components
    add(appLogoPanel);
    add(Box.createVerticalStrut(12));
    add(separator);
    add(Box.createVerticalStrut(12));
    add(navigationMenu);
  }

  class NavigationMenu extends JPanel {
    public NavigationMenu(Consumer<String> switchView) {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      setBackground(Colors.BACKGROUND);

      add(
          new MenuButton(
              "Manage Fighters", "/icons/fighters-menu.png", e -> switchView.accept("fighters")));
      add(
          new MenuButton(
              "Manage Events", "/icons/event-menu.png", e -> switchView.accept("events")));
      add(
          new MenuButton(
              "Manage Fights", "/icons/fight-menu.png", e -> switchView.accept("fights")));
    }
  }

  class MenuButton extends JButton {
    public MenuButton(String title, String iconPath, ActionListener actionListener) {
      super(title, IconLoader.load(iconPath, 24, 24, Colors.BUTTON_TEXT));

      setAlignmentX(LEFT_ALIGNMENT);
      setBorder(new EmptyBorder(10, 16, 10, 16));

      setForeground(Colors.BUTTON_TEXT);
      setBackground(Colors.BACKGROUND);
      setFont(Fonts.BUTTON);
      setFocusPainted(false);
      setOpaque(true);
      setContentAreaFilled(true);

      setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));

      setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

      addActionListener(actionListener);

      addMouseListener(
          new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
              setBackground(Colors.BUTTON_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
              setBackground(Colors.BUTTON_BG);
            }
          });
    }
  }

  class AppLogoPanel extends JPanel {
    public AppLogoPanel() {
      setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
      setBackground(Colors.BACKGROUND);

      ImageIcon logo = IconLoader.load("/icons/app-logo.png", 50, 50);
      JLabel logoLabel = new JLabel(logo);

      JLabel appName = new JLabel("FEDMAN 2000");
      appName.setBorder(new EmptyBorder(8, 0, 8, 8));
      appName.setFont(Fonts.TITLE);
      appName.setForeground(Colors.TITLE);

      add(logoLabel);
      add(appName);
    }

    @Override
    public Dimension getMaximumSize() {
      return getPreferredSize();
    }
  }
}
