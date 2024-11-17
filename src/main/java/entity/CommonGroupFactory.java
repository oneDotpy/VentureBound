package entity;

import java.util.List;

/**
 * Factory for creating CommonGroup objects.
 */
public class CommonGroupFactory implements GroupFactory{

    @Override
    public Group create(String groupName, List<String> users, List<Response> responses, List<String> recommendations, List<String> chosen, List<Message> messages) {
        return new CommonGroup(groupName, users, responses, recommendations, chosen, messages);
    }

    @Override
    public Group create(String groupName, List<String> users) {
        return null;
    }
//
//    @Override
//    public Group create(String groupName, List<String> users) {
//        return null;
//    }
//
//    @Override
//    public Group create(String groupName, List<User> users){
//        return new CommonGroup(groupName, users);
//    }
}