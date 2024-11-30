package interface_adapter.chat;

import com.google.cloud.Timestamp;

import java.util.List;

public interface ChatControllerInterface {
    void handleMessage(String sender, String content, Timestamp timestamp, String currentUser, int groupSize, String groupID);

    void addMembers(List<String> members);

}
