package entity;

public class CommonResponse implements Response{
    private final String user;
    private final String response;

    public CommonResponse(String promptQuestion, String answer) {
        this.user = promptQuestion;
        this.response = answer;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getResponse() {
        return response;
    }
}
