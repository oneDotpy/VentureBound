package view;

import interface_adapter.group.GroupController;
import interface_adapter.group.GroupViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreateGroupView extends JPanel implements PropertyChangeListener {

    private final JButton backButton;
    private final JTextField groupNameInputField;
    private final JButton createButton;
    private final GroupController groupController;
    private final GroupViewModel groupViewModel;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public CreateGroupView(GroupViewModel groupViewModel, GroupController groupController, CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.groupController = groupController;
        this.groupViewModel = groupViewModel;

        groupViewModel.addPropertyChangeListener(this);

        this.setBackground(Color.decode("#2c2c2e"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 50, 250, 50));

        // Back Button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.decode("#2c2c2e"));
        backButton = new JButton("< Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.decode("#2c2c2e"));
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "welcome");
            System.out.println("Redirecting to Welcome view...");
        });
        topPanel.add(backButton);

        // Title
        JLabel title = new JLabel("Create a new group");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Group Name Input Field
        groupNameInputField = new JTextField(15);
        groupNameInputField.setMaximumSize(new Dimension(300, 40));
        groupNameInputField.setFont(new Font("SansSerif", Font.PLAIN, 16));

        // Create Button
        createButton = new JButton("Create");
        createButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        createButton.setBackground(Color.decode("#4a5f8a"));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setBorderPainted(false);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.addActionListener(e -> {
            String groupName = groupNameInputField.getText().trim();
            groupController.createGroup(groupName);
            cardLayout.show(cardPanel, "chat");
            System.out.println("Group created. Redirecting to Chat view...");
        });

        // Add components to the panel
        this.add(topPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(groupNameInputField);
        this.add(Box.createVerticalStrut(20));
        this.add(createButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("createMessage".equals(evt.getPropertyName())) {
            String message = groupViewModel.getState().getCreateMessage();
            JOptionPane.showMessageDialog(this, message, "Group Creation", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public String getViewName() {
        return groupViewModel.getState().getGroupName();
    }
}
