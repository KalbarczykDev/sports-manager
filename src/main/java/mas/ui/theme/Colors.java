package mas.ui.theme;

import java.awt.Color;

/**
 * Colors class defines the color scheme for the application. It includes base colors, button
 * styles, card and panel backgrounds, and status colors.
 */
public class Colors {

  // Base Colors
  public static final Color BACKGROUND = Color.decode("#F4F4F6");
  public static final Color PRIMARY = Color.decode("#FFEB3B");
  public static final Color ACCENT = Color.decode("#2196F3");
  public static final Color TITLE = Color.decode("#000000");
  public static final Color TEXT = Color.decode("#424242");

  // Buttons
  public static final Color BUTTON_BG = BACKGROUND;
  public static final Color BUTTON_TEXT = TEXT;
  public static final Color BUTTON_HOVER = Color.decode("#E0E0E0");
  public static final Color BUTTON_SELECTED = Color.decode("#E0E0E0");

  // Cards, Panels
  public static final Color SHADOW = Color.decode("#000000");
  public static final Color CARD_BACKGROUND = BACKGROUND;
  public static final Color PANEL_BACKGROUND = BUTTON_HOVER;
  public static final Color BORDER = Color.decode("#000000");

  // Status Colors
  public static final Color SUCCESS = Color.decode("#4CAF50");
  public static final Color ERROR = Color.decode("#F44336");
  public static final Color WARNING = Color.decode("#FF9800");
}
