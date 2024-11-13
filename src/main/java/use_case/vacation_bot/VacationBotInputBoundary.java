package use_case.vacation_bot;

public interface VacationBotInputBoundary {
    void startBot();
    void stopBot();
    boolean isBotActive();
    void handleMessage(String username, String message);
}
