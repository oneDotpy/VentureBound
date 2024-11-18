package use_case.vacation_bot;

public class VacationBotOutputData {
    private final String message;

    public VacationBotOutputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
