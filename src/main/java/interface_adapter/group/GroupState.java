package interface_adapter.group;

public class GroupState {
    private String createMessage;
    private String joinMessage;
    private int groupId;
    private String groupName;

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

    public void setGroupName(String groupName) {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }
}
