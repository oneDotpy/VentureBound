package data_access;

import entity.*;
import use_case.join_group.JoinGroupGroupDataAccessInterface;
import use_case.send_message.SendMessageGroupDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class InMemoryGroupGroupDataAccessObject implements JoinGroupGroupDataAccessInterface,
        SendMessageGroupDataAccessInterface {
    GroupFactory groupFactory;

    public InMemoryGroupGroupDataAccessObject() {
        groupFactory = new CommonGroupFactory();
    }

    @Override
    public boolean existByID(String groupID) {
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
}
