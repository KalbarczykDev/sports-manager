package mas.domain.model.contractor;

import mas.domain.model.Influencer.Influencer;
import mas.domain.model.Influencer.SocialMediaHandle;
import mas.domain.model.comentator.Commentator;
import mas.domain.model.comentator.Language;
import mas.domain.model.company.Company;
import mas.domain.model.company.CompanyName;
import mas.domain.model.company.Email;
import mas.domain.model.company.PhoneNumber;
import mas.domain.model.person.IPerson;
import mas.domain.model.person.Name;
import mas.domain.model.person.Surname;
import mas.domain.model.shared.Address;
import mas.util.Util;

import java.util.Optional;

public class Contractor extends Company implements IPerson {

    private boolean rolesFinalized = false;

    private Name name;
    private Surname surname;
    private ServiceDescription serviceDescription;


    private Commentator commentatorRole;
    private Influencer influencerRole;

    Contractor(Name name, Surname surname, Address address, CompanyName companyName, Email contactEmail, PhoneNumber contactPhoneNumber) {
        try {
            setName(name);
            setSurname(surname);
            setCompanyName(companyName);
            setContactEmail(contactEmail);
            setContactPhoneNumber(contactPhoneNumber);
            setAddress(address);
        } catch (Exception e) {
            removeFromExtent();
            System.out.println("Contractor not created: " + e.getMessage());
        }

    }

    public void assignCommentatorRole(Language language) {
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

    public void assignInfluencerRole(SocialMediaHandle socialMediaHandle) {
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
            description += " and " + serviceDescription.getValue();
        }

        return description;
    }

    public void setServiceDescription(ServiceDescription serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public void setName(Name name) {
        Util.require(name != null, "Name cannot be null");
        this.name = name;
    }

    @Override
    public Surname getSurname() {
        return surname;
    }

    @Override
    public void setSurname(Surname surname) {
        Util.require(surname != null, "Surname cannot be null");
        this.surname = surname;
    }

    @Override
    public Address getAddress() {
        return super.getAddress();
    }

    @Override
    public void setAddress(Address address) {
        Util.require(address != null, "Address cannot be null");
        super.setAddress(address);
    }

    @Override
    public String toString() {
        return "Contractor{" +
                "name=" + name +
                ", surname=" + surname +
                ", serviceDescription=" + getServiceDescription() +
                '}';
    }

     void finalizeRoles() {
        this.rolesFinalized = true;
    }
}
