package mas.model.attribute;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an address with street number, street name, city, state, and zip code. This class is
 * immutable and provides validation for its fields.
 */
public class Address implements Serializable {

  private final int streetNumber;
  private final String streetName;
  private final String city;
  private final String state;
  private final String zip;

  /**
   * Private constructor to create an Address instance. This constructor is private to enforce the
   * use of the static factory method `of`.
   *
   * @param streetNumber the street number
   * @param streetName the street name
   * @param city the city
   * @param state the state
   * @param zip the zip code
   */
  private Address(int streetNumber, String streetName, String city, String state, String zip) {

    this.streetNumber = streetNumber;
    this.streetName = streetName;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  /**
   * Static factory method to create an Address instance. This method validates the input parameters
   * and returns a new Address instance if they are valid.
   *
   * @param streetNumber the street number
   * @param streetName the street name
   * @param city the city
   * @param state the state
   * @param zip the zip code
   * @return a new Address instance
   * @throws IllegalArgumentException if any of the parameters are invalid
   */
  public static Address of(
      int streetNumber, String streetName, String city, String state, String zip) {
    validate(streetNumber, streetName, city, state, zip);
    return new Address(streetNumber, streetName, city, state, zip);
  }

  /**
   * Validates the input parameters for creating an Address instance.
   *
   * @param streetNumber the street number
   * @param streetName the street name
   * @param city the city
   * @param state the state
   * @param zip the zip code
   * @throws IllegalArgumentException if any of the parameters are invalid
   */
  private static void validate(
      int streetNumber, String streetName, String city, String state, String zip) {

    if (streetNumber < 0) {
      throw new IllegalArgumentException("streetName cannot be negative");
    }

    if (streetName == null || streetName.isEmpty()) {
      throw new IllegalArgumentException("streetName cannot be empty");
    }

    if (city == null || city.isEmpty()) {
      throw new IllegalArgumentException("city cannot be empty");
    }

    if (state == null || state.isEmpty()) {
      throw new IllegalArgumentException("state cannot be empty");
    }

    if (zip == null || zip.isEmpty()) {
      throw new IllegalArgumentException("zip cannot be empty");
    }
  }

  /*
   * Getters for the address fields.
   */
  public int getStreetNumber() {
    return streetNumber;
  }

  /**
   * Gets the street name of the address.
   *
   * @return the street name
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * Gets the city of the address.
   *
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * Gets the state of the address.
   *
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * Gets the zip code of the address.
   *
   * @return the zip code
   */
  public String getZip() {
    return zip;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Address address = (Address) obj;
    return streetNumber == address.streetNumber
        && streetName.equals(address.streetName)
        && city.equals(address.city)
        && state.equals(address.state)
        && zip.equals(address.zip);
  }

  @Override
  public int hashCode() {
    return Objects.hash(streetNumber, streetName, city, state, zip);
  }

  @Override
  public String toString() {
    return "{"
        + "streetNumber="
        + streetNumber
        + ", streetName='"
        + streetName
        + '\''
        + ", city='"
        + city
        + '\''
        + ", state='"
        + state
        + '\''
        + ", zip='"
        + zip
        + '\''
        + '}';
  }
}
