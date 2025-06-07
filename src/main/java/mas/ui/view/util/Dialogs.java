package mas.ui.view.util;

import javax.swing.JOptionPane;

/**
 * Dialogs is a utility class that provides methods to show different types of dialog boxes. It uses
 * JOptionPane to display error, information, confirmation, and warning dialogs.
 */
public class Dialogs {

  /**
   * Shows an ErrorDialog with the specified message.
   *
   * @param message the message to display in the dialog
   */
  public static void showErrorDialog(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Shows an information dialog with the specified message.
   *
   * @param message the message to display in the dialog
   */
  public static void showInfoDialog(String message) {
    JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Shows a confirmation dialog with the specified message and returns the user's choice.
   *
   * @param message the message to display in the dialog
   * @return an integer representing the user's choice (YES_OPTION or NO_OPTION)
   */
  public static int showConfirmDialog(String message) {
    return JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION);
  }

  /**
   * Shows a warning dialog with the specified message.
   *
   * @param message the message to display in the dialog
   */
  public static void showWarningDialog(String message) {
    JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
  }
}
