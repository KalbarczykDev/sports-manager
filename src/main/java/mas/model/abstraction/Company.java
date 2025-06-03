package mas.model.abstraction;

import java.util.regex.Pattern;
import mas.model.attribute.Address;
import mas.model.data.ObjectExtent;

/**
 * Abstract class representing a company in the system. It contains common attributes and methods
 * for all types of companies.
 */
public abstract class Company extends ObjectExtent {

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);
  private String companyName;
  private String contactEmail;
  private String contactPhoneNumber;
  private Address address;
  private String nip;

  public String getCompanyName() {
    return companyName;
  }

  /**
   * Sets the name of the company. The name must not be null or empty.
   *
   * @param companyName the name to set
   * @throws IllegalArgumentException if the name is null or empty
   */
  public void setCompanyName(String companyName) {

    if (companyName == null) {
      throw new IllegalArgumentException("companyName is null");
    }

    if (companyName.isEmpty()) {
      throw new IllegalArgumentException("companyName is empty");
    }

    this.companyName = companyName;
  }

  /**
   * Gets the contact email for the company.
   *
   * @return the contact email
   */
  public String getContactEmail() {
    return contactEmail;
  }

  /**
   * Sets the contact email for the company. The email must not be null, empty, and must match a
   * specific pattern.
   *
   * @param contactEmail the email to set
   * @throws IllegalArgumentException if the email is null, empty, or invalid
   */
  public void setContactEmail(String contactEmail) {
    if (contactEmail == null) {
      throw new IllegalArgumentException("Email is null");
    }
    if (contactEmail.isEmpty()) {
      throw new IllegalArgumentException("Email is empty");
    }
    if (!EMAIL_PATTERN.matcher(contactEmail).matches()) {
      throw new IllegalArgumentException("Invalid email format: " + contactEmail);
    }

    this.contactEmail = contactEmail;
  }

  /**
   * Gets the contact phone number for the company.
   *
   * @return the contact phone number
   */
  public String getContactPhoneNumber() {
    return contactPhoneNumber;
  }

  /**
   * Sets the contact phone number for the company. The phone number must not be null, empty, and
   * must match a specific pattern (digits with an optional leading '+' sign).
   *
   * @param contactPhoneNumber the phone number to set
   * @throws IllegalArgumentException if the phone number is null, empty, or invalid
   */
  public void setContactPhoneNumber(String contactPhoneNumber) {

    if (contactPhoneNumber == null) {
      throw new IllegalArgumentException("Phone number is null");
    }
    if (contactPhoneNumber.isEmpty()) {
      throw new IllegalArgumentException("Phone number is empty");
    }
    if (!contactPhoneNumber.matches("\\+?[0-9]+")) {
      throw new IllegalArgumentException("Phone number is invalid");
    }

    this.contactPhoneNumber = contactPhoneNumber;
  }

  /**
   * Gets the address of the company.
   *
   * @return the address of the company
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Sets the address of the company. The address cannot be null.
   *
   * @param address the address to set
   * @throws IllegalArgumentException if the address is null
   */
  public void setAddress(Address address) {

    if (address == null) {
      throw new IllegalArgumentException("Address is null");
    }

    this.address = address;
  }

  /**
   * Gets the NIP (Tax Identification Number) of the company.
   *
   * @return the NIP of the company
   */
  public String getNip() {
    return nip;
  }

  /**
   * Sets the NIP (Tax Identification Number) for the company. The NIP must be unique across all
   * companies.
   *
   * @param nip the NIP to set
   * @throws IllegalArgumentException if the NIP is null, empty, or already taken
   */
  public void setNip(String nip) {
    if (nip == null) {
      throw new IllegalArgumentException("Nip cannot be null");
    }
    if (nip.isEmpty()) {
      throw new IllegalArgumentException("NIP cannot be empty");
    }

    boolean exists =
        getExtent(getClass()).stream().anyMatch(p -> p != this && p.getNip().equals(nip));

    if (exists) {
      throw new IllegalArgumentException("NIP already taken");
    }

    this.nip = nip;
  }
}
