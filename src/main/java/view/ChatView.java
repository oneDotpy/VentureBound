package view;

import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ChatView extends JPanel implements PropertyChangeListener {
    private final JLabel groupNameLabel;
    private final JButton leaveGroupButton;
    private final JTextArea chatArea;
    private final JTextField messageInputField;
    private final JButton sendButton;
    private final DefaultListModel<String> membersListModel;
    private final JList<String> membersList;
    private final ChatViewModel chatViewModel;
    private ChatController chatController;

    public ChatView(ChatViewModel chatViewModel, ChatController chatController, String groupName, List<String> members, CardLayout cardLayout, JPanel cardPanel) {
        this.chatViewModel = chatViewModel;
        this.chatController = chatController;

        // Add this class as a property change listener to the ViewModel
        chatViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode("#2c2c2e"));

        // Left Panel: Group Information
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.decode("#2c2c2e"));
        leftPanel.setPreferredSize(new Dimension(200, 0));

        // Group Name Label
        groupNameLabel = new JLabel(groupName);
        groupNameLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        groupNameLabel.setForeground(Color.WHITE);
        groupNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Leave Group Button
        leaveGroupButton = new JButton("Simulate");
        leaveGroupButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        leaveGroupButton.setBackground(Color.decode("#8b0000"));
        leaveGroupButton.setForeground(Color.WHITE);
        leaveGroupButton.setFocusPainted(false);
        leaveGroupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        leaveGroupButton.addActionListener(e -> {
//            System.out.println("Leaving group...");
//            cardLayout.show(cardPanel, "welcome");
//        });
        leaveGroupButton.addActionListener(new SimulButtonListener()); // Temporary

        // Members Label
        JLabel membersLabel = new JLabel("Members:");
        membersLabel.setForeground(Color.LIGHT_GRAY);
        membersLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        // Members List
        membersListModel = new DefaultListModel<>();
        updateMembers(members);
        membersList = new JList<>(membersListModel);
        membersList.setBackground(Color.decode("#2c2c2e"));
        membersList.setForeground(Color.WHITE);

        // Adding components to left panel
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(groupNameLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(leaveGroupButton);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(membersLabel);
        leftPanel.add(new JScrollPane(membersList));

        // Right Panel: Chat Area
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.decode("#2c2c2e"));

        // Chat Area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(Color.decode("#2c2c2e"));
        chatArea.setForeground(Color.WHITE);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Message Input and Send Button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(Color.decode("#2c2c2e"));
        messageInputField = new JTextField();
        messageInputField.setBackground(Color.decode("#f5f5f5"));
        messageInputField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        sendButton.setBackground(Color.decode("#4a5f8a"));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setPreferredSize(new Dimension(80, 30));
        sendButton.addActionListener(new SendButtonListener());

        inputPanel.add(messageInputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Adding components to right panel
        rightPanel.add(chatScrollPane, BorderLayout.CENTER);
        rightPanel.add(inputPanel, BorderLayout.SOUTH);

        // Add panels to main panel
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);
    }

    // Listener for Send Button
    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = messageInputField.getText().trim();
            if (!message.isEmpty()) {
                System.out.println("[ChatView] Sending message: " + message); // Debug statement
                chatController.sendMessage(message, chatViewModel.getCurrentUser());
                messageInputField.setText("");
            } else {
                System.out.println("[ChatView] No message to send."); // Debug statement
            }
        }
    }

    // [TEMP] Simulation button listener
    private class SimulButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String simulatedUser = "Alice";
            String simulatedMessage = "Hi everyone!";
            chatViewModel.simulateMemberMessage(simulatedUser, simulatedMessage);
            messageInputField.setText("");
        }
    }



    // Method to update chat area with new messages
    public void addMessage(String user, String message) {
        chatArea.append(user + ": " + message + "\n");
    }

    // Method to update members list
    public void updateMembers(List<String> members) {
        membersListModel.clear();
        members.forEach(membersListModel::addElement);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("messages".equals(evt.getPropertyName())) {
            List<String> messages = chatViewModel.getState().getMessages();
            chatArea.setText(String.join("\n", messages));
        } else if ("members".equals(evt.getPropertyName())) {
            updateMembers(chatViewModel.getMembers());
        }
    }

    public String getViewName() {
        return "chat";
    }

    public void setChatController(ChatController controller) {
        this.chatController = controller;
    }
}