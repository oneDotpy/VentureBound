package entity;

/**
 * The representation of a response from GPT API in out program.
 */
public interface Response {
    /**
     * Returns the question that is being asked.
     * @return the question
     */
    String getPromptQuestion();

    /**
     * Returns the answer from GPT API.
     * @return the answer
     */
    String getAnswer();
}
