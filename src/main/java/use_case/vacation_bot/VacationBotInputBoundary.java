package use_case.vacation_bot;

public interface VacationBotInputBoundary {
    void startBot(String username);
    void stopBot();
    boolean isBotActive();
    void handleMessage(String username, String message);
}
