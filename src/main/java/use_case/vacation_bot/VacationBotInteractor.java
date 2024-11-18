package use_case.vacation_bot;

import java.io.IOException;
import java.util.*;

import app.OpenAIChatGPT;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.chat.ChatInputBoundary;

public class VacationBotInteractor implements VacationBotInputBoundary {
    private enum BotState { INACTIVE, AWAITING_LOCATION, AWAITING_HOBBIES, AWAITING_TEMP, GENERATING_RECOMMENDATIONS }
    private BotState botState = BotState.INACTIVE;

    private final VacationBotOutputBoundary presenter;
    private final ChatInputBoundary chatState;
    private final OpenAIChatGPT chatGPT;

    private final StringBuilder InputActivities = new StringBuilder();

    private final Map<String, String> locationResponses = new HashMap<>();
    private final Map<String, String> hobbyResponses = new HashMap<>();
    private final Map<String, String> tempResponses = new HashMap<>();

    public VacationBotInteractor(VacationBotOutputBoundary presenter, ChatInputBoundary chatState) {
        this.presenter = presenter;
        this.chatState = chatState;
        this.chatGPT = new OpenAIChatGPT();
    }

    @Override
    public void startBot() {
        botState = BotState.AWAITING_LOCATION;
        presenter.sendBotMessage("Bot", "🌍 Vacation Bot started! 🛫\nPlease answer the following questions or send /stop to stop bot.\n\nQuestion 1: Where would you like to go for a vacation?");
    }

    @Override
    public void stopBot() {
        botState = BotState.INACTIVE;
        presenter.sendBotMessage("Bot", "Vacation Bot has been stopped.");
    }

    @Override
    public boolean isBotActive() {
        return botState != BotState.INACTIVE;
    }

    private void processLocationResponses() {
        String chosenLocation = determineMostCommon(locationResponses.values());
        presenter.sendBotMessage("Bot", "\n📍 The chosen vacation location is: **" + chosenLocation + "**");

        botState = BotState.AWAITING_HOBBIES;
        presenter.sendBotMessage("Bot", "\nQuestion 2: What is your favorite hobbies? (Please choose one)");
    }

    private void processHobbyResponses() {
        for (String hobby : hobbyResponses.values()) {
            InputActivities.append(hobby).append(", ");
        }

        botState = BotState.AWAITING_TEMP;
        presenter.sendBotMessage("Bot", "\nQuestion 2: What kind of climate do you like? [Warm/Tropical, Cold/Snowy, Mild/Temperate] (Please choose one)");
    }

    private void processTempResponses() {
        StringBuilder temperatures = new StringBuilder();
        for (String hobby : tempResponses.values()) {
            temperatures.append(hobby).append(", ");
        }

        String chosenLocation = determineMostCommon(locationResponses.values());
        generateRecommendations(chosenLocation, InputActivities.toString(), temperatures.toString());
    }

    private void generateRecommendations(String location, String activities, String temperatures) {
        botState = BotState.GENERATING_RECOMMENDATIONS;
        try {
            String recommendationsJson = OpenAIChatGPT.getVacationRecommendations(activities, temperatures, location);
            displayRecommendations(recommendationsJson);
        } catch (Exception e) {
            presenter.sendBotMessage("Bot", "❌ Error generating recommendations: " + e.getMessage());
        } finally {
            botState = BotState.INACTIVE;
        }
    }

    @Override
    public void handleMessage(String username, String message) {
        if (botState == BotState.AWAITING_LOCATION) {
            locationResponses.put(username, message);
            presenter.sendBotMessage("Bot", username + " chose location: " + message);
            // Check if all members have responded
            if (locationResponses.size() == chatState.getMembers().size()) {
                processLocationResponses();
            }

        } else if (botState == BotState.AWAITING_HOBBIES) {
            hobbyResponses.put(username, message);
            presenter.sendBotMessage("Bot", username + " enjoys: " + message);

            // Check if all members have responded
            if (hobbyResponses.size() == chatState.getMembers().size()) {
                processHobbyResponses();
            }
        } else if (botState == BotState.AWAITING_TEMP) {
            tempResponses.put(username, message);
            presenter.sendBotMessage("Bot", username + " likes: " + message + "climate");

            // Check if all members have responded
            if (tempResponses.size() == chatState.getMembers().size()) {
                processTempResponses();
            }
        }
    }

    private void displayRecommendations(String recommendationsJson) {
        try {
            JSONObject responseObject = new JSONObject(recommendationsJson);
            JSONArray vacationSpots = null;
            if (responseObject.has("vacationSpots")) {
                vacationSpots = responseObject.getJSONArray("vacationSpots");
                System.out.println(vacationSpots);
            } else if (responseObject.has("vacation_spots")) {
                vacationSpots = responseObject.getJSONArray("vacation_spots");
            } else {
                System.err.println("No 'vacationSpots' or 'vacation_spots' key found in response");
            }

            if (vacationSpots != null) {
                StringBuilder formattedRecommendations = new StringBuilder();
                formattedRecommendations.append("🌟 Here are the top vacation spots for your group 🌟\n");

                for (int i = 0; i < vacationSpots.length(); i++) {
                    JSONObject spot = vacationSpots.getJSONObject(i);
                    String name = spot.getString("name");
                    String googleMapsLink = spot.getString("maps_link");

                    formattedRecommendations.append(i + 1)
                            .append(". **")
                            .append(name)
                            .append("**\n   📍 Google Maps: ")
                            .append(googleMapsLink)
                            .append("\n\n");
                }

                presenter.sendBotMessage("Bot", formattedRecommendations.toString());
            } else {
                presenter.sendBotMessage("Bot", "No recommendations found.");
            }
        } catch (Exception e) {
            presenter.sendBotMessage("Bot", "❌ Error displaying recommendations: " + e.getMessage());
        }
    }

    private String determineMostCommon(Collection<String> responses) {
        Map<String, Integer> countMap = new HashMap<>();
        for (String response : responses) {
            countMap.put(response, countMap.getOrDefault(response, 0) + 1);
        }
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(countMap.entrySet());
        entries.sort((a, b) -> b.getValue() - a.getValue());

        if (entries.size() > 1 && entries.get(0).getValue().equals(entries.get(1).getValue())) {
            return entries.get(new Random().nextInt(entries.size())).getKey();
        }
        return entries.get(0).getKey();
    }
}
