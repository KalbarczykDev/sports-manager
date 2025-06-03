package mas.model;

import java.util.*;
import mas.model.abstraction.Company;
import mas.model.abstraction.IPerson;
import mas.model.attribute.Address;

/**
 * Represents the types of contractors in the system. A contractor can be either an influencer or a
 * commentator, each with specific attributes and behaviors.
 */
enum ContractorType {
  Influencer,
  Commentator
}

/**
 * Represents a contractor in the system, extending the Company class and implementing the IPerson
 * interface. A contractor can be an influencer or a commentator, with specific attributes and
 * behaviors associated with each type.
 */
public class Contractor extends Company implements IPerson {

  private String name;
  private String surname;
  private String serviceDescription;

  private String socialMediaHandle;
  private String language;
  private EnumSet<ContractorType> contractorTypes;
  private List<Fight> fights = new ArrayList<>();

  /**
   * Constructs a Contractor with the specified details. The contractor can be an influencer or a
   * commentator, and the appropriate attributes are set based on the contractor types.
   *
   * @param name the name of the contractor
   * @param surname the surname of the contractor
   * @param address the address of the contractor
   * @param companyName the name of the company associated with the contractor
   * @param contactEmail the contact email of the contractor
   * @param contactPhoneNumber the contact phone number of the contractor
   * @param nip the NIP (tax identification number) of the contractor
   * @param contractorTypes the types of contractor (influencer, commentator)
   * @param socialMediaHandle the social media handle if influencer
   * @param language the language if commentator
   */
  public Contractor(
      String name,
      String surname,
      Address address,
      String companyName,
      String contactEmail,
      String contactPhoneNumber,
      String nip,
      EnumSet<ContractorType> contractorTypes,
      String socialMediaHandle,
      String language) {
    try {
      setName(name);
      setSurname(surname);
      setCompanyName(companyName);
      setContactEmail(contactEmail);
      setContactPhoneNumber(contactPhoneNumber);
      setAddress(address);
      setNip(nip);
      setContractorTypes(contractorTypes);

      if (contractorTypes.contains(ContractorType.Influencer)) {
        setSocialMediaHandle(socialMediaHandle);
      }

      if (contractorTypes.contains(ContractorType.Commentator)) {
        setLanguage(language);
      }

    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Contractor not created: " + e.getMessage());
    }
  }

  /** Gets the language of the contractor if they are a commentator. */
  public String getLanguage() {
    return language;
  }

  /**
   * Sets the language of the contractor if they are a commentator. Throws an exception if the
   * contractor is not a commentator or if the language is null or empty.
   *
   * @param language the language to set
   */
  public void setLanguage(String language) {
    if (!contractorTypes.contains(ContractorType.Commentator)) {
      throw new IllegalStateException("Cannot set language for non-commentator");
    }

    if (language == null || language.isEmpty()) {
      throw new IllegalArgumentException("Language cannot be null or empty");
    }
    this.language = language;
  }

  /**
   * Gets the social media handle of the contractor if they are an influencer.
   *
   * @return the social media handle
   */
  public String getSocialMediaHandle() {
    return socialMediaHandle;
  }

  /**
   * Sets the social media handle of the contractor if they are an influencer. Throws an exception
   * if the contractor is not an influencer or if the social media handle is null or empty.
   *
   * @param socialMediaHandle the social media handle to set
   */
  public void setSocialMediaHandle(String socialMediaHandle) {
    if (!contractorTypes.contains(ContractorType.Influencer)) {
      throw new IllegalStateException("Cannot set social media handle for non-influencer");
    }
    if (socialMediaHandle == null || socialMediaHandle.isEmpty()) {
      throw new IllegalArgumentException("Social media handle cannot be null or empty");
    }
    this.socialMediaHandle = socialMediaHandle;
  }

  public EnumSet<ContractorType> getContractorTypes() {
    return contractorTypes;
  }

  /**
   * Sets the contractor types for this contractor. Throws an exception if the contractorTypes is
   * null.
   *
   * @param contractorTypes the set of contractor types to set
   */
  public void setContractorTypes(EnumSet<ContractorType> contractorTypes) {
    if (contractorTypes == null) {
      throw new IllegalArgumentException("contractorTypes is null");
    }
    this.contractorTypes = contractorTypes;
  }

  /**
   * Gets the service description of the contractor. This is applicable if the contractor is a
   * commentator.
   *
   * @return the service description
   */
  public String getServiceDescription() {
    return serviceDescription;
  }

  /**
   * Sets the service description of the contractor. Throws an exception if the service description
   * is empty.
   *
   * @param serviceDescription the service description to set
   */
  public void setServiceDescription(String serviceDescription) {
    if (serviceDescription.isBlank()) {
      throw new IllegalArgumentException("Service description is empty");
    }
    this.serviceDescription = serviceDescription;
  }

  /**
   * Adds a fight to the contractor's list of fights. This method can only be called if the
   * contractor is a commentator.
   *
   * @param fight the fight to add
   * @throws IllegalArgumentException if the contractor is not a commentator or if the fight is null
   */
  public void addFight(Fight fight) {
    if (!contractorTypes.contains(ContractorType.Commentator)) {
      throw new IllegalArgumentException("Contractor is not Commentator");
    }

    if (fight == null) {
      throw new IllegalArgumentException("Fight cannot be null");
    }
    if (!fights.contains(fight)) {
      fights.add(fight);
      fight.addCommentator(this);
    }
  }

  /**
   * Adds a fight to the contractor's list of fights. This method can only be called if the
   * contractor is a commentator.
   *
   * @param fight the fight to add
   * @throws IllegalArgumentException if the contractor is not a commentator or if the fight is null
   */
  public void removeFight(Fight fight) {
    if (!contractorTypes.contains(ContractorType.Commentator)) {
      throw new IllegalArgumentException("Contractor is not Commentator");
    }
    if (fights.contains(fight)) {
      fights.remove(fight);
      fight.removeCommentator(this);
    }
  }

  /**
   * Returns an unmodifiable list of fights associated with the contractor. This method can only be
   * called if the contractor is a commentator.
   *
   * @return an unmodifiable list of fights
   * @throws IllegalArgumentException if the contractor is not a commentator
   */
  public List<Fight> getFights() {
    if (!contractorTypes.contains(ContractorType.Commentator)) {
      throw new IllegalArgumentException("Contractor is not Commentator");
    }

    return Collections.unmodifiableList(fights);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    if (name == null || name.isEmpty()) {
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
    if (surname == null || surname.isEmpty()) {
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
    if (address == null) {
      throw new IllegalArgumentException("Contractor address is null");
    }

    super.setAddress(address);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Contractor{");

    sb.append("name=").append(name).append(", surname=").append(surname);

    if (contractorTypes.contains(ContractorType.Influencer)) {
      sb.append(", role=Influencer");
    }

    if (contractorTypes.contains(ContractorType.Commentator)) {
      sb.append(", role=Commentator");
    }

    if (serviceDescription != null && !serviceDescription.isEmpty()) {
      sb.append(", serviceDescription=").append(serviceDescription);
    }

    sb.append('}');
    return sb.toString();
  }
}
