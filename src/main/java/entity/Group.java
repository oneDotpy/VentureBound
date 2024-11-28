package entity;

import java.util.List;

/**
 * The representation of a group in our program.
 */
public interface Group {

    /**
     * Returns the name of the group.
     * @return the name of the group.
     */
    String getGroupName();

    /**
     * Returns the users of the group.
     * @return the users of the group.
     */
    List<String> getUsernames();

    /**
     * Returns the responses of the group.
     * @return the responses of the group.
     */
    List<Response> getResponses();

    /**
     * Returns the recommended locations of the group.
     * @return the recommended locations of the group.
     */
    List<Recommendation> getRecommendedLocations();

    /**
     * Returns the chosen locations of the group.
     * @return the chosen locations of the group.
     */
    List<String> getChosenLocations();

    /**
     * Returns the messages in the group.
     * @return the messages in the group.
     */
    List<Message> getMessages();

    /**
     * Returns the groupID of the group
     * @return the groupID of this group
     */
    String getGroupID();

    /**
     * add Message to the group entity
     * @param message the new message
     */
    void addMessage(Message message);

    void setMembers(List<String> members);
}
