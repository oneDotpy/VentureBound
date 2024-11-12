package use_case.group;

public class GroupOutputData {
    private final boolean success;
    private final String message;

    public GroupOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
