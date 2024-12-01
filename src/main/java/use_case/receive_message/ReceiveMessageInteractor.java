package use_case.receive_message;

import com.google.cloud.Timestamp;
import entity.Message;
import entity.MessageFactory;

/**
 * The Receive Message Interactor.
 */
public class ReceiveMessageInteractor implements ReceiveMessageInputBoundary{
    private final ReceiveMessageOutputBoundary presenter;
    private final MessageFactory messageFactory;

    public ReceiveMessageInteractor(ReceiveMessageOutputBoundary presenter, MessageFactory messageFactory) {
        this.presenter = presenter;
        this.messageFactory = messageFactory;
    }

    @Override
    public void receiveMessage(ReceiveMessageInputData receiveMessageInputData) {
        String currentUser = receiveMessageInputData.getCurrentUser();
        Message message = receiveMessageInputData.getMessage();
        String sender = message.getSender();
        String content = message.getContent();
        Timestamp timestamp = message.getTimestamp();
        String formattedMessage;
        if (sender.equals(currentUser)) {
            formattedMessage = "You: " + content;
        } else {
            formattedMessage = sender + ": " + content;
        }

        Message messageEntity = messageFactory.createMessage(sender, content, timestamp);

        ReceiveMessageOutputData response = new ReceiveMessageOutputData(formattedMessage, messageEntity);
        presenter.showMessage(response);
    }
}
