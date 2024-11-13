package interface_adapter.chat;

import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInputData;

public class ChatController {
    private final ChatInputBoundary chatInteractor;
    private final ChatViewModel chatViewModel;
    private final VacationBotManager vacationBotManager;

    public ChatController(ChatInputBoundary chatInteractor, ChatViewModel chatViewModel) {
        this.chatInteractor = chatInteractor;
        this.chatViewModel = chatViewModel;
        this.vacationBotManager = new VacationBotManager(this, chatViewModel);
    }

    /**
     * Handles sending a message, either to the bot or to the chat.
     * @param message - The message to send
     * @param username - The sender's username
     */
    public void sendMessage(String message, String username) {
        if (message == null || message.trim().isEmpty()) {
            System.out.println("[ChatController] Message is empty or null");
            return;
        }

        System.out.println("[ChatController] Received message from " + username + ": " + message);

        // Check if the message is the command to start the bot
        if ("/start".equalsIgnoreCase(message.trim()) && !vacationBotManager.isBotActive()) {
            vacationBotManager.startBot();
            return;
        }

        else if ("/stop".equalsIgnoreCase(message.trim()) && vacationBotManager.isBotActive()) {
            vacationBotManager.endBot();
            return;
        }

        // If the bot is active, handle the message with the bot
        if (vacationBotManager.isBotActive()) {
            vacationBotManager.handleMessage(username, message);
        } else {
            // Regular chat message
            ChatInputData chatInputData = new ChatInputData(username, message);
            chatInteractor.sendMessage(chatInputData);
        }
    }

    /**
     * Method to send a message directly from the bot to the chat.
     * @param sender - The sender's name (e.g., "Bot")
     * @param message - The message content
     */
    public void sendBotMessage(String sender, String message) {
        System.out.println("[ChatController] Bot sending message: " + message);
        ChatInputData chatInputData = new ChatInputData(sender, message);
        chatInteractor.sendMessage(chatInputData);
    }
}