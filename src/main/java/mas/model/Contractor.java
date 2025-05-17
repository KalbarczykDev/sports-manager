package mas.model;

import java.util.Optional;

import mas.model.abstraction.Company;
import mas.model.abstraction.IPerson;
import mas.model.attribute.Address;


public class Contractor extends Company implements IPerson {

  private boolean rolesFinalized = false;

  private String name;
  private String surname;
  private String serviceDescription;

  private Commentator commentatorRole;
  private Influencer influencerRole;

  Contractor(
      String name,
      String surname,
      Address address,
      String companyName,
      String contactEmail,
      String contactPhoneNumber,
      String nip) {
    try {
      setName(name);
      setSurname(surname);
      setCompanyName(companyName);
      setContactEmail(contactEmail);
      setContactPhoneNumber(contactPhoneNumber);
      setAddress(address);
      setNip(nip);
    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Contractor not created: " + e.getMessage());
    }
  }

  public void assignCommentatorRole(String language) {
    if (rolesFinalized) {
      throw new IllegalStateException("Cannot add roles after they have been finalized.");
    }

    this.commentatorRole = Commentator.of(this, language);
  }

  public Optional<Commentator> getCommentatorRole() {
    return Optional.ofNullable(commentatorRole);
  }

  public void removeCommentatorRole() {
    if (rolesFinalized) {
      throw new IllegalStateException("Cannot remove roles after they have been finalized.");
    }

    if (commentatorRole != null) {
      commentatorRole.removeFromExtent();
      commentatorRole = null;
    }
  }

  public void assignInfluencerRole(String socialMediaHandle) {
    if (rolesFinalized) {
      throw new IllegalStateException("Cannot add roles after they have been finalized.");
    }

    this.influencerRole = Influencer.of(this, socialMediaHandle);
  }

  public Optional<Influencer> getInfluencerRole() {
    return Optional.ofNullable(influencerRole);
  }

  public void removeInfluencerRole() {

    if (rolesFinalized) {
      throw new IllegalStateException("Cannot remove roles after they have been finalized.");
    }

    if (influencerRole != null) {
      influencerRole.removeFromExtent();
      influencerRole = null;
    }
  }

  public String getServiceDescription() {

    String description = "";

    if (influencerRole != null) {
      description += "Is Influencer";
    }

    if (commentatorRole != null) {
      description += " and Commentator";
    }

    if (serviceDescription != null) {
      description += " and " + serviceDescription;
    }

    return description;
  }

  public void setServiceDescription(String serviceDescription) {
    if (serviceDescription == null) {
            throw new IllegalArgumentException("Service description is null");
        }
        if (serviceDescription.isEmpty()) {
            throw new IllegalArgumentException("Service description is empty");
        }
    this.serviceDescription = serviceDescription;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    if(name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Contractor name is null or empty");
    }
    this.name = name;
  }

  @Override
  public String getSurname() {
    return surname;
  }

  @Override
  public void setSurname(String surname) {
    if(surname == null || surname.isEmpty()) {
      throw new IllegalArgumentException("Contractor surname is null or empty");
    }

    this.surname = surname;
  }

  @Override
  public Address getAddress() {
    return super.getAddress();
  }

  @Override
  public void setAddress(Address address) {
    if(address == null) {
      throw new IllegalArgumentException("Contractor address is null");
    }

    super.setAddress(address);
  }

  @Override
  public String toString() {
    return "Contractor{"
        + "name="
        + name
        + ", surname="
        + surname
        + ", serviceDescription="
        + getServiceDescription()
        + '}';
  }

  void finalizeRoles() {
    this.rolesFinalized = true;
  }
}
