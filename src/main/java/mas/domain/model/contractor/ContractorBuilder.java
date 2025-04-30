package mas.domain.model.contractor;

import mas.domain.model.Influencer.SocialMediaHandle;
import mas.domain.model.comentator.Language;
import mas.domain.model.company.CompanyName;
import mas.domain.model.company.Email;
import mas.domain.model.company.PhoneNumber;
import mas.domain.model.person.Name;
import mas.domain.model.person.Surname;
import mas.domain.model.shared.Address;
import mas.util.Util;

public class ContractorBuilder {
    private final Name name;
    private final Surname surname;
    private final Address address;
    private final CompanyName companyName;
    private final Email contactEmail;
    private final PhoneNumber contactPhoneNumber;

    private Language commentatorLanguage;
    private SocialMediaHandle socialMediaHandle;
    private ServiceDescription serviceDescription;

    private ContractorBuilder(Name name, Surname surname, Address address, CompanyName companyName,
                              Email contactEmail, PhoneNumber contactPhoneNumber) {

        Util.require(address != null, "address cannot be null");
        Util.require(name != null, "name cannot be null");
        Util.require(surname != null, "surname cannot be null");
        Util.require(companyName != null, "companyName cannot be null");
        Util.require(contactEmail != null, "contactEmail cannot be null");
        Util.require(contactPhoneNumber != null, "contactPhoneNumber cannot be null");

        this.name = name;
        this.surname = surname;
        this.address = address;
        this.companyName = companyName;
        this.contactEmail = contactEmail;
        this.contactPhoneNumber = contactPhoneNumber;

    }

    public static ContractorBuilder forContractor(Name name, Surname surname, Address address, CompanyName companyName,
                                                  Email contactEmail, PhoneNumber contactPhoneNumber) {

        return new ContractorBuilder(name, surname, address, companyName, contactEmail, contactPhoneNumber);
    }

    public ContractorBuilder asCommentator(Language language) {
        this.commentatorLanguage = language;
        return this;
    }

    public ContractorBuilder asInfluencer(SocialMediaHandle socialMediaHandle) {
        this.socialMediaHandle = socialMediaHandle;
        return this;
    }

    public ContractorBuilder withServiceDescription(ServiceDescription description) {
        this.serviceDescription = description;
        return this;
    }

    public Contractor build() {
        Contractor contractor = new Contractor(
                name,
                surname,
                address,
                companyName,
                contactEmail,
                contactPhoneNumber
        );

        if (commentatorLanguage != null) {
            contractor.assignCommentatorRole(commentatorLanguage);
        }

        if (socialMediaHandle != null) {
            contractor.assignInfluencerRole(socialMediaHandle);
        }

        if (serviceDescription != null) {
            contractor.setServiceDescription(serviceDescription);
        }

        contractor.finalizeRoles();

        return contractor;
    }
}
