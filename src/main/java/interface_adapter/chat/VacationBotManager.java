package interface_adapter.chat;

import app.OpenAIChatGPT;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class VacationBotManager {
    private enum BotState {
        INACTIVE, AWAITING_LOCATION, AWAITING_HOBBIES, GENERATING_RECOMMENDATIONS
    }

    private BotState botState = BotState.INACTIVE;
    private final ChatController chatController;
    private final ChatViewModel chatViewModel;
    private final OpenAIChatGPT openAIChatGPT;

    private final Map<String, String> locationResponses = new HashMap<>();
    private final Map<String, String> hobbyResponses = new HashMap<>();

    public VacationBotManager(ChatController chatController, ChatViewModel chatViewModel) {
        this.chatController = chatController;
        this.chatViewModel = chatViewModel;
        this.openAIChatGPT = new OpenAIChatGPT();
    }

    public boolean isBotActive() {
        return botState != BotState.INACTIVE;
    }

    public void startBot() {
        botState = BotState.AWAITING_LOCATION;
        chatController.sendBotMessage("Bot", "🌍 Vacation Bot started! 🛫\nPlease answer the following questions or send /stop to stop bot.\n\n**Question 1:** Where would you like to go for a vacation?");
    }

    public void endBot() {
        botState = BotState.INACTIVE;
        chatController.sendBotMessage("Bot", "Vacation Bot has been stopped.");
    }

    public void handleMessage(String username, String message) {
        if (botState == BotState.INACTIVE) return;

        List<String> members = chatViewModel.getMembers();

        if (botState == BotState.AWAITING_LOCATION) {
            // Store the user's location response
            locationResponses.put(username, message);
            chatController.sendBotMessage("Bot", username + " has chosen: " + message);

            // Check if all members have responded
            if (locationResponses.size() == members.size()) {
                processLocationResponses();
            }
        } else if (botState == BotState.AWAITING_HOBBIES) {
            // Store the user's hobby response
            hobbyResponses.put(username, message);
            chatController.sendBotMessage("Bot", username + " enjoys: " + message);

            // Check if all members have responded
            if (hobbyResponses.size() == members.size()) {
                processHobbyResponses();
            }
        }
    }

    private void processLocationResponses() {
        String chosenLocation = determineMostCommon(locationResponses.values());
        chatController.sendBotMessage("Bot", "\n📍 The chosen vacation location is: **" + chosenLocation + "**");

        botState = BotState.AWAITING_HOBBIES;
        chatController.sendBotMessage("Bot", "\n**Question 2:** What is your favorite hobbies? (Please choose one)");
    }

    private void processHobbyResponses() {
        StringBuilder activities = new StringBuilder();
        for (String hobby : hobbyResponses.values()) {
            activities.append(hobby).append(", ");
        }

        String chosenLocation = determineMostCommon(locationResponses.values());
        generateRecommendations(chosenLocation, activities.toString());
    }

    private void generateRecommendations(String location, String activities) {
        botState = BotState.GENERATING_RECOMMENDATIONS;
        try {
            String recommendationsJson = openAIChatGPT.getVacationRecommendations(activities, location);
            displayRecommendations(recommendationsJson);
        } catch (Exception e) {
            chatController.sendBotMessage("Bot", "❌ Error generating recommendations: " + e.getMessage());
        } finally {
            botState = BotState.INACTIVE;
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
                formattedRecommendations.append("🌟 **Here are the top vacation spots for your group** 🌟\n");

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

                chatController.sendBotMessage("Bot", formattedRecommendations.toString());
            } else {
                chatController.sendBotMessage("Bot", "No recommendations found.");
            }
        } catch (Exception e) {
            chatController.sendBotMessage("Bot", "❌ Error displaying recommendations: " + e.getMessage());
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
