package use_case.chat;

public class ChatOutputData {
    private final String message;
    private final String sender;

    public ChatOutputData(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() { return sender; }
}
