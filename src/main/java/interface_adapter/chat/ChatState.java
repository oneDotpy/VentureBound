package interface_adapter.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatState {
    private final List<String> messages = new ArrayList<>();
    private List<String> members = new ArrayList<>();
    private String currentUser;

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
    }

    public List<String> getMembers() {
        return members;
    }

    public String getCurrentUser() {
        System.out.println("[ChatState] Retrieving current user: " + currentUser);
        return currentUser;
    }

}
