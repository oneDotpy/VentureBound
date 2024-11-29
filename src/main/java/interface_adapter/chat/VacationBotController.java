package interface_adapter.chat;

import use_case.vacation_bot.VacationBotInputBoundary;

public class VacationBotController {
    private final VacationBotInputBoundary botInteractor;

    public VacationBotController(VacationBotInputBoundary botInteractor) {
        this.botInteractor = botInteractor;
    }

    public void startBot(String username) {
        botInteractor.startBot("",10);
    }

    public void stopBot() {
        botInteractor.stopBot();
    }

    public void handleBotMessage(String username, String message, int groupSize) {
        botInteractor.handleMessage(username, message, groupSize, "");
    }
}
