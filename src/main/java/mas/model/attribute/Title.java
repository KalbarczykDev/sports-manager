package mas.model.attribute;

import java.io.Serializable;

/**
 * Represents a title with a name and description. This class is immutable and provides validation
 * for its fields.
 */
public final class Title implements Serializable {

  private final String name;
  private final String description;

  /**
   * Private constructor to create a Title instance. This constructor is private to enforce the use
   * of the static factory method `of`.
   *
   * @param name the name of the title
   * @param description the description of the title
   */
  private Title(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Static factory method to create a Title instance. This method validates the input parameters
   * and returns a new Title instance if they are valid.
   *
   * @param name the name of the title
   * @param description the description of the title
   * @return a new Title instance
   * @throws IllegalArgumentException if any of the parameters are invalid
   */
  public static Title of(String name, String description) {
    validate(name, description);
    return new Title(name, description);
  }

  /**
   * Gets the name of the title.
   *
   * @return the name of the title
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the description of the title.
   *
   * @return the description of the title
   */
  public String getName() {
    return name;
  }

  /**
   * Validates the input parameters for creating a Title instance.
   *
   * @param name the name of the title
   * @param description the description of the title
   * @throws IllegalArgumentException if any of the parameters are invalid
   */
  private static void validate(String name, String description) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty");
    }

    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be empty");
    }
  }

  @Override
  public String toString() {
    return "{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
  }
}
