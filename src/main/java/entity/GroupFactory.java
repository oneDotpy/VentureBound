package entity;

import java.util.List;

/**
 * Factory for creating users.
 */
public interface GroupFactory {
    /**
     * Creates a group from database.
     * @param groupName the name of the group
     * @param usernames the list of the members
     * @param responses the list of responses in the group
     * @param recommendations the list of recommendations_locations
     * @param chosen the list of chosen_locations
     * @param messages the list of messages in the group
     * @return the group from database
     */
    Group create(String groupName, List<String> usernames, List<Response> responses, List<Recommendation> recommendations, List<String> chosen, List<Message> messages);

    /**
     * Creates a new group not from database
     * @param groupName the name of the new group
     * @param usernames the list of members
     * @return the new group
     */
    Group create(String groupName, List<String> usernames);
}
