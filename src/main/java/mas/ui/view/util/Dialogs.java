package mas.ui.view.util;

import javax.swing.JOptionPane;

public class Dialogs {

  public static void showErrorDialog(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public static void showInfoDialog(String message) {
    JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
  }

  public static int showConfirmDialog(String message) {
    return JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION);
  }

  public static void showWarning(String message) {
    JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
  }
}
