package interface_adapter.chat;

import entity.Message;
import entity.User;
import interface_adapter.ViewModel;
import use_case.chat.RealTimeChatUpdatesUseCase;
import use_case.send_message.SendMessageInputData;
import use_case.send_message.SendMessageInteractor;

import java.util.List;
import java.util.Map;

public class ChatViewModel extends ViewModel<ChatState> {

    private RealTimeChatUpdatesUseCase chatUpdatesUseCase;
    private SendMessageInteractor sendMessageInteractor;

    public ChatViewModel() {
        super("chat");
        setState(ChatState.getInstance());
    }

    public void setChatUpdatesUseCase(RealTimeChatUpdatesUseCase chatUpdatesUseCase) {
        this.chatUpdatesUseCase = chatUpdatesUseCase;
    }

    public void setSendMessageInteractor(SendMessageInteractor sendMessageInteractor) {
        this.sendMessageInteractor = sendMessageInteractor;
    }

    public void startListeningForUpdates(String groupID) {
        ChatState state = getState();

        // Listen for group member updates
        chatUpdatesUseCase.listenForGroupMembers(groupID, new RealTimeChatUpdatesUseCase.GroupMemberUpdateListener() {
            @Override
            public void onGroupMembersUpdated(List<String> members) {
                state.setMembers(members); // Update state directly
                firePropertyChanged("members");
            }

            @Override
            public void onError(Exception e) {
                System.err.println("[ChatViewModel] Error updating members: " + e.getMessage());
            }
        });

        // Listen for message updates
        chatUpdatesUseCase.listenForMessages(groupID, new RealTimeChatUpdatesUseCase.MessageUpdateListener() {
            @Override
            public void onMessagesUpdated(Map<String, String> messages) {
                messages.forEach((key, value) -> state.addMessage(key, value));
                System.out.println(state.getMessages());
                setState(state);
                firePropertyChanged("messages");
            }

            @Override
            public void onError(Exception e) {
                System.err.println("[ChatViewModel] Error updating messages: " + e.getMessage());
            }
        });
    }

    public void sendMessage(String content, User user) {
        ChatState state = getState();

        // Add message to local state for immediate feedback
        state.addMessage(user.getName(), content);
        firePropertyChanged("messages"); // Notify listeners about the updated messages

        // Create input data and call the use case
        SendMessageInputData inputData = new SendMessageInputData(user, content);
        sendMessageInteractor.sendMessage(inputData); // Send the message to the database
    }
}
