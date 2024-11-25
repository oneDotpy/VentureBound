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

    /**
     * Make a new group that already have ID. Most likely used for user that have joined, or group that already in database.
     * @param groupName the name of the new group
     * @param usernames the list of members
     * @param responses the list of responses in the group
     * @param recommendations the list of recommended locations
     * @param chosen the list of chosen locations
     * @param messages the list of messages
     * @param groupID the groupID
     * @return the new group that are ready to use
     */
    Group create(String groupName, List<String> usernames, List<Response> responses, List<Recommendation> recommendations, List<String> chosen, List<Message> messages, String groupID);

    /**
     * Make a new group that already ID. Most likely used for new group.
     * @param groupName the name of the new group
     * @param usernames the list of members
     * @param groupID the groupID
     * @return the new group that are ready to use
     */
    Group create(String groupName, List<String> usernames, String groupID);
}
