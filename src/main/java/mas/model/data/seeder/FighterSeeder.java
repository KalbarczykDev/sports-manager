package mas.model.data.seeder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import mas.model.Fighter;
import mas.model.attribute.Address;
import mas.model.attribute.Title;

public class FighterSeeder implements ISeeder {

  private static final String[] FIRST_NAMES = {
    "Alex", "John", "Mia", "Liam", "Sara", "Emma", "Ola", "Tom", "Nina", "Mark", "Zoe", "Adam",
    "Natalie", "Chris"
  };

  private static final String[] LAST_NAMES = {
    "Smith",
    "Brown",
    "Johnson",
    "Kowalski",
    "Nowak",
    "Wiśniewski",
    "Adams",
    "Taylor",
    "Lee",
    "Patel",
    "Nguyen"
  };

  private static final String[] STREETS = {
    "Main St",
    "Highland Ave",
    "Park Road",
    "Testowa",
    "Sunset Blvd",
    "Maple Street",
    "Oak Avenue",
    "River Lane"
  };

  private static final String[] CITIES = {
    "Warsaw",
    "New York",
    "Berlin",
    "Paris",
    "Krakow",
    "London",
    "Amsterdam",
    "Oslo",
    "Prague",
    "Helsinki"
  };

  private static final String[] TITLE_NAMES = {
    "Heavyweight Champion", "Lightweight Champion", "Middleweight Champion",
    "Rookie of the Year", "Undefeated Fighter", "Knockout Specialist"
  };

  public void run() {
    int count = 20;
    for (int i = 0; i < count; i++) {
      String name = random(FIRST_NAMES);
      String surname = random(LAST_NAMES);
      String street = random(STREETS);
      int streetNumber = random(1, 200);
      String city = random(CITIES);
      String postCode = String.format("%02d-%03d", random(10, 99), random(100, 999));

      Address address = Address.of(streetNumber, street, city, "Countryland", postCode);

      Fighter fighter = new Fighter(name, surname, address);

      int titleCount = random(0, 15);
      for (int j = 0; j < titleCount; j++) {
        String titleName = random(TITLE_NAMES);
        String titleDesc = "Awarded for excellence in " + titleName.toLowerCase();
        Title title = Title.of(titleName, titleDesc);
        fighter.addTitle(title);
      }

      fighter.setDateOfJoining(randomJoinDate());
    }
  }

  private static LocalDateTime randomJoinDate() {

    long daysBack =
        ThreadLocalRandom.current()
            .nextLong(
                0,
                ChronoUnit.DAYS.between(LocalDateTime.now().minusYears(10), LocalDateTime.now()));
    return LocalDateTime.now().minusDays(daysBack);
  }

  private static String random(String[] array) {
    return array[ThreadLocalRandom.current().nextInt(array.length)];
  }

  private static int random(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max);
  }
}
