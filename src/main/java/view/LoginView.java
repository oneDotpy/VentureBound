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
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        // Title Label
        JLabel title = new JLabel("VentureBound", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(title, gbc);

        // Username Label and Field
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.LIGHT_GRAY);
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        this.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        usernameInputField.setPreferredSize(new Dimension(200, 30));
        usernameInputField.setMaximumSize(new Dimension(200, 30));
        this.add(usernameInputField, gbc);

        // Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.LIGHT_GRAY);
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        this.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordInputField.setPreferredSize(new Dimension(200, 30));
        passwordInputField.setMaximumSize(new Dimension(200, 30));
        this.add(passwordInputField, gbc);

        // Login Button
        logIn = new JButton("Login");
        logIn.setFont(new Font("SansSerif", Font.BOLD, 16));
        logIn.setBackground(Color.decode("#8ca5e5"));
        logIn.setForeground(Color.WHITE);
        logIn.setFocusPainted(false);
        logIn.setBorderPainted(false);

        // Set fixed size for the login button
        logIn.setPreferredSize(new Dimension(200, 40));
        logIn.setMaximumSize(new Dimension(200, 40));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        this.add(logIn, gbc);

        logIn.addActionListener(e -> {
            cardLayout.show(cardPanel, "welcome");
            System.out.println("Redirecting to Welcome view...");
        });

        // Sign Up Button
        JButton signUpButton = new JButton("Or Sign up");
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        signUpButton.setForeground(Color.decode("#8ca5e5"));
        signUpButton.setBackground(Color.decode("#2c2c2e"));
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        gbc.gridy = 4;
        this.add(signUpButton, gbc);

        signUpButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "signup");
            System.out.println("Redirecting to SignUp view...");
        });
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
}
