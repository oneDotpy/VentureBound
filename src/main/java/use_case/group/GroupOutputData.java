package use_case.group;

import java.util.List;

public class GroupOutputData {
    private final boolean success;
    private final String message;
    private final List<String> members;

    public GroupOutputData(boolean success, String message, List<String> members) {
        this.success = success;
        this.message = message;
        this.members = members;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getMembers() {
        return members;
    }
}
