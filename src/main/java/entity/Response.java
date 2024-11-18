package entity;

/**
 * The representation of a response from GPT API in out program.
 */
public interface Response {
    /**
     * Returns the user that is being asked.
     * @return the user
     */
    String getUser();

    /**
     * Returns the answer from GPT API.
     * @return the answer
     */
    String getResponse();
}
