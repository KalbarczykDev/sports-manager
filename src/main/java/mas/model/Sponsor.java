package mas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mas.model.abstraction.Company;
import mas.model.association.Sponsorship;
import mas.model.attribute.Address;

/**
 * Represents a sponsor in the system, extending the Company class. A sponsor can sponsor multiple
 * fighters through sponsorships, and it provides methods to manage these sponsorships.
 */
public class Sponsor extends Company {

  private final List<Sponsorship> sponsoredFighters = new ArrayList<>();

  /**
   * Constructs a Sponsor with the specified company name, contact email, contact phone number,
   * address, and NIP (tax identification number).
   *
   * @param companyName the name of the sponsoring company
   * @param contactEmail the contact email of the sponsor
   * @param contactPhoneNumber the contact phone number of the sponsor
   * @param address the address of the sponsor
   * @param nip the NIP (tax identification number) of the sponsor
   */
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

  /**
   * Adds sponsorship to the sponsor. The sponsorship must not be null.
   *
   * @param sponsorship thesponsorship to add
   */
  public void addSponsorship(Sponsorship sponsorship) {

    if (sponsorship == null) {
      throw new IllegalArgumentException("Sponsorship cannot be null");
    }
    sponsoredFighters.add(sponsorship);
  }

  /**
   * Removes a sponsorship from the sponsor. If the sponsorship is null, it does nothing.
   *
   * @param sponsorship the sponsorship to remove
   */
  public void removeSponsorship(Sponsorship sponsorship) {

    if (sponsorship == null) {
      return;
    }

    sponsoredFighters.remove(sponsorship);
    sponsorship.removeFromExtent();
  }

  /**
   * Returns an unmodifiable list of sponsorships associated with the sponsor.
   *
   * @return an unmodifiable list of sponsorships
   */
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
        + sponsoredFighters.stream()
            .map(f -> f.getFighter().getName() + " " + f.getFighter().getSurname())
        + '}';
  }

  @Override
  protected void removeFromExtent() {
    removeSponsorships();
    super.removeFromExtent();
  }

  /**
   * Removes all sponsorships associated with this sponsor. This method is called when the sponsor
   * is removed from the extent.
   */
  private void removeSponsorships() {
    for (Sponsorship sponsorship : new ArrayList<>(sponsoredFighters)) {
      sponsorship.removeFromExtent();
    }
  }
}
