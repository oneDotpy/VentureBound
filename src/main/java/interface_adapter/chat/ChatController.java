package interface_adapter.chat;

import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInputData;

public class ChatController {
    private final ChatInputBoundary chatInteractor;

    public ChatController(ChatInputBoundary chatInteractor) {
        this.chatInteractor = chatInteractor;
    }

    public void sendMessage(String message, String username) {
        if (message == null || message.trim().isEmpty()) {
            System.out.println("Message is empty or null"); // Debug statement
            return;
        }
        System.out.println("ChatController: Sending message - " + message); // Debug statement
        ChatInputData chatInputData = new ChatInputData(username, message);
        chatInteractor.sendMessage(chatInputData);
    }
}
