package entity;

public class CommonResponseFactory implements ResponseFactory{
    @Override
    public Response create(User user, String promptQuestion, String answer) {
        return new CommonResponse(user, promptQuestion, answer);
    }
}
