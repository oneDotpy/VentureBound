package use_case.vacation_bot;

public class VacationBotInputData {
    private final String username;
    private final String message;

    public VacationBotInputData(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
