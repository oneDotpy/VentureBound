package use_case.vacation_bot;

import java.util.List;

public interface VacationBotInputBoundary {
    void startBot(String groupID, int threshold);

    void stopBot();

    boolean isBotActive();

    void sendBotMessage(String botMessage);

    void handleMessage(String username, String message, int groupSize, String groupID);

    void setThreshold(int threshold);

    void removeResponse(List<String> members);
}
