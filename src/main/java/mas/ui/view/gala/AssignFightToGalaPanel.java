package mas.ui.view.gala;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import mas.model.Fight;
import mas.model.Gala;
import mas.model.data.ObjectExtent;
import mas.ui.theme.Colors;
import mas.ui.theme.Fonts;
import mas.ui.view.layout.MainScreen;
import mas.ui.view.util.Dialogs;
import mas.ui.viewmodel.ManageFightsViewModel;

/**
 * AssignFightToGalaPanel is a JPanel that allows users to assign fights to a gala. It displays
 * available galas with.
 */
public class AssignFightToGalaPanel extends JPanel {

  private final JList<Gala> galasList;
  private final JList<Gala> slectedGalasList;
  private final DefaultListModel<Gala> availableModel = new DefaultListModel<>();
  private final DefaultListModel<Gala> selectedModel = new DefaultListModel<>();
  private final ManageFightsViewModel viewModel;
  private Consumer<String> switchView;

  public AssignFightToGalaPanel(ManageFightsViewModel viewModel, Consumer<String> switchView) {
    this.viewModel = viewModel;
    this.switchView = switchView;

    List<Gala> availableGalas =
        ObjectExtent.getExtent(Gala.class).stream()
            .sorted(Comparator.comparing(g -> g.getEventName()))
            .collect(Collectors.toList());

    availableGalas.forEach(availableModel::addElement);

    galasList = new JList<>(availableModel);
    galasList.setVisibleRowCount(10);
    galasList.setFont(Fonts.BODY);
    galasList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    slectedGalasList = new JList<>(selectedModel);
    slectedGalasList.setFont(Fonts.BODY);
    slectedGalasList.setEnabled(true);

    setLayout(new BorderLayout());
    setBorder(new EmptyBorder(20, 24, 20, 24));
    setBackground(Color.WHITE);

    add(createTitle(), BorderLayout.NORTH);
    add(createListsPanel(), BorderLayout.CENTER);
    add(createButtonPanel(switchView), BorderLayout.SOUTH);
  }

  private JLabel createTitle() {
    JLabel title = new JLabel("Assign Fight To Gala");
    title.setFont(Fonts.TITLE);
    title.setBorder(new EmptyBorder(0, 0, 20, 0));
    title.setHorizontalAlignment(SwingConstants.CENTER);
    return title;
  }

  private JPanel createListsPanel() {
    JPanel listsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
    listsPanel.setBackground(Color.WHITE);

    listsPanel.add(createListPanel("Available Galas", galasList));
    return listsPanel;
  }

  private JPanel createListPanel(String title, JList<Gala> list) {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBackground(Color.WHITE);
    JLabel label = new JLabel(title);
    label.setFont(Fonts.TITLE);
    panel.add(label, BorderLayout.NORTH);

    JScrollPane scrollPane = new JScrollPane(list);
    scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    panel.add(scrollPane, BorderLayout.CENTER);
    return panel;
  }

  private JPanel createButtonPanel(Consumer<String> switchView) {
    JButton cancelButton =
        createButton(
            "\u274C Cancel",
            _ -> {
              if (Dialogs.showConfirmDialog("Are you sure you want to cancel?")
                  == JOptionPane.YES_OPTION) {
                MainScreen.getInstance().toggleEditing();
                switchView.accept("fights");
              }
            });

    JButton addButton = createButton("\u2795 Assign", _ -> handleAssignFight());

    JPanel panel = new JPanel();
    panel.setBackground(Color.WHITE);
    panel.setBorder(new EmptyBorder(16, 0, 0, 0));
    panel.add(cancelButton);
    panel.add(addButton);
    return panel;
  }

  private void handleAssignFight() {
    List<Gala> selected = galasList.getSelectedValuesList();
    if (selected.isEmpty()) {
      Dialogs.showWarningDialog("Please select at least one fighter to add.");
      return;
    }
    Gala gala = selected.get(0);
    if (gala == null) {
      Dialogs.showWarningDialog("Please select gala.");
      return;
    }
    Fight fight = viewModel.getCurrentFight();
    if (fight == null) {
      Dialogs.showWarningDialog("No fight selected. Please select a fight first.");
      return;
    }

    viewModel.assignFightToGala(fight, gala);

    MainScreen.getInstance().toggleEditing();
    reset();
    switchView.accept("fights");
  }

  private JButton createButton(String text, ActionListener listener) {
    JButton button = new JButton(text);
    button.setFont(Fonts.BUTTON);
    button.setFocusPainted(false);
    button.setBackground(new Color(59, 89, 152));
    button.setForeground(Colors.BUTTON_TEXT);
    button.setPreferredSize(new Dimension(200, 40));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.addActionListener(listener);
    return button;
  }

  public void reset() {

    selectedModel.clear();
    availableModel.clear();

    List<Gala> availableGalas =
        ObjectExtent.getExtent(Gala.class).stream()
            .sorted(Comparator.comparing(g -> g.getEventName()))
            .collect(Collectors.toList());

    availableGalas.forEach(availableModel::addElement);

    galasList.clearSelection();
    slectedGalasList.clearSelection();
  }
}
