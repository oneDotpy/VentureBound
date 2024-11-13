package interface_adapter.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatState {
    private static ChatState instance;
    private final List<String> messages = new ArrayList<>();
    private List<String> members = new ArrayList<>();
    private String currentUser;

    // Private constructor to prevent instantiation
    private ChatState() {}

    // Singleton instance getter
    public static ChatState getInstance() {
        if (instance == null) {
            instance = new ChatState();
        }
        return instance;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setCurrentUser(String username) {
        System.out.println("[ChatState] Setting current user: " + username + " (Instance: " + this + ")");
        this.currentUser = username;
    }

    public void addMessage(String sender, String message) {
        String formattedMessage;
        if (sender.equals(currentUser)) {
            formattedMessage = "You: " + message;
        } else {
            formattedMessage = sender + ": " + message;
        }
        System.out.println("[ChatState] Adding message: " + formattedMessage + " (Current User: " + currentUser + " Instance: " + this + ")");
        messages.add(formattedMessage);
    }

    public void setMembers(List<String> newMembers) {
        this.members = new ArrayList<>(newMembers);
        System.out.println("[ChatState] Members set: " + this.members + " (Instance: " + this + ")");
    }

    public List<String> getMembers() {
        System.out.println("[ChatState] Retrieving members: " + this.members + " (Instance: " + this + ")");
        return members;
    }


    public String getCurrentUser() {
        System.out.println("[ChatState] Retrieving current user: " + currentUser);
        return currentUser;
    }

}
