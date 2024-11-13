package use_case.chat;

public class ChatOutputData {
    private final String sender;
    private final String message;

    public ChatOutputData(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
