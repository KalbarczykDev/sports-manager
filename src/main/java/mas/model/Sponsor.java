package mas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mas.model.abstraction.Company;
import mas.model.attribute.Address;
import mas.model.association.Sponsorship;


public class Sponsor extends Company {

    private final List<Sponsorship> sponsoredFighters = new ArrayList<>();

    public Sponsor(
            String companyName,
            String contactEmail,
            String contactPhoneNumber,
            Address address,
            String nip) {
        try {
            setCompanyName(companyName);
            setContactEmail(contactEmail);
            setContactPhoneNumber(contactPhoneNumber);
            setAddress(address);
            setNip(nip);
        } catch (Exception e) {
            removeFromExtent();
            System.out.println("Sponsor not created: " + e.getMessage());
        }
    }

    public void addSponsorship(Sponsorship sponsorship) {

        if (sponsorship == null) {
            throw new IllegalArgumentException("Sponsorship cannot be null");
        }
        sponsoredFighters.add(sponsorship);
    }

    public void removeSponsorship(Sponsorship sponsorship) {

        if (sponsorship == null) {
            return;
        }

        sponsoredFighters.remove(sponsorship);
        sponsorship.removeFromExtent();


    }

    public List<Sponsorship> getSponsoredFighters() {
        return Collections.unmodifiableList(sponsoredFighters);
    }

    @Override
    public String toString() {
        return "Sponsor{"
                + "companyName='"
                + getCompanyName()
                + '\''
                + ", contactEmail='"
                + getContactEmail()
                + '\''
                + ", contactPhoneNumber='"
                + getContactPhoneNumber()
                + '\''
                + ", address="
                + getAddress()
                + ", sponsoredFighters="
                + sponsoredFighters.stream().map(f -> f.getFighter().getName() + " " + f.getFighter().getSurname())
                + '}';
    }

    @Override
    protected void removeFromExtent() {
        removeSponsorships();
        super.removeFromExtent();
    }

    private void removeSponsorships() {
        for (Sponsorship sponsorship : new ArrayList<>(sponsoredFighters)) {
            sponsorship.removeFromExtent();
        }
    }
}
