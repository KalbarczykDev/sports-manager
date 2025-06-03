package mas.ui.view.fight;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import mas.model.Fighter;
import mas.model.data.ObjectExtent;
import mas.ui.theme.Colors;
import mas.ui.theme.Fonts;
import mas.ui.viewmodel.ManageFightsViewModel;

public class AddFightPanel extends JPanel {

  private JList<Fighter> fighterList;
  private JList<Fighter> selectedFighterList;
  private ManageFightsViewModel viewModel;

  public AddFightPanel() {

    List<Fighter> availableFighters = new ArrayList<>(ObjectExtent.getExtent(Fighter.class));
    availableFighters.sort(Comparator.comparing(f -> f.getName() + " " + f.getSurname()));

    setLayout(new BorderLayout());
    setBorder(new EmptyBorder(20, 24, 20, 24));
    setBackground(Color.WHITE);

    // Title
    JLabel title = new JLabel("Add Participants to Fight");
    title.setFont(Fonts.TITLE);
    title.setBorder(new EmptyBorder(0, 0, 20, 0));
    title.setHorizontalAlignment(SwingConstants.CENTER);
    add(title, BorderLayout.NORTH);

    // Split pane
    JPanel listsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
    listsPanel.setBackground(Color.WHITE);

    // Left side — Available fighters
    JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
    leftPanel.setBackground(Color.WHITE);
    JLabel availableLabel = new JLabel("Available Fighters:");
    availableLabel.setFont(Fonts.TITLE);
    leftPanel.add(availableLabel, BorderLayout.NORTH);

    fighterList = new JList<>(availableFighters.toArray(new Fighter[0]));
    fighterList.setVisibleRowCount(10);
    fighterList.setFont(Fonts.BODY);
    fighterList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane availableScroll = new JScrollPane(fighterList);
    availableScroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    leftPanel.add(availableScroll, BorderLayout.CENTER);

    // Right side — Already added
    JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
    rightPanel.setBackground(Color.WHITE);
    JLabel selectedLabel = new JLabel("Already Added:");
    selectedLabel.setFont(Fonts.TITLE);
    rightPanel.add(selectedLabel, BorderLayout.NORTH);

    selectedFighterList = new JList<>();
    selectedFighterList.setFont(Fonts.BODY);
    selectedFighterList.setEnabled(false); // read-only for now
    JScrollPane selectedScroll = new JScrollPane(selectedFighterList);
    selectedScroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    rightPanel.add(selectedScroll, BorderLayout.CENTER);

    listsPanel.add(leftPanel);
    listsPanel.add(rightPanel);
    add(listsPanel, BorderLayout.CENTER);

    // Buttons

    JButton cancelButton =
        createButton(
            "❌ Cancel",
            e -> {
              System.out.println("Cancelling fight addition");
            });

    JButton addButton =
        createButton(
            "➕ Add",
            e -> {
              System.out.println("Adding fighter");
            });
    JButton removeButton =
        createButton(
            "➖ Remove",
            e -> {
              System.out.println("Removing selected fighters");
            });

    JButton confirmButton = createButton("✅ Confirm", e -> {});

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.WHITE);
    buttonPanel.setBorder(new EmptyBorder(16, 0, 0, 0));
    buttonPanel.add(cancelButton);
    buttonPanel.add(addButton);
    buttonPanel.add(removeButton);
    buttonPanel.add(confirmButton);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  private JButton createButton(String text, ActionListener actionListener) {
    JButton button = new JButton(text);
    button.setFont(Fonts.BUTTON);
    button.setFocusPainted(false);
    button.setBackground(new Color(59, 89, 152));
    button.setForeground(Colors.BUTTON_TEXT);
    button.setPreferredSize(new Dimension(200, 40));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.addActionListener(actionListener);
    return button;
  }

  public void setViewModel(ManageFightsViewModel viewModel) {
    this.viewModel = viewModel;
  }
}
