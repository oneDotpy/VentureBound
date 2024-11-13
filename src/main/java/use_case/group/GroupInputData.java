package use_case.group;

import java.util.ArrayList;
import java.util.List;

public class GroupInputData {
    private final String groupName;
    private final String username;
    private final List<String> members;

    // Constructor for creating a new group with the current user as the only member
    public GroupInputData(String groupName, String username) {
        this.groupName = groupName;
        this.username = username;
        this.members = new ArrayList<>();
        this.members.add(username);
    }

    // Constructor for joining an existing group
    public GroupInputData(String groupName, String username, List<String> existingMembers) {
        this.groupName = groupName;
        this.username = username;
        this.members = new ArrayList<>(existingMembers);
        this.members.add(username);
    }

    public String getGroupName() {
        return groupName;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getMembers() {
        return members;
    }
}
