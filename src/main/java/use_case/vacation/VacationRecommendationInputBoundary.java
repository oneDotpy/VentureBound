package use_case.vacation;

/**
 * Input boundary interface for generating vacation recommendations.
 */
public interface VacationRecommendationInputBoundary {
    /**
     * Generates vacation recommendations based on the chosen location and activities.
     * @param location The selected location for the vacation.
     * @param activities The activities the group enjoys.
     */
    void generateRecommendations(String location, String activities);
}
