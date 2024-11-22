package use_case.chat;

import data_access.FirestoreGroupDataAccessObject;
import entity.Message;
import java.util.List;
import java.util.Map;

public class RealTimeChatUpdatesUseCase {

    private final FirestoreGroupDataAccessObject groupDAO;

    public RealTimeChatUpdatesUseCase(FirestoreGroupDataAccessObject groupDAO) {
        this.groupDAO = groupDAO;
    }

    public interface GroupMemberUpdateListener {
        void onGroupMembersUpdated(List<String> members);
        void onError(Exception e);
    }

    public interface MessageUpdateListener {
        void onMessagesUpdated(Map<String, String> messages);
        void onError(Exception e);
    }

    public void listenForGroupMembers(String groupID, GroupMemberUpdateListener listener) {
        groupDAO.setGroupMemberListener(groupID, listener);
    }

    public void listenForMessages(String groupID, MessageUpdateListener listener) {
        groupDAO.setMessageListener(groupID, listener);
    }
}
