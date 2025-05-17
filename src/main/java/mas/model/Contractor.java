package mas.model;

import java.util.*;
import mas.model.abstraction.Company;
import mas.model.abstraction.IPerson;
import mas.model.attribute.Address;

enum ContractorType {
  Influencer,
  Commentator
}

public class Contractor extends Company implements IPerson {

  private String name;
  private String surname;
  private String serviceDescription;

  private String socialMediaHandle;
  private String language;
  private EnumSet<ContractorType> contractorTypes;
  private List<Fight> fights = new ArrayList<>();

  Contractor(
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

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    if (!contractorTypes.contains(ContractorType.Commentator)) {
      throw new IllegalStateException("Cannot set language for non-commentator");
    }

    if (language == null || language.isEmpty()) {
      throw new IllegalArgumentException("Language cannot be null or empty");
    }
    this.language = language;
  }

  public String getSocialMediaHandle() {
    return socialMediaHandle;
  }

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

  public void setContractorTypes(EnumSet<ContractorType> contractorTypes) {
    if (contractorTypes == null) {
      throw new IllegalArgumentException("contractorTypes is null");
    }
    this.contractorTypes = contractorTypes;
  }

  public String getServiceDescription() {
    return serviceDescription;
  }

  public void setServiceDescription(String serviceDescription) {
    if (serviceDescription.isBlank()) {
      throw new IllegalArgumentException("Service description is empty");
    }
    this.serviceDescription = serviceDescription;
  }

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

  public void removeFight(Fight fight) {
    if (!contractorTypes.contains(ContractorType.Commentator)) {
      throw new IllegalArgumentException("Contractor is not Commentator");
    }
    if (fights.contains(fight)) {
      fights.remove(fight);
      fight.removeCommentator(this);
    }
  }

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
