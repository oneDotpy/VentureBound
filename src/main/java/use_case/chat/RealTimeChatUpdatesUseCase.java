package use_case.chat;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.ListenerRegistration;
import data_access.FirestoreGroupGroupDataAccessObject;

import java.util.List;
import java.util.Map;

public class RealTimeChatUpdatesUseCase {

    private final FirestoreGroupGroupDataAccessObject groupDAO;

    public RealTimeChatUpdatesUseCase(FirestoreGroupGroupDataAccessObject groupDAO) {
        this.groupDAO = groupDAO;
    }

    public interface GroupMemberUpdateListener {
        void onGroupMembersUpdated(List<String> members);
        void onError(Exception e);
    }

    public interface MessageUpdateListener {
        void onMessagesUpdated(Map<String, String> messages, Timestamp timestamp);
        void onError(Exception e);
    }

    public ListenerRegistration listenForGroupMembers(String groupID, GroupMemberUpdateListener listener) {
        return groupDAO.setGroupMemberListener(groupID, listener);
    }

    public ListenerRegistration listenForMessages(String groupID, MessageUpdateListener listener) {
        return groupDAO.setMessageListener(groupID, listener);
    }
}
