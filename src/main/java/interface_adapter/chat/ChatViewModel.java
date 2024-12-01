package interface_adapter.chat;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.ListenerRegistration;
import entity.User;
import interface_adapter.ViewModel;
import use_case.chat.RealTimeChatUpdatesUseCase;
import use_case.send_message.SendMessageInputData;
import use_case.send_message.SendMessageInteractor;
import use_case.vacation_bot.VacationBotInputBoundary;

import java.util.List;
import java.util.Map;

/**
 * ViewModel for managing the chat UI and interactions.
 * Handles updates from use cases and communicates with the view.
 */
public class ChatViewModel extends ViewModel<ChatState> {

    private RealTimeChatUpdatesUseCase chatUpdatesUseCase;
    private SendMessageInteractor sendMessageInteractor;
    private VacationBotInputBoundary botInteractor; // Bot integration
    private ListenerRegistration messageListener;
    private ListenerRegistration memberListener;
    private ChatControllerInterface chatController;

    /**
     * Constructs a new ChatViewModel instance.
     */
    public ChatViewModel() {
        super("chat");
        setState(ChatState.getInstance());
    }

    /**
     * Sets the use case for real-time chat updates.
     *
     * @param chatUpdatesUseCase The use case for real-time chat updates.
     */
    public void setChatUpdatesUseCase(RealTimeChatUpdatesUseCase chatUpdatesUseCase) {
        this.chatUpdatesUseCase = chatUpdatesUseCase;
    }

    /**
     * Sets the interactor for sending messages.
     *
     * @param sendMessageInteractor The interactor for handling message sending.
     */
    public void setSendMessageInteractor(SendMessageInteractor sendMessageInteractor) {
        this.sendMessageInteractor = sendMessageInteractor;
    }

    /**
     * Sets the Vacation Bot interactor for handling bot-related commands.
     *
     * @param botInteractor The bot interactor.
     */
    public void setBotInteractor(VacationBotInputBoundary botInteractor) {
        this.botInteractor = botInteractor;
    }

    /**
     * Sets the chat controller interface.
     *
     * @param chatController The controller for handling chat interactions.
     */
    public void setController(ChatControllerInterface chatController) {
        this.chatController = chatController;
    }

    /**
     * Starts listening for updates to messages and group members.
     *
     * @param groupID The ID of the group to listen for updates.
     */
    public void startListeningForUpdates(String groupID) {
        ChatState state = getState();

        memberListener = chatUpdatesUseCase.listenForGroupMembers(groupID, new RealTimeChatUpdatesUseCase.GroupMemberUpdateListener() {
            @Override
            public void onGroupMembersUpdated(List<String> members) {
                chatController.addMembers(members);
            }

            @Override
            public void onError(Exception e) {
                System.err.println("[ChatViewModel] Error updating members: " + e.getMessage());
            }
        });

        messageListener = chatUpdatesUseCase.listenForMessages(groupID, new RealTimeChatUpdatesUseCase.MessageUpdateListener() {
            @Override
            public void onMessagesUpdated(Map<String, String> messages, Timestamp timestamp) {
                chatController.handleMessage(
                        messages.get("sender"),
                        messages.get("content"),
                        timestamp,
                        state.getCurrentUser().getName(),
                        state.getCurrentUser().getGroup().getUsernames().size(),
                        state.getCurrentUser().getGroupID()
                );
            }

            @Override
            public void onError(Exception e) {
                System.err.println("[ChatViewModel] Error updating messages: " + e.getMessage());
            }
        });
    }

    /**
     * Stops the Vacation Bot if it is currently active.
     */
    public void stopBot() {
        if (botInteractor.isBotActive()) {
            botInteractor.stopBot();
        }
    }

    /**
     * Sends a message using the interactor.
     *
     * @param content The content of the message to send.
     * @param user    The user sending the message.
     */
    public void sendMessage(String content, User user) {
        ChatState state = getState();
        System.out.println("[ChatViewModel] Received: " + content + " from " + user.getName());

        firePropertyChanged("messages"); // Notify listeners about updated messages

        SendMessageInputData inputData = new SendMessageInputData(user, content);
        sendMessageInteractor.sendMessage(inputData); // Send the message to the database
    }

    /**
     * Stops listening for message updates.
     */
    public void stopListenMessage() {
        messageListener.remove();
    }

    /**
     * Stops listening for member updates.
     */
    public void stopListenMember() {
        memberListener.remove();
    }
}
