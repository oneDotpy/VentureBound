package use_case.send_message;

import com.google.cloud.Timestamp;
import data_access.FirestoreGroupDataAccessObject;
import entity.Message;
import entity.MessageFactory;
import java.time.Instant;

public class SendMessageInteractor {
    private final FirestoreGroupDataAccessObject groupDataAccessObject;
    private final MessageFactory messageFactory;

    public SendMessageInteractor(FirestoreGroupDataAccessObject groupDataAccessObject
            , MessageFactory messageFactory) {
        this.groupDataAccessObject = groupDataAccessObject;
        this.messageFactory = messageFactory;
    }

    public void sendMessage(SendMessageInputData sendMessageInputData) {

        String sender = sendMessageInputData.getUser().getName();
        String content = sendMessageInputData.getContent();

        Instant instant = Instant.now();
        Timestamp cloudTimestamp = Timestamp.ofTimeSecondsAndNanos(
                instant.getEpochSecond(),
                instant.getNano()
        );

        Message message = messageFactory.createMessage(sender, content, cloudTimestamp);
        String groupID = sendMessageInputData.getUser().getGroup().getGroupID();
        System.out.println("[SMI] Recieve Message : " + message + " from : " + sender + " in " + groupID);
        groupDataAccessObject.updateMessage(groupID, message);
    }
}