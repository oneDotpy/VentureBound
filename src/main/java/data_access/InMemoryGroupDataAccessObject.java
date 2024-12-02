package data_access;

import entity.*;
import use_case.create_group.CreateGroupGroupDataAccessInterface;
import use_case.join_group.JoinGroupGroupDataAccessInterface;
import use_case.leave_group.LeaveGroupGroupDataAccessInterface;
import use_case.login.LoginGroupDataAccessInterface;
import use_case.send_message.SendMessageGroupDataAccessInterface;
import use_case.vacation_bot.VacationBotGroupDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class InMemoryGroupDataAccessObject implements
        LoginGroupDataAccessInterface,
        JoinGroupGroupDataAccessInterface,
        SendMessageGroupDataAccessInterface,
        CreateGroupGroupDataAccessInterface,
        LeaveGroupGroupDataAccessInterface, VacationBotGroupDataAccessInterface {
    GroupFactory groupFactory;
    List<Group> groups = new ArrayList<>();

    public InMemoryGroupDataAccessObject() {
        groupFactory = new CommonGroupFactory();

        String groupName = "Group";
        List<String> usernames = new ArrayList<>();
        List< Response > responses = new ArrayList<>();
        List< Recommendation > recommendations = new ArrayList<>();
        List<String> chosen = new ArrayList<>();
        List<Message> messages = new ArrayList<>();

        save(groupFactory.create(groupName, usernames, responses, recommendations, chosen, messages, "groupID"));
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
        for (Group group : groups) {
            if (group.getGroupID().equals(groupID)) {
                List<String> members = group.getUsernames();
                members.add(username);
                group.setMembers(members);
                return;
            }
        }
    }

    @Override
    public Group get(String groupID) {
        for (Group group : groups) {
            if (group.getGroupID().equals(groupID)) {
                return group;
            }
        }
        return null;
    }

    @Override
    public void updateMessage(String groupID, Message message) {
        for (Group group: groups) {
            if (group.getGroupID().equals(groupID)) {
                group.getMessages().add(message);
                return;
            }
        }
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
        for (Group group: groups) {
            if (group.getGroupID().equals(groupID)) {
                group.getUsernames().remove(username);
                return;
            }
        }
    }

    @Override
    public void detachListener() {
        return;
    }
}
