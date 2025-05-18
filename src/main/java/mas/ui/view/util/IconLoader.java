package mas.ui.view.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.*;

public class IconLoader {

  public static ImageIcon load(String path, int width, int height) {
    return load(path, width, height, null);
  }

  public static ImageIcon load(String path, int width, int height, Color tintColor) {
    URL resource = IconLoader.class.getResource(path);
    if (resource == null) {
      throw new IllegalArgumentException("Icon not found: " + path);
    }

    ImageIcon original = new ImageIcon(resource);
    Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaled);

    if (tintColor != null) {
      return tintIcon(scaledIcon, tintColor);
    } else {
      return scaledIcon;
    }
  }

  public static ImageIcon load(String path) {
    URL resource = IconLoader.class.getResource(path);
    if (resource == null) {
      throw new IllegalArgumentException("Icon not found: " + path);
    }
    return new ImageIcon(resource);
  }

  public static ImageIcon tintIcon(ImageIcon icon, Color color) {
    Image image = icon.getImage();
    BufferedImage tinted =
        new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = tinted.createGraphics();
    g2d.drawImage(image, 0, 0, null);

    g2d.setComposite(AlphaComposite.SrcAtop);
    g2d.setColor(color);
    g2d.fillRect(0, 0, tinted.getWidth(), tinted.getHeight());

    g2d.dispose();
    return new ImageIcon(tinted);
  }
}
