package interface_adapter.chat;

import entity.User;

import java.util.ArrayList;
import java.util.List;

public class ChatState {
    private static ChatState instance;
    private List<String> messages = new ArrayList<>();
    private List<String> members = new ArrayList<>();
    private User currentUser;
    private User user;
    private String groupName;

    private ChatState() {}

    public static ChatState getInstance() {
        if (instance == null) {
            instance = new ChatState();
        }
        return instance;
    }


    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void setCurrentUser(User user) {
        System.out.println("[ChatState] Setting current user: " + user + " (Instance: " + this + ")");
        this.currentUser = user;
    }

    public void setUser (User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void addMessage(String sender, String message) {
        String formattedMessage;
        if (sender.equals(currentUser.getName())) {
            formattedMessage = "You: " + message;
        } else {
            formattedMessage = sender + ": " + message;
        }
        System.out.println("[ChatState] Adding message: " + formattedMessage + " (Current User: " + currentUser + " Instance: " + this + ")");
        messages.add(formattedMessage);
    }


    // New Implementation
    public void addMessages(String formattedMessage) {
        messages.add(formattedMessage);
    }
    // New Implementations



    public void setMembers(List<String> newMembers) {
        this.members = new ArrayList<>(newMembers);
        System.out.println("[ChatState] Members set: " + this.members + " (Instance: " + this + ")");
    }

    public void addMember(String member) {
        this.members.add(member);
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
        System.out.println("[ChatState] Setting group name: " + groupName + " (Instance: " + this + ")");
    }

    public String getGroupName() {
        return groupName;
    }

    public List<String> getMembers() {
        System.out.println("[ChatState] Retrieving members: " + this.members + " (Instance: " + this + ")");
        return members;
    }


    public User getCurrentUser() {
        System.out.println("[ChatState] Retrieving current user: " + currentUser);
        return currentUser;
    }

}
