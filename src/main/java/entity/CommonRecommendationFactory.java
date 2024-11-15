package entity;

import com.google.cloud.firestore.GeoPoint;
import okhttp3.Cookie;

import java.util.List;

public class CommonRecommendationFactory implements RecommendationFactory{

    @Override
    public CommonRecommendation create(String location, String description, GeoPoint coordinates, int rating) {
        return new CommonRecommendation(location, description, coordinates, rating);
    }
}
