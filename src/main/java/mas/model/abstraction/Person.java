package mas.model.abstraction;

import java.util.Objects;
import mas.model.attribute.Address;
import mas.model.data.ObjectExtent;

/**
 * Abstract class representing a person in the system. It contains common attributes and methods for
 * all types of persons.
 */
public abstract class Person extends ObjectExtent implements IPerson {
  private String name;
  private String surname;
  private Address address;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank");
    }
    this.name = name;
  }

  @Override
  public String getSurname() {
    return surname;
  }

  @Override
  public void setSurname(String surname) {
    if (surname == null || surname.isBlank()) {
      throw new IllegalArgumentException("Surname cannot be null or blank");
    }
    this.surname = surname;
  }

  @Override
  public Address getAddress() {
    return address;
  }

  @Override
  public void setAddress(Address address) {

    if (address == null) {
      throw new IllegalArgumentException("Address cannot be null");
    }

    this.address = address;
  }

  @Override
  public void removeFromExtent() {
    super.removeFromExtent();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "{"
        + "name='"
        + name
        + '\''
        + ", surname='"
        + surname
        + '\''
        + ", address="
        + address
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person other)) return false;
    return name.equals(other.name) && surname.equals(other.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname);
  }
}

