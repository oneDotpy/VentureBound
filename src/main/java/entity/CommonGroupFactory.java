package entity;

import java.util.List;

/**
 * Factory for creating CommonGroup objects.
 */
public class CommonGroupFactory implements GroupFactory{

    @Override
    public Group create(String groupName, List<String> usernames, List<Response> responses, List<Recommendation> recommendations, List<String> chosen, List<Message> messages) {
        return new CommonGroup(groupName, usernames, responses, recommendations, chosen, messages);
    }

    @Override
    public Group create(String groupName, List<String> usernames){
        return new CommonGroup(groupName, usernames);
    }
}
