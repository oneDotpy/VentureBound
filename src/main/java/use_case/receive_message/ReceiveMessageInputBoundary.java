package use_case.receive_message;

/**
 * Input Boundary for actions which are related to receiving a message.
 */
public interface ReceiveMessageInputBoundary {

    /**
     * Executes the Receive Message Use Case
     * @param receiveMessageInputData the input data
     */
    void receiveMessage(ReceiveMessageInputData receiveMessageInputData);
}
