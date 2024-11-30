package use_case.receive_message;

/**
 * The output boundary for the Receive Message Use Case.
 */
public interface ReceiveMessageOutputBoundary {

    /**
     * show the message received
     * @param response the message received
     */
    void showMessage(ReceiveMessageOutputData response);
}
