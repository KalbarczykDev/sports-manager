package mas.model.data.seeder;

import java.time.LocalDateTime;
import mas.model.Gala;

public class EventsSeeder implements ISeeder {

  public void run() {
    new Gala("UFC 3000", LocalDateTime.of(2025, 11, 10, 20, 0));
    new Gala("KSW 100", LocalDateTime.of(2026, 12, 15, 18, 0));
    new Gala("PLMMA 500", LocalDateTime.of(2026, 9, 20, 19, 0));
    new Gala("ONE Championship 200", LocalDateTime.of(2026, 8, 25, 21, 0));
  }
}
