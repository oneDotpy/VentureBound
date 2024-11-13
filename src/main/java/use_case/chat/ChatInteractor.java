package use_case.chat;

import interface_adapter.chat.ChatState;
import java.util.List;

public class ChatInteractor implements ChatInputBoundary {
    private final ChatOutputBoundary chatPresenter;
    private final ChatState chatState;

    public ChatInteractor(ChatOutputBoundary chatPresenter, ChatState chatState) {
        this.chatPresenter = chatPresenter;
        this.chatState = chatState;
    }

    @Override
    public void sendMessage(ChatInputData inputData) {
        String message = inputData.getMessage();
        String sender = inputData.getUsername();

        System.out.println("[ChatInteractor] Processing message from " + sender + ": " + message);

        // Pass the message to the presenter without directly updating the state
        ChatOutputData outputData = new ChatOutputData(sender, message);
        chatPresenter.presentMessage(outputData);
    }


    public List<String> getMembers() {
        return chatState.getMembers();
    }

    public void setCurrentUser(String username) {
        System.out.println("[ChatInteractor] Setting current user to: " + username);
        chatState.setCurrentUser(username);
    }

    public String getCurrentUser() {
        String user = chatState.getCurrentUser();
        System.out.println("[ChatInteractor] Getting current user: " + user);
        return user;
    }


    public void setMembers(List<String> members) {
        chatState.setMembers(members);
    }

    public void updateGroupMembers(List<String> members) {
        chatState.setMembers(members);
        chatPresenter.updateMembers(members);
    }
}
