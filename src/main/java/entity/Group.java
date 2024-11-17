
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
     * Returns the responses of the group.
     * @return the responses of the group.
     */
    List<Response> getResponses();

    /**
     * Returns the recommended location of the group.
     * @return the recommended location of the group.
     */
    List<String> getRecommendedLocations();

    /**
     * Returns the chosen location of the group.
     * @return the chosen location of the group.
     */
    List<String> getChosenLocations();

    /**
     * Return the usernames of the group
     * @return list of usernames
     */
    List<String> getUsernames();
}
