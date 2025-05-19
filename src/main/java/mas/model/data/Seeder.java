package mas.model.data;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import mas.model.Fighter;
import mas.model.attribute.Address;
import mas.model.attribute.Title;

public class Seeder {
  public static void run(int count) {
    String[] names = {"Alex", "John", "Mia", "Liam", "Sara", "Emma", "Ola", "Tom"};
    String[] surnames = {"Smith", "Brown", "Johnson", "Kowalski", "Nowak", "Wiśniewski", "Adams"};
    String[] streets = {"Main St", "Highland Ave", "Park Road", "Testowa", "Sunset Blvd"};
    String[] titleNames = {"Heavyweight Champion", "LightweightChampion"};

    for (int i = 0; i < count; i++) {
      String name = names[random(names.length)];
      String surname = surnames[random(surnames.length)];
      String street = streets[random(streets.length)];
      int streetNumber = ThreadLocalRandom.current().nextInt(1, 100);
      String city = "City" + random(100);
      String postCode = String.format("%02d-%03d", random(99), random(999));

      Address address = Address.of(streetNumber, street, city, "Testland", postCode);

      Fighter fighter = new Fighter(name, surname, address);

      for (int j = 0; j < ThreadLocalRandom.current().nextInt(0, 15); j++) {
        Title tile = Title.of(titleNames[ThreadLocalRandom.current().nextInt(0, 1)], "test desc");
        fighter.addTitle(tile);
      }

      fighter.setDateOfJoining(LocalDateTime.now());
      ;
    }
  }

  private static int random(int bound) {
    return ThreadLocalRandom.current().nextInt(bound);
  }
}
