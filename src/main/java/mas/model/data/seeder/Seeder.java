package mas.model.data.seeder;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for seeders that populate the data model with initial data. Each seeder should
 * implement the run method to define how it seeds the data.
 */
interface ISeeder {
  /**
   * Runs the seeder to populate the data model with initial data. This method should be implemented
   * by each seeder to define the specific data it seeds.
   */
  void run();
}

/**
 * The Seeder class is responsible for managing and executing a collection of seeders that populate
 * the data model with initial data. It allows for the registration of new seeders and ensures that
 * all registered seeders are executed when the run method is called.
 */
public class Seeder {

  static List<ISeeder> seeders = new ArrayList<>();

  /**
   * Runs all registered seeders to populate the data model with initial data. This method should be
   * called once at the start of the application to ensure that the data model is seeded with
   * necessary initial data.
   */
  public static void run() {
    if (seeders.isEmpty()) {
      initDefaultSeeders();
    }
    seeders.forEach(ISeeder::run);
  }

  /**
   * Initializes the default seeders that are included with the application. This method is called
   * when no seeders have been registered yet.
   */
  private static void initDefaultSeeders() {
    registerSeeder(new FighterSeeder());
    registerSeeder(new EventsSeeder());
    registerSeeder(new FightSeeder());
  }

  /**
   * Registers a new seeder to be run during the seeding process.
   *
   * @param seeder The seeder to register.
   */
  public static void registerSeeder(ISeeder seeder) {
    seeders.add(seeder);
  }
}
