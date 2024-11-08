package interface_adapter.group;

public class GroupState {
    private String groupName = "";
    private String createError;

    public String getGroupName() {
        return groupName;
    }

    public String getCreateError() {
        return createError;
    }


    public void setUsername(String groupName) {
        this.groupName = groupName;
    }

    public void setLoginError(String createError) {
        this.createError = createError;
    }
}
