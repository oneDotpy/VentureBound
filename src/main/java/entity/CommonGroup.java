package entity;

import java.util.ArrayList;
import java.util.List;

public class CommonGroup implements Group{
    private final String groupName;
    private List<String> usernames;
    private final List<Response> responses;
    private final List<Recommendation> recommendations;
    private final List<String> chosen;
    private final List<Message> messages;
    private String groupID;


    public CommonGroup(String groupName, List<String> usernames, List<Response> responses, List<Recommendation> recommendations, List<String> chosen, List<Message> messages){
        this.groupName = groupName;
        this.usernames = usernames;
        this.responses = responses;
        this.recommendations = recommendations;
        this.chosen = chosen;
        this.messages = messages;
        this.groupID = "";
    }


    public CommonGroup(String groupName, List<String> usernames){
        this.groupName = groupName;
        this.usernames = usernames;
        this.responses = new ArrayList<>();
        this.recommendations = new ArrayList<>();
        this.chosen = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.groupID = "";
    }

    public CommonGroup(String groupName, List<String> usernames, String groupID) {
        this.groupName = groupName;
        this.usernames = usernames;
        this.responses = new ArrayList<>();
        this.recommendations = new ArrayList<>();
        this.chosen = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.groupID = groupID;
    }


    public CommonGroup(String groupName, List<String> usernames, List<Response> responses, List<Recommendation> recommendations, List<String> chosen, List<Message> messages, String groupID){
        this.groupName = groupName;
        this.usernames = usernames;
        this.responses = responses;
        this.recommendations = recommendations;
        this.chosen = chosen;
        this.messages = messages;
        this.groupID = groupID;
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public List<String> getUsernames() {
        return usernames;
    }

    @Override
    public List<Response> getResponses() {
        return responses;
    }

    @Override
    public List<Recommendation> getRecommendedLocations() {
        return recommendations;
    }

    @Override
    public List<String> getChosenLocations() {
        return chosen;
    }

    @Override
    public List<Message> getMessages() { return messages; }

    @Override
    public String getGroupID() {
        return groupID;
    }

    public void addMessage(Message message) {this.messages.add(message);}

    public void setMembers(List<String> members) {
        usernames = new ArrayList<>(members);
    }
}
