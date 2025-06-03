package mas.ui.view.fight;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import mas.model.Fighter;
import mas.model.data.ObjectExtent;
import mas.ui.theme.Colors;
import mas.ui.theme.Fonts;
import mas.ui.view.layout.MainScreen;
import mas.ui.view.util.Dialogs;
import mas.ui.viewmodel.ManageFightsViewModel;

/**
 * AddFightPanel is a JPanel that allows users to add fighters to a new fight. It displays available
 * fighters and those already added, with options to add or remove fighters.
 */
public class AddFightPanel extends JPanel {

  private final JList<Fighter> fighterList;
  private final JList<Fighter> selectedFighterList;
  private final DefaultListModel<Fighter> availableModel = new DefaultListModel<>();
  private final DefaultListModel<Fighter> selectedModel = new DefaultListModel<>();
  private final ManageFightsViewModel viewModel;

  /**
   * Constructs an AddFightPanel with the specified view model and switch view consumer.
   *
   * @param viewModel the view model for managing fights
   * @param switchView a consumer to switch views when actions are performed
   */
  public AddFightPanel(ManageFightsViewModel viewModel, Consumer<String> switchView) {
    this.viewModel = viewModel;

    List<Fighter> availableFighters =
        ObjectExtent.getExtent(Fighter.class).stream()
            .sorted(Comparator.comparing(f -> f.getName() + " " + f.getSurname()))
            .collect(Collectors.toList());

    availableFighters.forEach(availableModel::addElement);

    fighterList = new JList<>(availableModel);
    fighterList.setVisibleRowCount(10);
    fighterList.setFont(Fonts.BODY);
    fighterList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    selectedFighterList = new JList<>(selectedModel);
    selectedFighterList.setFont(Fonts.BODY);
    selectedFighterList.setEnabled(true);

    setLayout(new BorderLayout());
    setBorder(new EmptyBorder(20, 24, 20, 24));
    setBackground(Color.WHITE);

    add(createTitle(), BorderLayout.NORTH);
    add(createListsPanel(), BorderLayout.CENTER);
    add(createButtonPanel(switchView), BorderLayout.SOUTH);
  }

  /**
   * Creates a title label for the panel.
   *
   * @return a JLabel with the title "Add Participants to Fight"
   */
  private JLabel createTitle() {
    JLabel title = new JLabel("Add Participants to Fight");
    title.setFont(Fonts.TITLE);
    title.setBorder(new EmptyBorder(0, 0, 20, 0));
    title.setHorizontalAlignment(SwingConstants.CENTER);
    return title;
  }

  /**
   * Creates a panel containing two lists: one for available fighters and one for already added
   * fighters.
   *
   * @return a JPanel containing the two lists
   */
  private JPanel createListsPanel() {
    JPanel listsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
    listsPanel.setBackground(Color.WHITE);

    listsPanel.add(createListPanel("Available Fighters:", fighterList));
    listsPanel.add(createListPanel("Already Added:", selectedFighterList));
    return listsPanel;
  }

  /**
   * Creates a panel for a list with a title.
   *
   * @param title the title of the list
   * @param list the JList to be displayed
   * @return a JPanel containing the title and the list
   */
  private JPanel createListPanel(String title, JList<Fighter> list) {
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

  /**
   * Creates a panel with buttons for canceling, adding, removing, and confirming fighters.
   *
   * @param switchView a consumer to switch views when actions are performed
   * @return a JPanel containing the buttons
   */
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

    JButton addButton = createButton("\u2795 Add", _ -> handleAddFighters());
    JButton removeButton = createButton("\u2796 Remove", _ -> handleRemoveFighters());
    JButton confirmButton =
        createButton(
            "\u2705 Confirm",
            _ -> {
              if (selectedModel.isEmpty()) {
                Dialogs.showWarningDialog("Please add at least one fighter to the fight.");
                return;
              }
              viewModel.assignFightersToNewFight(
                  java.util.Collections.list(selectedModel.elements()));
              MainScreen.getInstance().toggleEditing();
              reset();
              switchView.accept("fights");
            });

    JPanel panel = new JPanel();
    panel.setBackground(Color.WHITE);
    panel.setBorder(new EmptyBorder(16, 0, 0, 0));
    panel.add(cancelButton);
    panel.add(addButton);
    panel.add(removeButton);
    panel.add(confirmButton);
    return panel;
  }

  /**
   * Handles the addition of selected fighters to the fight. Validates each fighter's yearly fight
   * limit before adding.
   */
  private void handleAddFighters() {
    List<Fighter> selected = fighterList.getSelectedValuesList();
    if (selected.isEmpty()) {
      Dialogs.showWarningDialog("Please select at least one fighter to add.");
      return;
    }
    for (Fighter fighter : selected) {
      if (!selectedModel.contains(fighter)) {
        try {
          fighter.validateYearlyFightLimit();
          selectedModel.addElement(fighter);
          availableModel.removeElement(fighter);
        } catch (Exception e) {
          Dialogs.showWarningDialog(
              "Fighter " + fighter.getName() + " " + fighter.getSurname() + ": " + e.getMessage());
        }
      } else {
        Dialogs.showWarningDialog(
            "Fighter " + fighter.getName() + " " + fighter.getSurname() + " is already added.");
      }
    }
  }

  /**
   * Handles the removal of selected fighters from the fight. Moves them back to the available list.
   */
  private void handleRemoveFighters() {
    List<Fighter> selected = selectedFighterList.getSelectedValuesList();
    if (selected.isEmpty()) {
      Dialogs.showWarningDialog("Please select at least one fighter to remove.");
      return;
    }
    for (Fighter fighter : selected) {
      selectedModel.removeElement(fighter);
      availableModel.addElement(fighter);
    }
  }

  /**
   * Creates a button with the specified text and action listener.
   *
   * @param text the text to display on the button
   * @param listener the action listener to handle button clicks
   * @return a JButton configured with the specified text and listener
   */
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

  /**
   * Resets the panel to its initial state, clearing selected and available fighters. It repopulates
   * the available fighters list with all fighters from the extent.
   */
  public void reset() {

    selectedModel.clear();
    availableModel.clear();

    List<Fighter> allFighters =
        ObjectExtent.getExtent(Fighter.class).stream()
            .sorted(Comparator.comparing(f -> f.getName() + " " + f.getSurname()))
            .collect(Collectors.toList());

    allFighters.forEach(availableModel::addElement);

    fighterList.clearSelection();
    selectedFighterList.clearSelection();
  }
}
