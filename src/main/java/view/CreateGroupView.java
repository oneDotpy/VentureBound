package view;

import javax.swing.*;
import java.awt.*;

public class CreateGroupView extends JPanel {

    private final JButton backButton;
    private final JTextField groupNameInputField;
    private final JButton createButton;

    public CreateGroupView(CardLayout cardLayout, JPanel cardPanel) {
        // Set background color and layout
        this.setBackground(Color.decode("#2c2c2e"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 50, 250, 50)); // Padding around the content

        // Create a container for all components with top alignment
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.decode("#2c2c2e"));

        // Back Button (Top Panel)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // No gap
        topPanel.setBackground(Color.decode("#2c2c2e"));
        backButton = new JButton("< Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.decode("#2c2c2e"));
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        topPanel.add(backButton);

        backButton.addActionListener(e -> {
            // Switch to the "welcome" view
            cardLayout.show(cardPanel, "welcome");
            System.out.println("Redirecting to Welcome view...");
        });

        // Title Label
        JLabel title = new JLabel("Create a new group", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Group Name Input Field
        groupNameInputField = new JTextField(15);
        groupNameInputField.setMaximumSize(new Dimension(300, 40));
        groupNameInputField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        groupNameInputField.setForeground(Color.BLACK);
        groupNameInputField.setBackground(Color.WHITE);
        groupNameInputField.setHorizontalAlignment(JTextField.CENTER);
        groupNameInputField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create Button
        createButton = new JButton("Create");
        createButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        createButton.setBackground(Color.decode("#4a5f8a"));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setBorderPainted(false);
        createButton.setContentAreaFilled(true);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.setMaximumSize(new Dimension(200, 50));

        createButton.addActionListener(e -> {
            String groupName = groupNameInputField.getText().trim();
            if (groupName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a group code.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Attempting to join group with code: " + groupName);
            }
        });

        // Add components to the container with reduced spacing
        this.add(topPanel);
        this.add(Box.createVerticalStrut(10)); // Small space below back button
        this.add(title);
        this.add(Box.createVerticalStrut(20)); // Space below title
        this.add(groupNameInputField);
        this.add(Box.createVerticalStrut(20)); // Space below input field
        this.add(createButton);
    }
}
