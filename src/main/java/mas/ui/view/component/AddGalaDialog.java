package mas.ui.view.component;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import mas.model.Gala;
import mas.ui.view.gala.AssignFightToGalaPanel;
import mas.ui.view.util.Dialogs;
import mas.ui.viewmodel.ManageFightsViewModel;

/**
 * AddGalaDialog is a dialog for creating a new Gala. It allows the user to input the name and date
 * of the gala, and upon submission, it adds the gala to the available model in the
 * AssignFightToGalaPanel.
 */
public class AddGalaDialog extends JDialog {

  public AddGalaDialog(
      AssignFightToGalaPanel panel, ManageFightsViewModel viewModel, Consumer<String> switchView) {
    super((Frame) SwingUtilities.getWindowAncestor(panel), "Create New Gala", true);
    setSize(400, 220);
    setLocationRelativeTo(panel);
    setLayout(new BorderLayout());

    JLabel nameLabel = new JLabel("Gala Name:");
    JTextField nameField = new JTextField();

    JLabel dateLabel = new JLabel("Date (yyyy-MM-dd):");
    JTextField dateField = new JTextField(LocalDate.now().toString());

    JPanel inputPanel = new JPanel(new GridLayout(4, 1, 10, 10));
    inputPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
    inputPanel.add(nameLabel);
    inputPanel.add(nameField);
    inputPanel.add(dateLabel);
    inputPanel.add(dateField);

    JButton createButton = new JButton("Create");
    createButton.addActionListener(
        _ -> {
          String name = nameField.getText().trim();
          String dateString = dateField.getText().trim();

          if (name.isEmpty() || dateString.isEmpty()) {
            Dialogs.showWarningDialog("Please enter both name and date.");
            return;
          }

          try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime dateTime = date.atStartOfDay();

            Gala gala = new Gala(name, dateTime);
            System.out.println("Creating Gala with name: " + name + " and date: " + dateTime);
            panel.getAvailableModel().addElement(gala);
            dispose();
          } catch (Exception e) {
            Dialogs.showErrorDialog(e.getMessage());
            e.printStackTrace();
          }
        });

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
    buttonPanel.add(createButton);

    add(inputPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
    setVisible(true);
  }
}
