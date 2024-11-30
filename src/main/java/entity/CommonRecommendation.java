package entity;

import com.google.cloud.firestore.GeoPoint;

import java.util.List;

public class CommonRecommendation implements Recommendation {
    private final String location;
    private final String description;
    private final GeoPoint coordinates;
    private final int rating;
    private int voting = 0;
    private boolean chosen = false;

    public CommonRecommendation(String location, String description, GeoPoint coordinates, int rating) {
        this.location = location;
        this.description = description;
        this.coordinates = coordinates;
        this.rating = rating;
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

    public int getVoting() {return voting;}

    public void addVoting(int amount) {voting += amount;}

    public void setChosen(boolean result) {chosen = result;}
}
