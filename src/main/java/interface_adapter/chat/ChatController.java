package interface_adapter.chat;

import com.google.cloud.Timestamp;
import entity.User;
import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInputData;
import use_case.chat.ChatInteractor;
import use_case.leave_group.LeaveGroupInputBoundary;
import use_case.leave_group.LeaveGroupInputData;
import use_case.receive_message.ReceiveMessageInputBoundary;
import use_case.receive_message.ReceiveMessageInputData;
import use_case.vacation_bot.VacationBotInputBoundary;
import java.util.List;

public class ChatController {
    private final ChatInputBoundary chatInteractor;
    private final LeaveGroupInputBoundary leaveGroupInteractor;
    private final VacationBotInputBoundary botInteractor;
    private final ReceiveMessageInputBoundary receiveMessageInteractor;

    public ChatController(ChatInputBoundary chatInteractor, LeaveGroupInputBoundary leaveGroupInteractor, VacationBotInputBoundary botInteractor, ReceiveMessageInputBoundary receiveMessageInteractor) {
        this.chatInteractor = chatInteractor;
        this.leaveGroupInteractor = leaveGroupInteractor;
        this.botInteractor = botInteractor;
        this.receiveMessageInteractor = receiveMessageInteractor;
    }

    public void leaveGroup(User user) {
        LeaveGroupInputData leaveGroupInputData = new LeaveGroupInputData(user);
        leaveGroupInteractor.leaveGroup(leaveGroupInputData);
    }

//    public void sendMessage(String message, String username) {
//        if (message == null || message.trim().isEmpty()) {
//            System.out.println("[ChatController] Message is empty or null");
//            return;
//        }
//        System.out.println("[ChatController] Sending message from " + username + ": " + message);
//
//        if ("/start".equalsIgnoreCase(message.trim())) {
//            botInteractor.startBot(username);
//        } else if ("/stop".equalsIgnoreCase(message.trim())) {
//            botInteractor.stopBot();
//        } else if (botInteractor.isBotActive()) {
//            botInteractor.handleMessage(username, message);
//        } else {
//            // Only send the message to the interactor, do not update the state directly here
//            ChatInputData inputData = new ChatInputData(username, message, null, null);
//            chatInteractor.sendMessage(inputData);
//        }
//    }

    public void sendBotMessage(String sender, String message) {
        System.out.println("[ChatController] Bot sending message: " + message);
        ChatInputData inputData = new ChatInputData(sender, message, null, null);
        chatInteractor.sendMessage(inputData);
    }

    public void addMembers(List<String> members) {
        chatInteractor.setMembers(members);
    }

    public void handleMessage(String sender, String content, Timestamp timestamp, String currentUser, int groupSize, String groupID) {

        if (sender.equals(currentUser)) {
            if (content.trim().equalsIgnoreCase("/start")) {
                botInteractor.startBot(groupID);
                return;
            }

            else if (content.trim().equalsIgnoreCase("/stop")) {
                botInteractor.stopBot();
                return;
            }
            else {
                ReceiveMessageInputData receiveMessageInputData = new ReceiveMessageInputData(sender, content, currentUser, timestamp);
                receiveMessageInteractor.showMessage(receiveMessageInputData);
                return;
            }
        }

        else if (botInteractor.isBotActive()) {
            botInteractor.handleMessage(sender, content, groupSize, groupID);
            return;
        }

        else {
            ReceiveMessageInputData receiveMessageInputData = new ReceiveMessageInputData(sender, content, currentUser, timestamp);
            receiveMessageInteractor.showMessage(receiveMessageInputData);
            return;
        }
    }
}
