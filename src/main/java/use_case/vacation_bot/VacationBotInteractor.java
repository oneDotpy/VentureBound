package use_case.vacation_bot;

import java.io.IOException;
import java.util.*;

import app.OpenAIChatGPT;
import com.google.cloud.Timestamp;
import data_access.FirestoreGroupDataAccessObject;
import data_access.FirestoreUserDataAccessObject;
import entity.*;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.chat.ChatInputBoundary;
import use_case.send_message.SendMessageInputData;

public class VacationBotInteractor implements VacationBotInputBoundary {
    private enum BotState { INACTIVE, AWAITING_LOCATION, AWAITING_HOBBIES, GENERATING_RECOMMENDATIONS }
    private boolean botCalled = false;
    private BotState botState = BotState.INACTIVE;

    private final VacationBotOutputBoundary presenter;
    private final ChatViewModel chatViewModel;
    private final OpenAIChatGPT chatGPT;
    private final FirestoreUserDataAccessObject firestoreUserDataAccessObject;
    private final FirestoreGroupDataAccessObject groupDataAccessObject;
    private final MessageFactory messageFactory;
    private User user;

    private final Map<String, String> locationResponses = new HashMap<>();
    private final Map<String, String> hobbyResponses = new HashMap<>();

    public VacationBotInteractor(VacationBotOutputBoundary presenter,
                                 ChatViewModel chatViewModel,
                                 FirestoreUserDataAccessObject firestoreUserDataAccessObject,
                                 FirestoreGroupDataAccessObject groupDataAccessObject,
                                 MessageFactory messageFactory) {
        this.presenter = presenter;
        this.chatViewModel = chatViewModel;
        this.chatGPT = new OpenAIChatGPT();
        this.messageFactory = messageFactory;
        this.firestoreUserDataAccessObject = firestoreUserDataAccessObject;
        this.groupDataAccessObject = groupDataAccessObject;
    }

    @Override
    public void startBot() {
        System.out.println("[VBI2] start");
        this.user = createBotUser();
        botState = BotState.AWAITING_LOCATION;

        sendBotMessage("🌍 Vacation Bot started! 🛫\nPlease answer the following questions or send /stop to stop bot.\n\n**Question 1:** Where would you like to go for a vacation?");
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

    public boolean isBotCalled() {
        return botCalled;
    }

    private void sendBotMessage(String botMessage) {
        SendMessageInputData inputData = new SendMessageInputData(user, botMessage);
        System.out.println(inputData.getContent() + inputData.getUser().getName());
        String sender = inputData.getUser().getName();
        String content = inputData.getContent();
        Timestamp timestamp = firestoreUserDataAccessObject.getTimestamp(sender);

        System.out.println("[VBI3] sender: " + sender + " content: " + content);

        Message message = messageFactory.createMessage(sender, content, timestamp);
        System.out.println("[VBI4] Message created: " + message);
        String groupID = chatViewModel.getState().getCurrentUser().getGroup().getGroupID();
        System.out.println("[VBI4] GroupID: " + groupID);

        try {
            System.out.println("[VBI5] Attempting to update message in Firestore...");
            groupDataAccessObject.updateMessage(groupID, message);
            System.out.println("[VBI5] Successfully updated message in Firestore.");
        } catch (Exception e) {
            System.err.println("[VBI5] Error while updating Firestore: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private User createBotUser() {
        System.out.println("[VBI1] Create bot at " + chatViewModel.getState().getCurrentUser().getGroupID());
        UserFactory userFactory = new CommonUserFactory();
        return userFactory.create("Bot", null, chatViewModel.getState().getUser().getGroupID());
    }

    private void processLocationResponses() {
        String chosenLocation = determineMostCommon(locationResponses.values());
        sendBotMessage("\n📍 The chosen vacation location is: **" + chosenLocation + "**");

        botState = BotState.AWAITING_HOBBIES;
        sendBotMessage("\n**Question 2:** What is your favorite hobbies? (Please choose one)");

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
            String recommendationsJson = OpenAIChatGPT.getVacationRecommendations(activities, location);
            displayRecommendations(recommendationsJson);
        } catch (Exception e) {
            sendBotMessage("❌ Error generating recommendations: " + e.getMessage());
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
            if (locationResponses.size() == chatViewModel.getState().getMembers().size()) {
                processLocationResponses();
            }

        } else if (botState == BotState.AWAITING_HOBBIES) {
            hobbyResponses.put(username, message);
            presenter.sendBotMessage("Bot", username + " enjoys: " + message);

            // Check if all members have responded
            if (hobbyResponses.size() == chatViewModel.getState().getMembers().size()) {
                botCalled = true;
                sendBotMessage("Generating the your perfect holiday destination....");
                if (botCalled) {processHobbyResponses();}
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

                sendBotMessage(formattedRecommendations.toString());
            } else {
                sendBotMessage("No recommendations found.");
            }
        } catch (Exception e) {
            sendBotMessage("❌ Error displaying recommendations: " + e.getMessage());
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
