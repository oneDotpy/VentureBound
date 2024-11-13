package use_case.vacation;

public interface VacationRecommendationOutputBoundary {
    /**
     * Method to present the generated vacation recommendations.
     * @param outputData The data containing the vacation recommendations.
     */
    void presentRecommendations(VacationRecommendationOutputData outputData);

    /**
     * Method to present an error if recommendations generation fails.
     * @param errorMessage The error message to be presented.
     */
    void presentError(String errorMessage);
}
