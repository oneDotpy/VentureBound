package view;

import entity.User;
import interface_adapter.create_group.CreateGroupController;
import interface_adapter.create_group.CreateGroupViewModel;
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
    private CreateGroupController createGroupController;
    private final CreateGroupViewModel createGroupViewModel;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public CreateGroupView(CreateGroupViewModel createGroupViewModel, CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.createGroupViewModel = createGroupViewModel;

        createGroupViewModel.addPropertyChangeListener(this);

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
            User user = createGroupViewModel.getState().getUser();
            createGroupController.createGroup(groupName, user);
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

    public void setCreateGroupController(CreateGroupController createGroupController) {
        this.createGroupController = createGroupController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if("error".equals(evt.getPropertyName())){
            String message = createGroupViewModel.getState().getGroupError();
            JOptionPane.showMessageDialog(this, message);
        }
    }

    public String getViewName() {
        return groupViewModel.getState().getGroupName();
    }
}
