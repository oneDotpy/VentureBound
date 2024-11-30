package use_case.send_message;

import com.google.cloud.Timestamp;
import data_access.FirestoreGroupDataAccessObject;
import data_access.FirestoreUserDataAccessObject;
import entity.Message;
import entity.MessageFactory;

public class SendMessageInteractor implements SendMessageInputBoundary {
    private final FirestoreUserDataAccessObject userDataAccessObject;
    private final FirestoreGroupDataAccessObject groupDataAccessObject;
    private final MessageFactory messageFactory;

    public SendMessageInteractor(FirestoreUserDataAccessObject userDataAccessObject,
                                 FirestoreGroupDataAccessObject groupDataAccessObject,
                                 MessageFactory messageFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.groupDataAccessObject = groupDataAccessObject;
        this.messageFactory = messageFactory;
    }

    @Override
    public void sendMessage(SendMessageInputData sendMessageInputData) {

        String sender = sendMessageInputData.getUser().getName();
        String content = sendMessageInputData.getContent();
        Timestamp timestamp = userDataAccessObject.getTimestamp(sender);

        Message message = messageFactory.createMessage(sender, content, timestamp);
        String groupID = sendMessageInputData.getUser().getGroup().getGroupID();
        System.out.println("[SMI] Recieve Message : " + message + " from : " + sender + " in " + groupID);
        groupDataAccessObject.updateMessage(groupID, message);
    }
}
