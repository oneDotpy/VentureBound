package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private LoginController loginController;

    public LoginView(LoginViewModel loginViewModel, CardLayout cardLayout, JPanel cardPanel) {
        loginViewModel.addPropertyChangeListener(this);

        // Set background color and layout
        this.setBackground(Color.decode("#2c2c2e"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding around the content

        // Title Label
        JLabel title = new JLabel("VentureBound", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Container for fields and button
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.decode("#2c2c2e"));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Inner padding for the form panel

        Dimension inputFieldSize = new Dimension(200, 30);

        // Username Label and Field
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.setBackground(Color.decode("#2c2c2e"));
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.LIGHT_GRAY);
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        usernameLabel.setPreferredSize(new Dimension(100, 30));
        usernameInputField.setPreferredSize(inputFieldSize);
        usernameInputField.setMaximumSize(inputFieldSize);
        usernamePanel.add(usernameLabel);
//        usernamePanel.add(Box.createHorizontalStrut(10)); // Space between label and field
        usernamePanel.add(usernameInputField);

        formPanel.add(usernamePanel);
        formPanel.add(Box.createVerticalStrut(10));
        // Password Label and Field
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setBackground(Color.decode("#2c2c2e"));
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.LIGHT_GRAY);
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passwordLabel.setPreferredSize(new Dimension(100, 30));
        passwordInputField.setPreferredSize(inputFieldSize);
        passwordInputField.setMaximumSize(inputFieldSize);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(10)); // Space between label and field
        passwordPanel.add(passwordInputField);

        formPanel.add(passwordPanel);
        formPanel.add(Box.createVerticalStrut(20)); // Space before login button

        // Login Button
        logIn = new JButton("Login");
        logIn.setFont(new Font("SansSerif", Font.BOLD, 16));
        logIn.setBackground(Color.decode("#8ca5e5"));
        logIn.setForeground(Color.WHITE);
        logIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logIn.setMaximumSize(new Dimension(275, 40));
        formPanel.add(logIn);

        logIn.addActionListener(evt -> {
            if (evt.getSource().equals(logIn)) {
                final LoginState currentState = loginViewModel.getState();
                loginController.execute(
                        currentState.getUsername(),
                        currentState.getPassword()
                );
            }
        });

        formPanel.add(Box.createVerticalStrut(20)); // Space before sign-up link

        // Sign Up Button
        JButton signUpButton = new JButton("Or Sign up");
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        signUpButton.setForeground(Color.decode("#8ca5e5"));
        signUpButton.setBackground(Color.decode("#2c2c2e"));
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        signUpButton.addActionListener(e -> {
            // Switch to the "signup" view
            cardLayout.show(cardPanel, "signup");
            System.out.println("Redirecting to SignUp view...");
        });

        formPanel.add(signUpButton);

        this.add(title);
        this.add(Box.createVerticalStrut(20)); // Space between title and form
        this.add(formPanel);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return "log in";
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public JLabel getPasswordErrorField() {
        return passwordErrorField;
    }
}
