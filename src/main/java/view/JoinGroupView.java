package view;

import interface_adapter.group.GroupController;
import interface_adapter.group.GroupViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class JoinGroupView extends JPanel implements PropertyChangeListener {

    private final JButton backButton;
    private final JTextField groupCodeInputField;
    private final JButton joinButton;
    private final GroupController groupController;
    private final GroupViewModel groupViewModel;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public JoinGroupView(GroupViewModel groupViewModel, GroupController groupController, CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.groupController = groupController;
        this.groupViewModel = groupViewModel;

        // Add this view as a listener for ViewModel updates
        groupViewModel.addPropertyChangeListener(this);

        // Set up the panel layout
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
        JLabel title = new JLabel("Join an existing group");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Group Code Input Field
        groupCodeInputField = new JTextField(15);
        groupCodeInputField.setMaximumSize(new Dimension(300, 40));
        groupCodeInputField.setFont(new Font("SansSerif", Font.PLAIN, 16));

        // Join Button
        joinButton = new JButton("Join");
        joinButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        joinButton.setBackground(Color.decode("#4a5f8a"));
        joinButton.setForeground(Color.WHITE);
        joinButton.setFocusPainted(false);
        joinButton.setBorderPainted(false);
        joinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        joinButton.setMaximumSize(new Dimension(200, 50));

        // Action listener to bypass group joining and redirect to chat
        joinButton.addActionListener(e -> {
            String groupCode = groupCodeInputField.getText().trim();
            groupController.joinGroup(groupCode);
            cardLayout.show(cardPanel, "chat");
            System.out.println("Redirecting to Chat view...");
        });

        // Add components to the panel
        this.add(topPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(groupCodeInputField);
        this.add(Box.createVerticalStrut(20));
        this.add(joinButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("joinMessage".equals(evt.getPropertyName())) {
            String message = groupViewModel.getState().getJoinMessage();
            JOptionPane.showMessageDialog(this, message, "Group Join", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
