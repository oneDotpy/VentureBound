package use_case.vacation_bot;

public interface VacationBotInputBoundary {
    void startBot(String groupID, int threshold);
    void stopBot();
    boolean isBotActive();
    void handleMessage(String username, String message, int groupSize, String groupID);

    void setThreshold(int threshold);
}
