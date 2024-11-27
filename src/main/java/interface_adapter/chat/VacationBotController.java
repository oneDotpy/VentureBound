package interface_adapter.chat;

import use_case.vacation_bot.VacationBotInputBoundary;

public class VacationBotController {
    private final VacationBotInputBoundary botInteractor;

    public VacationBotController(VacationBotInputBoundary botInteractor) {
        this.botInteractor = botInteractor;
    }

    public void startBot(String username) {
        botInteractor.startBot(username);
    }

    public void stopBot() {
        botInteractor.stopBot();
    }

    public void handleBotMessage(String username, String message) {
        botInteractor.handleMessage(username, message);
    }
}
