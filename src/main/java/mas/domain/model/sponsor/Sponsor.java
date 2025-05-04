package mas.domain.model.sponsor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mas.domain.model.company.*;
import mas.domain.model.company.Company;
import mas.domain.model.company.CompanyName;
import mas.domain.model.company.Email;
import mas.domain.model.company.PhoneNumber;
import mas.domain.model.shared.Address;
import mas.domain.model.sponsorship.Sponsorship;
import mas.util.Util;

public class Sponsor extends Company {

  private final List<Sponsorship> sponsoredFighters = new ArrayList<>();

  public Sponsor(
      CompanyName companyName,
      Email contactEmail,
      PhoneNumber contactPhoneNumber,
      Address address,
      NIP nip) {
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
    Util.require(sponsorship != null, "Sponsorship cannot be null");
    sponsoredFighters.add(sponsorship);
  }

  public void removeSponsorship(Sponsorship sponsorship) {
    Util.require(sponsorship != null, "Sponsorship cannot be null");
    if (sponsoredFighters.remove(sponsorship)) {
      sponsorship.removeFromExtent();
    }
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
