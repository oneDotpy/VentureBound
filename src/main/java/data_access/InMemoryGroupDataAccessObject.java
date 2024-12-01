package data_access;

import entity.*;
import use_case.create_group.CreateGroupGroupDataAccessInterface;
import use_case.join_group.JoinGroupGroupDataAccessInterface;
import use_case.leave_group.LeaveGroupGroupDataAccessInterface;
import use_case.login.LoginGroupDataAccessInterface;
import use_case.send_message.SendMessageGroupDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class InMemoryGroupDataAccessObject implements
        LoginGroupDataAccessInterface,
        JoinGroupGroupDataAccessInterface,
        SendMessageGroupDataAccessInterface,
        CreateGroupGroupDataAccessInterface,
        LeaveGroupGroupDataAccessInterface {
    GroupFactory groupFactory;
    List<Group> groups = new ArrayList<>();

    public InMemoryGroupDataAccessObject() {
        groupFactory = new CommonGroupFactory();
    }

    @Override
    public boolean existByID(String groupID) {
        for (Group group : groups) {
            if (group.getGroupID().equals(groupID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void join(String groupID, String username) {

    }

    @Override
    public Group get(String groupID) {
        String groupName = "Group";
        List<String> usernames = new ArrayList<>();
        List< Response > responses = new ArrayList<>();
        List< Recommendation > recommendations = new ArrayList<>();
        List<String> chosen = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        return groupFactory.create(groupName, usernames, responses, recommendations, chosen, messages, groupID);
    }

    @Override
    public void updateMessage(String groupID, Message message) {

    }

    @Override
    public String save(Group group) {
        String groupID = group.getGroupID();
        if ("".equals(groupID)) {
            groupID = "CreateGroupID";
            group = groupFactory.create(group.getGroupName(), group.getUsernames(), groupID);
        }
        groups.add(group);
        return groupID;
    }

    @Override
    public void removeMember(String groupID, String username) {

    }

    @Override
    public void detachListener() {

    }
}
