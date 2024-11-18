package entity;

public class CommonResponseFactory implements ResponseFactory{
    @Override
    public Response create(String user, String response) {
        return new CommonResponse(user, response);
    }
}
