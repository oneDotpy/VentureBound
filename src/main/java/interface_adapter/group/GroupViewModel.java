package interface_adapter.group;

public class GroupViewModel {
    private String createMessage;
    private String joinMessage;

    public String getCreateMessage() {
        return createMessage;
    }

    public void setCreateMessage(String createMessage) {
        this.createMessage = createMessage;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }
}
