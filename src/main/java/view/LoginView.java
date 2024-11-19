package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupState;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private boolean loginSuccess;

    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);

    private final JButton logInButton;
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
        logInButton = new JButton("Login");
        logInButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        logInButton.setBackground(Color.decode("#8ca5e5"));
        logInButton.setForeground(Color.WHITE);
        logInButton.setFocusPainted(false);
        logInButton.setBorderPainted(false);
        logInButton.setPreferredSize(new Dimension(200, 40));
        logInButton.setMaximumSize(new Dimension(200, 40));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        this.add(logInButton, gbc);

        // Action listener for Login button
        logInButton.addActionListener(evt -> {
            if (evt.getSource().equals(logInButton)) {
                final LoginState currentState = loginViewModel.getState();
                loginController.execute(
                        currentState.getUsername(),
                        currentState.getPassword()
                );
                // If there is no error go to the next view
                if (currentState.getLoginError() == null) {
                    System.out.println("User has a group? " + currentState.isUserHasGroup());
                    if (currentState.isUserHasGroup()) {
                        cardLayout.show(cardPanel, "chat");
                        System.out.println("Redirecting to chat view...");
                    } else {
                        cardLayout.show(cardPanel, "welcome");
                        System.out.println("Redirecting to welcome view...");
                    }
                }
                currentState.setLoginError(null);
            }
        });

        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });


        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
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
        if (state.getLoginError() != null) {
            JOptionPane.showMessageDialog(this, state.getLoginError());
        }
        setFields(state);
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return "login";
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
