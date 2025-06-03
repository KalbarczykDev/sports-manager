package mas.model.abstraction;

import mas.model.attribute.Address;

/**
 * Interface representing a person in the system. It contains methods to get and set the person's
 * name, surname, and address.
 */
public interface IPerson {
  /**
   * Gets the name of the person.
   *
   * @return the name of the person
   */
  String getName();

  /**
   * Sets the name of the person. The name must not be null or empty.
   *
   * @param name the name to set
   * @throws IllegalArgumentException if the name is null or empty
   */
  void setName(String name);

  /**
   * Gets the surname of the person.
   *
   * @return the surname of the person
   */
  String getSurname();

  /**
   * Sets the surname of the person. The surname must not be null or empty.
   *
   * @param surname the surname to set
   * @throws IllegalArgumentException if the surname is null or empty
   */
  void setSurname(String surname);

  /**
   * Gets the address of the person.
   *
   * @return the address of the person
   */
  Address getAddress();

  /**
   * Sets the address of the person. The address must not be null.
   *
   * @param address the address to set
   * @throws IllegalArgumentException if the address is null
   */
  void setAddress(Address address);
}
