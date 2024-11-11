package entity;

/**
 * Factory for creating Response.
 */
public interface ResponseFactory {
    /**
     * Creates a group from database.
     * @param user the sender of the message
     * @param promptQuestion the content of the message
     * @param answer the timestamp of the message
     * @return the message
     */
    public Response create(User user, String promptQuestion, String answer);
}
