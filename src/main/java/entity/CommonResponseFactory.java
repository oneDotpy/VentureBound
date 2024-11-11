package entity;

public class CommonResponseFactory implements ResponseFactory{
    @Override
    public Response create(String promptQuestion, String answer) {
        return new CommonResponse(promptQuestion, answer);
    }
}
