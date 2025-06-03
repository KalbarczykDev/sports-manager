package mas;

import java.io.IOException;
import javax.swing.*;
import mas.model.data.ObjectExtent;
import mas.model.data.Seeder;
import mas.ui.view.layout.MainScreen;

public class Main {

  public static void main(String[] args) throws IOException {
    Seeder.run();
    SwingUtilities.invokeLater(MainScreen::new);
    ObjectExtent.saveExtent();
  }
}
