package interface_adapter.chat;

import com.google.cloud.Timestamp;
import entity.Message;
import entity.MessageFactory;
import entity.User;
import use_case.chat.ChatInputBoundary;
import use_case.leave_group.LeaveGroupInputBoundary;
import use_case.leave_group.LeaveGroupInputData;
import use_case.receive_message.ReceiveMessageInputBoundary;
import use_case.receive_message.ReceiveMessageInputData;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInputData;
import use_case.vacation_bot.VacationBotInputBoundary;
import use_case.vacation_bot.VacationBotInputData;

import java.util.List;

public class ChatController implements ChatControllerInterface {
    private final MessageFactory messageFactory;
    private final ChatInputBoundary chatInteractor;
    private final LeaveGroupInputBoundary leaveGroupInteractor;
    private final VacationBotInputBoundary botInteractor;
    private final SendMessageInputBoundary sendMessageInteractor;
    private final ReceiveMessageInputBoundary receiveMessageInteractor;

    public ChatController(MessageFactory messageFactory,
                          ChatInputBoundary chatInteractor,
                          LeaveGroupInputBoundary leaveGroupInteractor,
                          VacationBotInputBoundary botInteractor,
                          SendMessageInputBoundary sendMessageInteractor,
                          ReceiveMessageInputBoundary receiveMessageInteractor) {
        this.messageFactory = messageFactory;
        this.chatInteractor = chatInteractor;
        this.leaveGroupInteractor = leaveGroupInteractor;
        this.botInteractor = botInteractor;
        this.sendMessageInteractor = sendMessageInteractor;
        this.receiveMessageInteractor = receiveMessageInteractor;
    }

    public void leaveGroup(User user) {
        LeaveGroupInputData leaveGroupInputData = new LeaveGroupInputData(user);
        botInteractor.stopBot();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        leaveGroupInteractor.leaveGroup(leaveGroupInputData);
    }

    public void addMembers(List<String> members) {
        chatInteractor.setMembers(members);
        botInteractor.removeResponse(members);
        botInteractor.setThreshold(members.size());
    }

    public void sendMessage(String content, User user) {
        System.out.println("[CVM3] Receive: " + content + "from" + user.getName());

        // Create input data and call the send message use case
        SendMessageInputData inputData = new SendMessageInputData(user, content);
        System.out.println(inputData.getContent() + inputData.getUser().getName());
        sendMessageInteractor.sendMessage(inputData);
    }

    public void handleMessage(String sender, String content, Timestamp timestamp, String currentUser, int groupSize, String groupID) {
        Message message = messageFactory.createMessage(sender, content, timestamp);
        ReceiveMessageInputData receiveMessageInputData = new ReceiveMessageInputData(currentUser, message);
        receiveMessageInteractor.receiveMessage(receiveMessageInputData);

        if (sender.equals(currentUser)) {
            if (content.trim().equalsIgnoreCase("/getid")) {
                botInteractor.createBotUser(groupID);
                botInteractor.sendGroupID(groupID);
                return;
            }

            else if (content.trim().equalsIgnoreCase("/start")) {
                VacationBotInputData inputData = new VacationBotInputData(groupID, groupSize);
                botInteractor.startBot(inputData);
                return;
            }

            else if (content.trim().equalsIgnoreCase("/stop")) {
                botInteractor.stopBot();
                return;
            }
        }

        if (botInteractor.isBotActive() && !sender.equals("Bot")) {
            botInteractor.handleMessage(sender, content, groupSize, groupID);
        }
    }
}
