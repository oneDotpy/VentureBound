package entity;

/**
 * Factory for creating Response.
 */
public interface ResponseFactory {
    /**
     * Creates a group from database.
     * @param user the user that send the message
     * @param response the response
     * @return the message
     */
    public Response create(String user, String response);
}
