package interface_adapter.chat;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.ListenerRegistration;
import entity.Group;
import entity.Message;
import entity.User;
import interface_adapter.ViewModel;
import use_case.chat.RealTimeChatUpdatesUseCase;
import use_case.send_message.SendMessageInputData;
import use_case.send_message.SendMessageInteractor;
import use_case.vacation_bot.VacationBotInputBoundary;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChatViewModel extends ViewModel<ChatState> {

    private RealTimeChatUpdatesUseCase chatUpdatesUseCase;
    private SendMessageInteractor sendMessageInteractor;
    private VacationBotInputBoundary botInteractor; // Bot integration
    private ListenerRegistration messageListener;
    private ListenerRegistration memberListener;
    private ChatControllerInterface chatController;

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

    public void setBotInteractor(VacationBotInputBoundary botInteractor) {
        this.botInteractor = botInteractor; // Inject the bot interactor
    }

    public void setController(ChatControllerInterface chatController) {
        this.chatController = chatController;
    }

    public void startListeningForUpdates(String groupID) {
        ChatState state = getState();

        // Listen for group member updates
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

        // Listen for message updates/
        messageListener = chatUpdatesUseCase.listenForMessages(groupID, new RealTimeChatUpdatesUseCase.MessageUpdateListener() {
            @Override
            public void onMessagesUpdated(Map<String, String> messages, Timestamp timestamp) {
//                System.out.println("[CVM1]" + messages);
//                System.out.println("[1] BOT Condition: " + botInteractor + " Bot Is Active: " + botInteractor.isBotActive());

                String sender = messages.get("sender");
                String message = messages.get("content");
                String currentUser = state.getCurrentUser().getName();
                int groupSize = state.getCurrentUser().getGroup().getUsernames().size();
                String groupID = state.getCurrentUser().getGroupID();

                chatController.handleMessage(sender, message, timestamp, currentUser, groupSize, groupID);
            }


            @Override
            public void onError(Exception e) {
                System.err.println("[ChatViewModel] Error updating messages: " + e.getMessage());
            }
        });
    }

    public void stopBot() {
        if (botInteractor.isBotActive()) {
            botInteractor.stopBot();
        }
    }

    public void sendMessage(String content, User user) {
        ChatState state = getState();
        System.out.println("[CVM3] Receive: " + content + "from" + user.getName());

        // Add message to local state for immediate feedback
        firePropertyChanged("messages"); // Notify listeners about the updated messages

        // Create input data and call the use case
        SendMessageInputData inputData = new SendMessageInputData(user, content);
        System.out.println(inputData.getContent() + inputData.getUser().getName());
        sendMessageInteractor.sendMessage(inputData); // Send the message to the database
    }

    public void stopListenMessage(){
        messageListener.remove();
    }

    public void stopListenMember() {
        memberListener.remove();
    }
}