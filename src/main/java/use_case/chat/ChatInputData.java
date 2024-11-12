package use_case.chat;

public class ChatInputData {
    private final String username;
    private final String message;

    public ChatInputData(String username, String message) {
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
