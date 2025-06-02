package mas.model.data;

import java.io.*;
import java.util.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class ObjectExtent implements Serializable {
  private static Map<Class, List> extent = new HashMap<>();
  public static final String EXTENT_FILE_PATH = "extent.ser";

  public ObjectExtent() {
    addToExtent();
  }

  protected void addToExtent() {
    List list = extent.computeIfAbsent(this.getClass(), _ -> new ArrayList<>());
    list.add(this);
  }

  protected void removeFromExtent() {
    List list = extent.get(this.getClass());
    if (list != null) {
      list.remove(this);
    }
  }

  public static <T> List<T> getExtent(Class<T> c) {
    extent.computeIfAbsent(c, _ -> new ArrayList<>());
    return Collections.unmodifiableList(extent.get(c));
  }

  public static void saveExtent() throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXTENT_FILE_PATH))) {
      oos.writeObject(extent);
    }
  }

  public static void loadExtent() throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXTENT_FILE_PATH))) {
      extent = (Map<Class, List>) ois.readObject();
    }
  }
}
