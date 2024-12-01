package use_case.chat;

import entity.User;
import interface_adapter.chat.ChatState;

import java.util.List;

/**
 * Interactor class for handling chat-related functionality.
 * Implements the input boundary for the chat use case and coordinates with the chat presenter.
 */
public class ChatInteractor implements ChatInputBoundary {
    private final ChatOutputBoundary chatPresenter;
    private final ChatState chatState;

    /**
     * Constructs a ChatInteractor instance.
     *
     * @param chatPresenter The output boundary for presenting chat data.
     * @param chatState     The current state of the chat, including group members.
     */
    public ChatInteractor(ChatOutputBoundary chatPresenter, ChatState chatState) {
        this.chatPresenter = chatPresenter;
        this.chatState = chatState;
    }

    /**
     * Sends a message and forwards it to the presenter for display.
     *
     * @param inputData Data containing the message and sender information.
     */
    @Override
    public void sendMessage(ChatInputData inputData) {
        String message = inputData.getMessage();
        String sender = inputData.getUsername();

        System.out.println("[ChatInteractor] Processing message from " + sender + ": " + message);

        // Pass the message to the presenter without directly updating the state
        ChatOutputData outputData = new ChatOutputData(sender, message);
        chatPresenter.presentMessage(outputData);
    }

    /**
     * Retrieves the list of current group members from the chat state.
     *
     * @return A list of usernames representing the group members.
     */
    public List<String> getMembers() {
        return chatState.getMembers();
    }

    /**
     * Updates the list of group members in the presenter.
     *
     * @param members A list of usernames representing the new group members.
     */
    public void setMembers(List<String> members) {
        chatPresenter.updateMembers(members);
    }
}
