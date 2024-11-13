package use_case.vacation_bot;

public interface VacationBotOutputBoundary {
    void presentBotResponse(VacationBotOutputData response);
    void sendBotMessage(String sender, String message);
}
