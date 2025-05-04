package mas.domain.model.company;

import mas.domain.model.shared.Address;
import mas.infrastructure.repository.ObjectExtent;
import mas.util.Util;

public abstract class Company extends ObjectExtent {
  private CompanyName companyName;
  private Email contactEmail;
  private PhoneNumber contactPhoneNumber;
  private Address address;
  private NIP nip;

  public CompanyName getCompanyName() {
    return companyName;
  }

  public void setCompanyName(CompanyName companyName) {
    Util.require(companyName != null, "Company name cannot be null");
    this.companyName = companyName;
  }

  public Email getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(Email contactEmail) {
    Util.require(contactEmail != null, "Contact email cannot be null");
    this.contactEmail = contactEmail;
  }

  public PhoneNumber getContactPhoneNumber() {
    return contactPhoneNumber;
  }

  public void setContactPhoneNumber(PhoneNumber contactPhoneNumber) {
    Util.require(contactPhoneNumber != null, "Contact phone number cannot be null");
    this.contactPhoneNumber = contactPhoneNumber;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    Util.require(address != null, "Address cannot be null");
    this.address = address;
  }

  public NIP getNip() {
    return nip;
  }

  public void setNip(NIP nip) {
    Util.require(nip != null, "NIP cannot be null");
    // unique

    boolean exists =
        getExtent(getClass()).stream().anyMatch(p -> p != this && p.getNip().equals(nip));

    Util.require(!exists, "NIP already taken");

    this.nip = nip;
  }
}
