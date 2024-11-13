package use_case.vacation;

public class VacationRecommendationOutputData {
    private final String recommendationsJson;

    public VacationRecommendationOutputData(String recommendationsJson) {
        this.recommendationsJson = recommendationsJson;
    }

    public String getFormattedRecommendations() {
        // Parse the JSON and format it for display (you can adjust this as needed).
        return "🌟 Recommended Vacation Spots: " + recommendationsJson;
    }

    public String getRawJson() {
        return recommendationsJson;
    }
}
