package entity;

public class CommonResponse implements Response{
    private final String promptQuestion;
    private final String answer;

    public CommonResponse(String promptQuestion, String answer) {
        this.promptQuestion = promptQuestion;
        this.answer = answer;
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
