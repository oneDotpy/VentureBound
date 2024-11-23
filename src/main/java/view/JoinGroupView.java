package view;

import interface_adapter.group.GroupController;
import interface_adapter.group.GroupViewModel;
import interface_adapter.join_group.JoinGroupController;
import interface_adapter.join_group.JoinGroupState;
import interface_adapter.join_group.JoinGroupViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class JoinGroupView extends JPanel implements PropertyChangeListener {

    private final JButton backButton;
    private final JTextField groupCodeInputField;
    private final JButton joinButton;
    private JoinGroupController joinGroupController;
    private final JoinGroupViewModel joinGroupViewModel;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public JoinGroupView(JoinGroupViewModel joinGroupViewModel, CardLayout cardLayout, JPanel cardPanel) {
        this.joinGroupViewModel = joinGroupViewModel;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        // Add this view as a listener for ViewModel updates
        joinGroupViewModel.addPropertyChangeListener(this);

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
            JoinGroupState joinGroupState = joinGroupViewModel.getState();
            joinGroupController.switchToWelcomeView(joinGroupState.getUser());
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
            JoinGroupState current_state = joinGroupViewModel.getState();
            joinGroupController.joinGroup(groupCode, current_state.getUser());
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

    public void setJoinGroupController(JoinGroupController joinGroupController) {
        this.joinGroupController = joinGroupController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("error".equals(evt.getPropertyName())) {
            String message = joinGroupViewModel.getState().getGroupError();
            JOptionPane.showMessageDialog(this, message , "Error", JOptionPane.ERROR_MESSAGE);
            JoinGroupState joinGroupState = joinGroupViewModel.getState();
            joinGroupState.setGroupError("");
        }
    }
}
