package mas.ui.theme;

import java.awt.Font;

/**
 * Fonts class defines the font styles used throughout the application. It includes styles for
 * headings, body text, captions, buttons
 */
public class Fonts {

  // Base font family
  private static final String BASE_FONT = "Arial";

  // Headings
  public static final Font TITLE = new Font(BASE_FONT, Font.BOLD, 21);
  public static final Font SUBTITLE = new Font(BASE_FONT, Font.BOLD, 19);
  public static final Font SECTION = new Font(BASE_FONT, Font.PLAIN, 17);

  // Body
  public static final Font BODY = new Font(BASE_FONT, Font.PLAIN, 15);
  public static final Font BODY_BOLD = new Font(BASE_FONT, Font.BOLD, 15);

  // Captions and Metadata
  public static final Font SMALL = new Font(BASE_FONT, Font.PLAIN, 12);
  public static final Font SMALL_BOLD = new Font(BASE_FONT, Font.BOLD, 12);

  // Buttons
  public static final Font BUTTON = new Font(BASE_FONT, Font.BOLD, 14);
}
