package mas.ui.view.util;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class IconLoader {

  public static ImageIcon load(String path, int width, int height) {

    System.out.println(path);

    URL resource = IconLoader.class.getResource(path);

    if (resource == null) {
      throw new IllegalArgumentException("Icon not found: " + path);
    }
    ImageIcon original = new ImageIcon(resource);
    Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(scaled);
  }

  public static ImageIcon load(String path) {
    URL resource = IconLoader.class.getResource(path);
    if (resource == null) {
      throw new IllegalArgumentException("Icon not found: " + path);
    }
    return new ImageIcon(resource);
  }
}
