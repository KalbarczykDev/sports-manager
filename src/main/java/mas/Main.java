package mas;

import mas.domain.model.company.*;
import mas.domain.model.company.PhoneNumber;
import mas.domain.model.shared.Address;
import mas.domain.model.sponsor.Sponsor;

public class Main {

  public static void main(String[] args) {

    // unique

    Company uc1 =
        new Sponsor(
            CompanyName.of("Unique Company Name"),
            Email.of("unique@email.com"),
            PhoneNumber.of("111222333444"),
            Address.of(10, "Main Street", "Warsaw", "Mazowieckie", "00-001"),
            NIP.of("1234"));

    Company uc2 =
        new Sponsor(
            CompanyName.of("Another Company"),
            Email.of("another@email.com"),
            PhoneNumber.of("555666777"),
            Address.of(22, "Second Avenue", "Krakow", "Malopolskie", "30-002"),
            NIP.of("1234") // nie zostanie stworzony
            );
  }
}
