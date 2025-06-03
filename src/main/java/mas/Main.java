publicackage mas;

import java.io.IOException;
import javax.swing.*;
import mas.model.data.seeder.Seeder;
import mas.ui.view.layout.MainScreen;

public class Main {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    // File file = new File("extent.ser");

    // TODO: Uncoment for prod
    // if (file.exists()) {
    //   System.out.println("Loading existing extent...");
    //   ObjectExtent.loadExtent();
    // } else {
    //   System.out.println("No existing extent found, seeding the data");
    //   Seeder.run();
    // }

    Seeder.run();

    SwingUtilities.invokeLater(MainScreen::getInstance);
    //   ObjectExtent.saveExtent();
  }
}
