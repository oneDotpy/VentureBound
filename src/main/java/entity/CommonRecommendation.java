package entity;

import com.google.cloud.firestore.GeoPoint;

import java.util.List;

public class CommonRecommendation implements Recommendation {
    private final String location;
    private final String description;
    private final GeoPoint coordinates;
    private final int rating;
    private final List<Boolean> votes;

    public CommonRecommendation(String location, String description, GeoPoint coordinates, int rating, List<Boolean> votes) {
        this.location = location;
        this.description = description;
        this.coordinates = coordinates;
        this.rating = rating;
        this.votes = votes;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public GeoPoint getCoordinates() {
        return coordinates;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public List<Boolean> getVote() {
        return votes;
    }
}
