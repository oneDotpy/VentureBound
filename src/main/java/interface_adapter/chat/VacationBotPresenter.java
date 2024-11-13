package interface_adapter.chat;

import use_case.vacation_bot.VacationBotOutputBoundary;
import use_case.vacation_bot.VacationBotOutputData;

public class VacationBotPresenter implements VacationBotOutputBoundary {
    private final ChatViewModel chatViewModel;

    public VacationBotPresenter(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
    }

    @Override
    public void presentBotResponse(VacationBotOutputData response) {
        // Add the bot response to the chat
        ChatState chatState = chatViewModel.getState();
        chatState.addMessage("Bot", response.getMessage());
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("messages");
        System.out.println("[VacationBotPresenter] Bot response presented: " + response.getMessage());
    }

    @Override
    public void sendBotMessage(String sender, String message) {
        // Add the bot message to the chat
        ChatState chatState = chatViewModel.getState();
        chatState.addMessage(sender, message);
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("messages");
        System.out.println("[VacationBotPresenter] Bot sent message: " + message);
    }
}
