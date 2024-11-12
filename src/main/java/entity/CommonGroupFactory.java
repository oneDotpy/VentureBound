package entity;

import java.util.List;

/**
 * Factory for creating CommonGroup objects.
 */
public class CommonGroupFactory implements GroupFactory {

    @Override
    public Group create(String groupName, List<String> usernames, List<Response> responses, List<String> recommendations, List<String> chosen, List<Message> messages) {
        return null;
    }

    @Override
    public Group create(String groupName, List<String> usernames) {
        return null;
    }
}