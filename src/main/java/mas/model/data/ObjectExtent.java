package mas.model.data;

import java.io.*;
import java.util.*;

/**
 * Abstract class representing an object extent, which is a collection of all instances of a class.
 * This class provides methods to add, remove, and retrieve instances, as well as to save and load
 * the extent to and from a file.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class ObjectExtent implements Serializable {
  private static Map<Class, List> extent = new HashMap<>();
  public static final String EXTENT_FILE_PATH = "extent.ser";

  /** Default constructor that adds the current instance to the extent. */
  public ObjectExtent() {
    addToExtent();
  }

  /**
   * Finalize method that removes the current instance from the extent when it is garbage collected.
   */
  protected void addToExtent() {
    List list = extent.computeIfAbsent(this.getClass(), _ -> new ArrayList<>());
    list.add(this);
  }

  /**
   * Removes the current instance from the extent. This method should be called when the instance is
   * no longer needed.
   */
  protected void removeFromExtent() {
    List list = extent.get(this.getClass());
    if (list != null) {
      list.remove(this);
    }
  }

  /**
   * Retrieves the extent for the specified class.
   *
   * @param c the class for which to retrieve the extent
   * @param <T> the type of the class
   * @return an unmodifiable list of instances of the specified class
   */
  public static <T> List<T> getExtent(Class<T> c) {
    extent.computeIfAbsent(c, _ -> new ArrayList<>());
    return Collections.unmodifiableList(extent.get(c));
  }

  /**
   * Retrieves the extent for the current instance's class.
   *
   * @param <T> the type of the class
   * @return an unmodifiable list of instances of the current instance's class
   */
  public static void saveExtent() throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXTENT_FILE_PATH))) {
      oos.writeObject(extent);
    }
  }

  /**
   * Loads the extent from a file. This method should be called to restore the extent after it has
   * been saved.
   *
   * @throws IOException if an I/O error occurs while reading the file
   * @throws ClassNotFoundException if the class of a serialized object cannot be found
   */
  public static void loadExtent() throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXTENT_FILE_PATH))) {
      extent = (Map<Class, List>) ois.readObject();
    }
  }
}
