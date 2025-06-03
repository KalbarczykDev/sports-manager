package mas.ui.theme;

import java.awt.Font;

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

  // Optional: Monospaced for code or debug UI
  public static final Font MONO = new Font("Consolas", Font.PLAIN, 13);
}
