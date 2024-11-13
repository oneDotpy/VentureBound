package interface_adapter.chat;

import use_case.vacation_bot.VacationBotOutputBoundary;
import use_case.vacation_bot.VacationBotOutputData;

public class VacationBotPresenter implements VacationBotOutputBoundary {
    private final ChatViewModel chatViewModel;
    private final ChatState chatState = ChatState.getInstance(); // Use singleton

    public VacationBotPresenter(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
    }

    @Override
    public void presentBotResponse(VacationBotOutputData response) {
        chatState.addMessage("Bot", response.getMessage());
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("messages");
        System.out.println("[VacationBotPresenter] Bot response presented: " + response.getMessage());
    }

    @Override
    public void sendBotMessage(String sender, String message) {
        chatState.addMessage(sender, message);
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("messages");
        System.out.println("[VacationBotPresenter] Bot sent message: " + message);
    }
}
