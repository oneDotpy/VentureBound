package use_case.vacation_bot;

public interface VacationBotInputBoundary {
    void startBot(String groupID);
    void stopBot();
    boolean isBotActive();
    void handleMessage(String username, String message, int groupSize, String groupID);
}
