package use_case.chat;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.ListenerRegistration;
import data_access.FirestoreGroupDataAccessObject;

import java.util.List;
import java.util.Map;

/**
 * Use case for handling real-time chat updates, including group members and messages.
 */
public class RealTimeChatUpdatesUseCase {

    private final FirestoreGroupDataAccessObject groupDAO;

    /**
     * Constructs a new instance of RealTimeChatUpdatesUseCase.
     *
     * @param groupDAO Data access object for interacting with group data in Firestore.
     */
    public RealTimeChatUpdatesUseCase(FirestoreGroupDataAccessObject groupDAO) {
        this.groupDAO = groupDAO;
    }

    /**
     * Listener interface for group member updates.
     */
    public interface GroupMemberUpdateListener {
        /**
         * Triggered when the group member list is updated.
         *
         * @param members A list of updated group members.
         */
        void onGroupMembersUpdated(List<String> members);

        /**
         * Triggered when an error occurs while updating group members.
         *
         * @param e The exception encountered.
         */
        void onError(Exception e);
    }

    /**
     * Listener interface for message updates.
     */
    public interface MessageUpdateListener {
        /**
         * Triggered when the messages in the chat are updated.
         *
         * @param messages   A map of message details, including sender and content.
         * @param timestamp  The timestamp of the latest message update.
         */
        void onMessagesUpdated(Map<String, String> messages, Timestamp timestamp);

        /**
         * Triggered when an error occurs while updating messages.
         *
         * @param e The exception encountered.
         */
        void onError(Exception e);
    }

    /**
     * Sets a listener for group member updates.
     *
     * @param groupID  The ID of the group being monitored.
     * @param listener The listener for member updates.
     * @return A listener registration object for managing the listener.
     */
    public ListenerRegistration listenForGroupMembers(String groupID, GroupMemberUpdateListener listener) {
        return groupDAO.setGroupMemberListener(groupID, listener);
    }

    /**
     * Sets a listener for message updates.
     *
     * @param groupID  The ID of the group being monitored.
     * @param listener The listener for message updates.
     * @return A listener registration object for managing the listener.
     */
    public ListenerRegistration listenForMessages(String groupID, MessageUpdateListener listener) {
        return groupDAO.setMessageListener(groupID, listener);
    }
}
