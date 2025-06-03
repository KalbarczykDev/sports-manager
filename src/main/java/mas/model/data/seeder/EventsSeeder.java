package mas.model.data.seeder;

import java.time.LocalDateTime;
import mas.model.Gala;

/**
 * Seeder class for populating the "database" with initial event data. This class implements the
 * ISeeder interface and provides a method to run the seeding process.
 */
public class EventsSeeder implements ISeeder {

  public void run() {
    new Gala("UFC 3000", LocalDateTime.of(2025, 11, 10, 20, 0));
    new Gala("KSW 100", LocalDateTime.of(2026, 12, 15, 18, 0));
    new Gala("PLMMA 500", LocalDateTime.of(2026, 9, 20, 19, 0));
    new Gala("ONE Championship 200", LocalDateTime.of(2026, 8, 25, 21, 0));
  }
}
