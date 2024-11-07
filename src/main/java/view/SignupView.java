package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JPasswordField passwordRInputField = new JPasswordField(15);
    private final JLabel passwordRErrorField = new JLabel();

    private final JButton signUpButton;
    private final SignupViewModel signupViewModel;
    private SignupController signupController;

    public SignupView(SignupViewModel signupViewModel , CardLayout cardLayout, JPanel cardPanel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

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
        passwordPanel.add(Box.createHorizontalStrut(10));
        passwordPanel.add(passwordInputField);

        formPanel.add(passwordPanel);
        formPanel.add(Box.createVerticalStrut(10));

        // Re-enter Password Label and Field
        JPanel passwordRPanel = new JPanel();
        passwordRPanel.setLayout(new BoxLayout(passwordRPanel, BoxLayout.X_AXIS));
        passwordRPanel.setBackground(Color.decode("#2c2c2e"));
        JLabel passwordRLabel = new JLabel("Re-enter Password");
        passwordRLabel.setForeground(Color.LIGHT_GRAY);
        passwordRLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passwordRLabel.setPreferredSize(new Dimension(100, 30));
        passwordRInputField.setPreferredSize(inputFieldSize);
        passwordRInputField.setMaximumSize(inputFieldSize);
        passwordRPanel.add(passwordRLabel);
        passwordRPanel.add(Box.createHorizontalStrut(10));
        passwordRPanel.add(passwordRInputField);

        formPanel.add(passwordRPanel);
        formPanel.add(Box.createVerticalStrut(20));

        // Sign Up Button
        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        signUpButton.setBackground(Color.decode("#8ca5e5"));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setMaximumSize(new Dimension(275, 40));
        formPanel.add(signUpButton);

        signUpButton.addActionListener(evt -> {
            if (evt.getSource().equals(signUpButton)) {
                final SignupState currentState = signupViewModel.getState();
                signupController.execute(
                        currentState.getUsername(),
                        currentState.getPassword(),
                        currentState.getRepeatPassword()
                );
            }
        });

        formPanel.add(Box.createVerticalStrut(20));

        // Log In Button
        JButton loginButton = new JButton("Or Log In");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setForeground(Color.decode("#8ca5e5"));
        loginButton.setBackground(Color.decode("#2c2c2e"));
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.addActionListener(e -> {
            // Switch back to the "login" view
            cardLayout.show(cardPanel, "login");
            System.out.println("Redirecting to Login view...");
        });

        // Add loginButton to the panel
        formPanel.add(loginButton);

        // Add components to the main panel
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(formPanel);
    }


    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    private void addRepeatPasswordListener() {
        passwordRInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(passwordRInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return "sign up";
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
