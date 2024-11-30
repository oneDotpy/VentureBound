package use_case.send_message;

/**
 * Input Boundary for actions which are related to sending a message.
 */
public interface SendMessageInputBoundary {

    /**
     * Executes the Send Message Use Case
     * @param sendMessageInputData the input data
     */
    void sendMessage(SendMessageInputData sendMessageInputData);
}
