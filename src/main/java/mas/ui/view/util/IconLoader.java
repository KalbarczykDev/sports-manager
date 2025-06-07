package mas.ui.view.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.*;

/**
 * IconLoader is a utility class for loading icons from resources. It provides methods to load icons
 * with specified dimensions and optional tinting.
 */
public class IconLoader {

  /**
   * Loads an icon from the specified path with the given width and height, and applies an optional
   * tint color.
   *
   * @param path the path to the icon resource
   * @param width the desired width of the icon
   * @param height the desired height of the icon
   * @param tintColor the color to tint the icon, or null if no tinting is required
   * @return a scaled and optionally tinted ImageIcon
   */
  public static ImageIcon load(String path, int width, int height) {
    return load(path, width, height, null);
  }

  /**
   * Loads an icon from the specified path with the given width, height, and optional tint color.
   *
   * @param path the path to the icon resource
   * @param width the desired width of the icon
   * @param height the desired height of the icon
   * @param tintColor the color to tint the icon, or null if no tinting is required
   * @return a scaled and optionally tinted ImageIcon
   */
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

  /**
   * Loads an icon from the specified path without scaling or tinting.
   *
   * @param path the path to the icon resource
   * @return an ImageIcon loaded from the specified path
   */
  public static ImageIcon load(String path) {
    URL resource = IconLoader.class.getResource(path);
    if (resource == null) {
      throw new IllegalArgumentException("Icon not found: " + path);
    }
    return new ImageIcon(resource);
  }

  /**
   * Tints an ImageIcon with the specified color.
   *
   * @param icon the ImageIcon to be tinted
   * @param color the color to tint the icon
   * @return a new ImageIcon with the applied tint
   */
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
