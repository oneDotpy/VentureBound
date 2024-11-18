package use_case.send_message;

import entity.Message;
import entity.User;

public class SendMessageInputData {
    private final User user;
    private final String content;

    public SendMessageInputData(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public User getUser() { return user; }

    public String getContent() { return content; }

}
