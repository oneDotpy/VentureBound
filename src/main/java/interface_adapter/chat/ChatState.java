package interface_adapter.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatState {
    private final List<String> messages = new ArrayList<>();
    private final List<String> members = new ArrayList<>();
    private String currentMessage;
    private String currentUser;

    // Methods to manage messages
    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(String message) {
        this.currentMessage = message;
    }

    public void clearCurrentMessage() {
        this.currentMessage = "";
    }

    // Methods to manage members
    public List<String> getMembers() {
        return new ArrayList<>(members);
    }

    public void setMembers(List<String> newMembers) {
        members.clear();
        members.addAll(newMembers);
    }

    public void addMember(String member) {
        if (!members.contains(member)) {
            members.add(member);
        }
    }

    // User management
    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
    }
}
