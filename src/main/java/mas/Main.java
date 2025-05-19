package mas;

import javax.swing.*;
import mas.model.data.Seeder;
import mas.ui.view.layout.MainScreen;

public class Main {

  public static void main(String[] args) {
    Seeder.run(100);
    SwingUtilities.invokeLater(MainScreen::new);
  }
}
