package mas.domain.model.sponsor;

import mas.domain.model.company.Company;
import mas.domain.model.company.CompanyName;
import mas.domain.model.company.Email;
import mas.domain.model.company.PhoneNumber;
import mas.domain.model.fighter.Fighter;
import mas.domain.model.shared.Address;
import mas.util.Util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sponsor extends Company {


    private final List<Fighter> sponsoredFighters = new ArrayList<>();

    public Sponsor(CompanyName companyName, Email contactEmail, PhoneNumber contactPhoneNumber, Address address) {
        try {
            setCompanyName(companyName);
            setContactEmail(contactEmail);
            setContactPhoneNumber(contactPhoneNumber);
            setAddress(address);
        } catch (Exception e) {
            removeFromExtent();
            System.out.println("Sponsor not created: " + e.getMessage());
        }
    }


    public void addSponsoredFighter(Fighter fighter) {
        Util.require(fighter != null, "Fighter cannot be null");
        if (!sponsoredFighters.contains(fighter)) {
            sponsoredFighters.add(fighter);
            fighter.addSponsor(this);
        }
    }

    public void removeSponsoredFighter(Fighter fighter) {
        Util.require(fighter != null, "Fighter cannot be null");
        if (sponsoredFighters.remove(fighter)) {
            fighter.removeSponsor(this);
        }
    }

    public List<Fighter> getSponsoredFighters() {
        return Collections.unmodifiableList(sponsoredFighters);
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "companyName='" + getCompanyName() + '\'' +
                ", contactEmail='" + getContactEmail() + '\'' +
                ", contactPhoneNumber='" + getContactPhoneNumber() + '\'' +
                ", address=" + getAddress() +
                ", sponsoredFighters=" + sponsoredFighters.stream().map(f -> f.getName() + " " + f.getSurname()) +
                '}';
    }

    @Override
    protected void removeFromExtent() {
        removeFromFighters();
        super.removeFromExtent();
    }

    private void removeFromFighters() {
        for (Fighter fighter : new ArrayList<>(sponsoredFighters)) {
            removeSponsoredFighter(fighter);
        }
    }
}
