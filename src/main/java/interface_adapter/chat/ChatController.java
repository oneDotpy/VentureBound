package interface_adapter.chat;

import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInputData;
import use_case.chat.ChatInteractor;
import use_case.vacation_bot.VacationBotInputBoundary;
import java.util.List;

public class ChatController {
    private final ChatInputBoundary chatInteractor;
    private final VacationBotInputBoundary botInteractor;

    public ChatController(ChatInputBoundary chatInteractor, VacationBotInputBoundary botInteractor) {
        this.chatInteractor = chatInteractor;
        this.botInteractor = botInteractor;
    }

    public void sendMessage(String message, String username) {
        if (message == null || message.trim().isEmpty()) {
            System.out.println("[ChatController] Message is empty or null");
            return;
        }
        System.out.println("[ChatController] Sending message from " + username + ": " + message);

        if ("/start".equalsIgnoreCase(message.trim())) {
            botInteractor.startBot();
        } else if ("/stop".equalsIgnoreCase(message.trim())) {
            botInteractor.stopBot();
        } else if (botInteractor.isBotActive()) {
            botInteractor.handleMessage(username, message);
        } else {
            // Only send the message to the interactor, do not update the state directly here
            ChatInputData inputData = new ChatInputData(username, message);
            chatInteractor.sendMessage(inputData);
        }
    }

    public void sendBotMessage(String sender, String message) {
        System.out.println("[ChatController] Bot sending message: " + message);
        ChatInputData inputData = new ChatInputData(sender, message);
        chatInteractor.sendMessage(inputData);
    }

    public String getCurrentUser() {
        return ((ChatInteractor) chatInteractor).getCurrentUser();
    }

    public List<String> getMembers() {
        return ((ChatInteractor) chatInteractor).getMembers();
    }
}
