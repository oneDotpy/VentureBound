package interface_adapter.group;

import java.util.ArrayList;
import java.util.List;

public class GroupState {
    private String createMessage;
    private String joinMessage;
    private int groupId;
    private String groupName;
    private List<String> members = new ArrayList<>();

    // Getters and Setters
    public String getCreateMessage() {
        return createMessage;
    }

    public void setCreateMessage(String message) {
        this.createMessage = message;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(String message) {
        this.joinMessage = message;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public List<String> getMembers() {
        System.out.println("[GroupState] Retrieving members: " + members);
        return new ArrayList<>(members);
    }

    public void setMembers(List<String> members) {
        this.members = new ArrayList<>(members);
        System.out.println("[GroupState] Members set: " + this.members);
    }

    public void addMember(String member) {
        if (!members.contains(member)) {
            members.add(member);
            System.out.println("[GroupState] Member added: " + member);
        }
    }
}
