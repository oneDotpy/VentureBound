package interface_adapter.chat;
import java.util.ArrayList;
import java.util.List;

public class ChatState {
    private final List<String> messages = new ArrayList<>();
    private final List<String> members = new ArrayList<>();
    private String currentMessage;
    private String currentUser;

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void addMessage(String sender, String message) {
        String formattedMessage;
        if (sender.equals(currentUser)) {
            formattedMessage = "You: " + message;
        } else {
            formattedMessage = sender + ": " + message;
        }
        messages.add(formattedMessage);
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

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
    }
}
