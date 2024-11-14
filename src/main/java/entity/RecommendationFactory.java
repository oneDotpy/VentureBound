package entity;

import com.google.cloud.firestore.GeoPoint;

import java.util.List;

/**
 * Factory for creating message.
 */
public interface RecommendationFactory {
    /**
     * Creates a group from database.
     * @param location the name of the place
     * @param description a brief description of place
     * @param coordinates the coordinates of the place
     * @param rating the rating of the place
     */
    public CommonRecommendation create(String location, String description, GeoPoint coordinates, int rating);
}
