package entity;

import java.io.Serializable;

public class CommonResponse implements Response{
    private final User user;
    private final String promptQuestion;
    private final String answer;

    public CommonResponse(User user, String promptQuestion, String answer) {
        this.user = user;
        this.promptQuestion = promptQuestion;
        this.answer = answer;
    }

    @Override
    public User getSender() {
        return null;
    }

    @Override
    public String getPromptQuestion() {
        return promptQuestion;
    }

    @Override
    public String getAnswer() {
        return answer;
    }
}
