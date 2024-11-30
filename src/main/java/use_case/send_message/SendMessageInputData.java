package use_case.send_message;

import entity.User;

/**
 * The input data for the Send Message Use Case.
 */
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
