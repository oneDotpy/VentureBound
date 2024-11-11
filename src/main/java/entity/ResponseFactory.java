package entity;

/**
 * Factory for creating Response.
 */
public interface ResponseFactory {
    /**
     * Creates a group from database.
     * @param promptQuestion the content of the message
     * @param answer the timestamp of the message
     * @return the message
     */
    public Response create(String promptQuestion, String answer);
}
