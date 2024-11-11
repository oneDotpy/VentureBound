package entity;

import com.google.cloud.firestore.GeoPoint;
import java.util.List;

/**
 * The representation of a recommendation place from GPT API in out program.
 */
public interface Recommendation {
    /**
     * Returns the name of the place.
     * @return the question
     */
    String getLocation();

    /**
     * Returns the brief description of the place.
     * @return the question
     */
    String getDescription();

    /**
     * Returns the coordinates of the places.
     * @return the question
     */
    GeoPoint getCoordinates();

    /**
     * Returns the rating of this place from ChatGPT.
     * @return the question
     */
    int getRating();

    /**
     * Returns the vote of this place from users.
     * @return the question
     */
    List<Boolean> getVote();
}
