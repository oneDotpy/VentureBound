package use_case.vacation;

import app.OpenAIChatGPT;

/**
 * Interactor for generating vacation recommendations using OpenAI API.
 */
public class VacationRecommendationInteractor implements VacationRecommendationInputBoundary {
    private final OpenAIChatGPT openAIChatGPT;
    private final VacationRecommendationOutputBoundary presenter;

    public VacationRecommendationInteractor(VacationRecommendationOutputBoundary presenter) {
        this.openAIChatGPT = new OpenAIChatGPT();
        this.presenter = presenter;
    }

    @Override
    public void generateRecommendations(String location, String activities) {
        try {
            String recommendationsJson = openAIChatGPT.getVacationRecommendations(activities, location);
            VacationRecommendationOutputData outputData = new VacationRecommendationOutputData(recommendationsJson);
            presenter.presentRecommendations(outputData);
        } catch (Exception e) {
            presenter.presentError("❌ Error generating recommendations: " + e.getMessage());
        }
    }
}
