package use_case.vacation;

public interface VacationBotOutputBoundary {
    void sendBotMessage(String sender, String message);
    void generateRecommendations(String location, String activities);
}
