package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JTextField usernameInputField = new JTextField(15);
    private final JTextField emailInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField passwordRepeatInputField = new JPasswordField(15);
    private final JButton signUpButton;
    private final SignupViewModel signupViewModel;
    private SignupController signupController;

    public SignupView(SignupViewModel signupViewModel, CardLayout cardLayout, JPanel cardPanel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

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
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.LIGHT_GRAY);
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        this.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        usernameInputField.setPreferredSize(new Dimension(200, 30));
        this.add(usernameInputField, gbc);

        // Email Label and Field
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.LIGHT_GRAY);
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        this.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        emailInputField.setPreferredSize(new Dimension(200, 30));
        this.add(emailInputField, gbc);

        // Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.LIGHT_GRAY);
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        this.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordInputField.setPreferredSize(new Dimension(200, 30));
        this.add(passwordInputField, gbc);

        // Repeat Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordRepeatLabel = new JLabel("Re-enter Password");
        passwordRepeatLabel.setForeground(Color.LIGHT_GRAY);
        passwordRepeatLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        this.add(passwordRepeatLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordRepeatInputField.setPreferredSize(new Dimension(200, 30));
        this.add(passwordRepeatInputField, gbc);

        // Sign Up Button
        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        signUpButton.setBackground(Color.decode("#8ca5e5"));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.setBorderPainted(false);
        signUpButton.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        this.add(signUpButton, gbc);

        signUpButton.addActionListener(evt -> {
            if (evt.getSource().equals(signUpButton)) {
                final SignupState currentState = signupViewModel.getState();
                signupController.execute(
                        currentState.getUsername(),
                        currentState.getEmail(),
                        currentState.getPassword(),
                        currentState.getRepeatPassword()
                );

                cardLayout.show(cardPanel, "login");
                System.out.println("Signup successful. Redirecting to Login view...");
            }
        });

        // Log In Button
        JButton loginButton = new JButton("Or Log In");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setForeground(Color.decode("#8ca5e5"));
        loginButton.setBackground(Color.decode("#2c2c2e"));
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        gbc.gridy = 7;
        this.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "login");
            System.out.println("Redirecting to Login view...");
        });

        // Add listeners for password fields
        addUsernameListener();
        addEmailListener();
        addPasswordListener();
        addRepeatPasswordListener();
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(new String(usernameInputField.getText()));
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    private void addEmailListener() {
        emailInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setEmail(new String(emailInputField.getText()));
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    private void addRepeatPasswordListener() {
        passwordRepeatInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(passwordRepeatInputField.getPassword()));
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
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
        return "signup";
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
