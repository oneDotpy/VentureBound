package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

import interface_adapter.welcome.WelcomeController;
import interface_adapter.welcome.WelcomeState;
import interface_adapter.welcome.WelcomeViewModel;

/**
 * The View for when the user is logged into the program.
 */
public class WelcomeView extends JPanel implements PropertyChangeListener {

    private final String viewName = "welcome";
    private final WelcomeViewModel welcomeViewModel;

    private final JLabel greeting;
    private final JButton createGroup;
    private final JButton joinGroup;
    private final JButton logOut;
    private WelcomeController welcomeController;

    public WelcomeView(WelcomeViewModel welcomeViewModel, CardLayout cardLayout, JPanel cardPanel) {
        this.welcomeViewModel = welcomeViewModel;
        this.welcomeViewModel.addPropertyChangeListener(this);

        // Set the background and layout for the main panel
        this.setBackground(Color.decode("#2c2c2e"));
        this.setLayout(new BorderLayout());

        // Top-right panel for greeting and log out button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.decode("#2c2c2e"));

        greeting = new JLabel("Welcome, ");
        greeting.setFont(new Font("SansSerif", Font.BOLD, 16));
        greeting.setForeground(Color.WHITE);

        logOut = new JButton("Sign Out");
        logOut.setFont(new Font("SansSerif", Font.BOLD, 12));
        logOut.setBackground(Color.decode("#8a2b2b"));
        logOut.setForeground(Color.WHITE);
        logOut.setFocusPainted(false);
        logOut.setBorderPainted(false);
        logOut.setContentAreaFilled(true);
        logOut.setPreferredSize(new Dimension(100, 30));

        logOut.addActionListener(e -> {
            // Switch to the "login" view
            cardLayout.show(cardPanel, "login");
            System.out.println("Redirecting to login view...");
        });

        // Add greeting and logout button to the top panel
        topPanel.add(greeting);
        topPanel.add(logOut);

        // Center panel for "Create Group" and "Join a Group" buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode("#2c2c2e"));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // "Create Group" button
        createGroup = new JButton("Create Group");
        createGroup.setFont(new Font("SansSerif", Font.BOLD, 18));
        createGroup.setBackground(Color.decode("#8c8c8c"));
        createGroup.setForeground(Color.WHITE);
        createGroup.setFocusPainted(false);
        createGroup.setBorderPainted(false);
        createGroup.setContentAreaFilled(true);
        createGroup.setAlignmentX(Component.CENTER_ALIGNMENT);
        createGroup.setMaximumSize(new Dimension(200, 50));
        createGroup.setPreferredSize(new Dimension(200, 50));

        createGroup.addActionListener(e -> {
            // Switch to the "create group" view
            WelcomeState curState = welcomeViewModel.getState();
            welcomeController.switchToCreateGroupView(curState.getUser());
//            cardLayout.show(cardPanel, "create_group");
//            System.out.println("Redirecting to create group view...");
        });

        // "Join a Group" button
        joinGroup = new JButton("Join a group");
        joinGroup.setFont(new Font("SansSerif", Font.BOLD, 18));
        joinGroup.setBackground(Color.decode("#8c8c8c"));
        joinGroup.setForeground(Color.WHITE);
        joinGroup.setFocusPainted(false);
        joinGroup.setBorderPainted(false);
        joinGroup.setContentAreaFilled(true);
        joinGroup.setAlignmentX(Component.CENTER_ALIGNMENT);
        joinGroup.setMaximumSize(new Dimension(200, 50));
        joinGroup.setPreferredSize(new Dimension(200, 50));

        joinGroup.addActionListener(e -> {
            // Switch to the "join group" view
            WelcomeState curState = welcomeViewModel.getState();
            welcomeController.switchToJoinGroupView(curState.getUser());
        });

        // Add buttons to button panel with spacing
        buttonPanel.add(Box.createVerticalStrut(150));
        buttonPanel.add(createGroup);
        buttonPanel.add(Box.createVerticalStrut(20)); // Space between buttons
        buttonPanel.add(joinGroup);

        // Add components to the main panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);
//
//        // Log Out button functionality
//        logOut.addActionListener(evt -> {
//            if (evt.getSource().equals(logOut)) {
//                final LoggedInState currentState = welcomeViewModel.getState();
//                this.logoutController.execute(currentState.getUsername());
//            }
//        });

    }

    public String getViewName() {
        return viewName;
    }

    public void setWelcomeController(WelcomeController welcomeController) {
        this.welcomeController = welcomeController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("username".equals(evt.getPropertyName())) {
            System.out.println(welcomeViewModel.getState().getUser().getName());
            greeting.setText("Welcome, " + welcomeViewModel.getState().getUser().getName()); // Update the greeting text
        }
    }
}
