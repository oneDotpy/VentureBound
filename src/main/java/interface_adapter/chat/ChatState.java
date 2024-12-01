package interface_adapter.chat;

import entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class representing the state of the chat.
 * Manages messages, group members, and the current user in the chat.
 */
public class ChatState {
    private static ChatState instance;
    private List<String> messages = new ArrayList<>();
    private List<String> members = new ArrayList<>();
    private User currentUser;
    private User user;
    private String groupName;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private ChatState() {}

    /**
     * Retrieves the singleton instance of ChatState.
     *
     * @return The singleton instance of ChatState.
     */
    public static ChatState getInstance() {
        if (instance == null) {
            instance = new ChatState();
        }
        return instance;
    }

    /**
     * Retrieves the list of messages in the chat.
     *
     * @return A copy of the list of messages.
     */
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    /**
     * Sets the list of messages in the chat.
     *
     * @param messages The new list of messages.
     */
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    /**
     * Sets the current user interacting with the chat.
     *
     * @param user The current user.
     */
    public void setCurrentUser(User user) {
        System.out.println("[ChatState] Setting current user: " + user + " (Instance: " + this + ")");
        this.currentUser = user;
    }

    /**
     * Sets the user information for the chat state.
     *
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Retrieves the user information for the chat state.
     *
     * @return The user information.
     */
    public User getUser() {
        return user;
    }

    /**
     * Adds a message to the chat, formatting it based on the sender.
     *
     * @param sender  The username of the sender.
     * @param message The content of the message.
     */
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

    /**
     * Adds a pre-formatted message to the chat.
     *
     * @param formattedMessage The formatted message to add.
     */
    public void addMessages(String formattedMessage) {
        messages.add(formattedMessage);
    }

    /**
     * Sets the list of members in the chat group.
     *
     * @param newMembers The new list of members.
     */
    public void setMembers(List<String> newMembers) {
        this.members = new ArrayList<>(newMembers);
        System.out.println("[ChatState] Members set: " + this.members + " (Instance: " + this + ")");
    }

    /**
     * Adds a new member to the chat group.
     *
     * @param member The member to add.
     */
    public void addMember(String member) {
        this.members.add(member);
    }

    /**
     * Sets the group name for the chat.
     *
     * @param groupName The group name to set.
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
        System.out.println("[ChatState] Setting group name: " + groupName + " (Instance: " + this + ")");
    }

    /**
     * Retrieves the group name for the chat.
     *
     * @return The group name.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Retrieves the list of members in the chat group.
     *
     * @return A list of members in the chat group.
     */
    public List<String> getMembers() {
        System.out.println("[ChatState] Retrieving members: " + this.members + " (Instance: " + this + ")");
        return members;
    }

    /**
     * Retrieves the current user interacting with the chat.
     *
     * @return The current user.
     */
    public User getCurrentUser() {
        System.out.println("[ChatState] Retrieving current user: " + currentUser);
        return currentUser;
    }
}
