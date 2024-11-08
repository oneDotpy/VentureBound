package entity;

import java.util.List;
import java.util.ArrayList;

public class CommonGroup implements Group{
    private final String groupName;
    private final List<String> usernames;
    private final List<Response> responses;
    private final List<String> recommendations;
    private final List<String> chosen;
    private final List<Message> messages;

    public CommonGroup(String groupName, List<String> usernames, List<Response> responses, List<String> recommendations, List<String> chosen, List<Message> messages){
        this.groupName = groupName;
        this.usernames = usernames;
        this.responses = responses;
        this.recommendations = recommendations;
        this.chosen = chosen;
        this.messages = messages;
    }

    public CommonGroup(String groupName, List<String> usernames){
        this.groupName = groupName;
        this.usernames = usernames;
        this.responses = new ArrayList<>();
        this.recommendations = new ArrayList<>();
        this.chosen = new ArrayList<>();
        this.messages = new ArrayList<>();
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
    public List<String> getRecommendedLocations() {
        return recommendations;
    }

    @Override
    public List<String> getChosenLocations() {
        return chosen;
    }
}
