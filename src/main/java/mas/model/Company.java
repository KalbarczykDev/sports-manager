package mas.model;

import mas.model.attribute.Address;
import mas.model.data.ObjectExtent;
import mas.util.Util;

import java.util.regex.Pattern;

public abstract class Company extends ObjectExtent {

  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);
  private String companyName;
  private String contactEmail;
  private String contactPhoneNumber;
  private Address address;
  private String nip;

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    Util.require(companyName != null, "Company name cannot be null");
    Util.require(!companyName.isEmpty(), "Company name cannot be empty");
    this.companyName = companyName;
  }

  public String getContactEmail() {
    return contactEmail;
  }

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

  public String getContactPhoneNumber() {
    return contactPhoneNumber;
  }

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

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    Util.require(address != null, "Address cannot be null");

    this.address = address;
  }

  public String getNip() {
    return nip;
  }

  public void setNip(String nip) {
    Util.require(nip != null, "NIP cannot be null");
    Util.require(!nip.isEmpty(),"NIP cannot be empty");

    boolean exists =
        getExtent(getClass()).stream().anyMatch(p -> p != this && p.getNip().equals(nip));

    Util.require(!exists, "NIP already taken");



    this.nip = nip;
  }
}
