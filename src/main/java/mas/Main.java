package mas;

import javax.swing.*;
import mas.model.Fighter;
import mas.model.attribute.Address;
import mas.ui.view.layout.MainScreen;

public class Main {

  public static void main(String[] args) {
    seed();
    SwingUtilities.invokeLater(MainScreen::new);
  }

  public static void seed() {

    new Fighter("test", "testowy", Address.of(1, "testowa", "testowo", "testow", "21-37"));

    new Fighter("testoniusz", "testowski", Address.of(2, "testowa", "testowo", "testow", "21-37"));
  }
}
