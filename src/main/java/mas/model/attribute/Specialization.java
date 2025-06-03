package mas.model.attribute;

/**
 * Enum representing different specializations coach can have. Each specialization is represented by
 * a string value.
 */
public enum Specialization {
  STRIKING("striking"),
  GRAPPLING("grappling"),
  ENDURANCE("endurance");

  private final String specialization;

  /**
   * Constructor for Specialization enum.
   *
   * @param specialization the string representation of the specialization
   */
  Specialization(String specialization) {
    this.specialization = specialization;
  }

  /**
   * Gets the string representation of the specialization.
   *
   * @return the string representation of the specialization
   */
  public String getSpecialization() {
    return specialization;
  }
}
