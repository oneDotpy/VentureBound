package use_case.chat;

public class ChatOutputData {
    private final String message;

    public ChatOutputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
