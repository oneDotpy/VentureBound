package use_case.vacation_bot;

import java.util.*;

import app.OpenAIChatGPT;
import com.google.cloud.Timestamp;
import data_access.FirestoreGroupDataAccessObject;
import data_access.FirestoreUserDataAccessObject;
import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.send_message.SendMessageInputData;

/**
 * Interactor for managing the Vacation Bot functionality.
 * Handles vacation location and interest-based recommendations through user interactions.
 */
public class VacationBotInteractor implements VacationBotInputBoundary {
    private enum BotState { INACTIVE, AWAITING_LOCATION, AWAITING_HOBBIES, GENERATING_RECOMMENDATIONS }
    private boolean botCalled = false;
    private BotState botState = BotState.INACTIVE;

    private final OpenAIChatGPT chatGPT;
    private final FirestoreUserDataAccessObject firestoreUserDataAccessObject;
    private final FirestoreGroupDataAccessObject groupDataAccessObject;
    private final MessageFactory messageFactory;
    private User user;

    private String chosenLocation = "";

    private final Map<String, String> locationResponses = new HashMap<>();
    private final Map<String, String> hobbyResponses = new HashMap<>();
    private int threshold = 1;

    // New field to store hobby-related questions
    private final List<String> hobbyQuestions = Arrays.asList(
            "What kind of activities do you enjoy during vacations? (e.g., hiking, shopping, etc.)",
            "What is your favorite type of cuisine when traveling?",
            "Do you prefer urban adventures or rural escapes?",
            "What is your favorite mode of transportation during trips?",
            "What type of accommodation do you prefer? (e.g., hotel, camping, etc.)"
    );
    private int currentHobbyQuestionIndex = 0; // Tracks the current hobby question

    public VacationBotInteractor(FirestoreUserDataAccessObject firestoreUserDataAccessObject,
                                 FirestoreGroupDataAccessObject groupDataAccessObject,
                                 MessageFactory messageFactory) {

        this.chatGPT = new OpenAIChatGPT();
        this.messageFactory = messageFactory;
        this.firestoreUserDataAccessObject = firestoreUserDataAccessObject;
        this.groupDataAccessObject = groupDataAccessObject;
    }

    @Override
    public void startBot(String groupID, int groupSize) {
        if (botState.equals(BotState.INACTIVE)) {
            System.out.println("[VBI1] start");
            this.user = createBotUser(groupID);
            System.out.println("VacationBotInteractor: " + user.getGroupID());
            threshold = groupSize;
            botState = BotState.AWAITING_LOCATION;
            System.out.println("[VBI2.5] Successfully created bot and updated bot state");

            sendBotMessage("🌍 Vacation Bot started! 🛫\nPlease answer the following questions or send /stop to stop the bot.\n\n**Question 1:** Where would you like to go for a vacation?");
        } else {
            sendBotMessage("The Bot Already Started");
        }
    }

    @Override
    public void stopBot() {
        if (!botState.equals(BotState.INACTIVE)) {
            botState = BotState.INACTIVE;
            sendBotMessage("Vacation Bot has been stopped.");
        }
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
        System.out.println("[VBI] " + sender + ": " + content);
        Timestamp timestamp = firestoreUserDataAccessObject.getTimestamp(sender);

        System.out.println("[VBI3] sender: " + sender + " content: " + content);

        Message message = messageFactory.createMessage(sender, content, timestamp);
        System.out.println("[VBI4] Message created: " + message);
        String groupID = user.getGroupID();
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

    private User createBotUser(String groupID) {
        System.out.println("[VBI2] Create bot at " + groupID);
        UserFactory userFactory = new CommonUserFactory();
        return userFactory.create("Bot", null, "", null, groupID);
    }

    private void processLocationResponses() {
        if (locationResponses.size() >= threshold) {
            String chosenLocationInput = determineMostCommon(locationResponses.values());
            System.out.println("[VBI3] " + chosenLocationInput);
            chosenLocation = chosenLocationInput;
            sendBotMessage("\n📍 The chosen vacation location is: **" + chosenLocation + "**");

            botState = BotState.AWAITING_HOBBIES;
            askNextHobbyQuestion();
        }
    }

    private void processHobbyResponses() {
        if (hobbyResponses.size() >= threshold) {
            if (currentHobbyQuestionIndex < 3) {
                askNextHobbyQuestion();
            } else {
                finalizeHobbyResponses();
            }
        }
    }

    private void askNextHobbyQuestion() {
        if (currentHobbyQuestionIndex < hobbyQuestions.size()) {
            sendBotMessage("\n**Question " + (currentHobbyQuestionIndex + 2) + ":** " + hobbyQuestions.get(currentHobbyQuestionIndex));
            currentHobbyQuestionIndex++;
        }
    }

    private void finalizeHobbyResponses() {
        sendBotMessage("Generating your perfect holiday destination...");
        botCalled = true;
        StringBuilder activities = new StringBuilder();
        for (String hobby : hobbyResponses.values()) {
            activities.append(hobby).append(", ");
        }
        generateRecommendations(activities.toString(), chosenLocation);
        System.out.println(hobbyResponses);
        hobbyResponses.clear();
        locationResponses.clear();
        currentHobbyQuestionIndex = 0; // Reset for future sessions
    }

    private void generateRecommendations(String activities, String location) {
        System.out.println("[VBI] Generate recommendation before bot state");
        botState = BotState.GENERATING_RECOMMENDATIONS;
        System.out.println("[VBI] Generate recommendation after bot state");
        try {
            System.out.println("[VBI] Trying to generate recommendation");
            String recommendationsJson = OpenAIChatGPT.getVacationRecommendations(activities, location);
            displayRecommendations(recommendationsJson);
        } catch (Exception e) {
            sendBotMessage("❌ Error generating recommendations: " + e.getMessage());
        } finally {
            botState = BotState.INACTIVE;
        }
    }

    @Override
    public void handleMessage(String username, String message, int groupSize, String groupID) {
        if (botState == BotState.AWAITING_LOCATION) {
            locationResponses.put(username, message);
            sendBotMessage(username + " chose: " + message);
            processLocationResponses();
        } else if (botState == BotState.AWAITING_HOBBIES) {
            hobbyResponses.put(username, hobbyResponses.getOrDefault(username, "") + message + "; ");
            sendBotMessage(username + " responded: " + message);
            processHobbyResponses();
        }
    }

    @Override
    public void setThreshold(int threshold) {
        this.threshold = threshold;
        if (botState == BotState.AWAITING_LOCATION) {
            processLocationResponses();
        } else if (botState == BotState.AWAITING_HOBBIES) {
            processHobbyResponses();
        }
    }

    public void removeResponse(List<String> members) {
        if (botState == BotState.AWAITING_LOCATION) {
            removeMissingMemberResponse(members, locationResponses);
        } else if (botState == BotState.AWAITING_HOBBIES) {
            removeMissingMemberResponse(members, hobbyResponses);
        }
    }

    private void removeMissingMemberResponse(List<String> members, Map<String, String> responses) {
        String username;
        for (String key : responses.keySet()) {
            boolean foundMissingMember = true;
            for (String member : members) {
                if (key.equals(member)) {
                    foundMissingMember = false;
                    break;
                }
            }
            if (foundMissingMember) {
                username = key;
                System.out.println("removed response from " + username + " with content: " + responses.remove(username));
                return;
            }
        }
    }

    private void displayRecommendations(String recommendationsJson) {
        try {
            System.out.println("[VBI] Trying to display recommendation");
            JSONObject responseObject = new JSONObject(recommendationsJson);
            JSONArray vacationSpots = null;
            if (responseObject.has("vacationSpots")) {
                vacationSpots = responseObject.getJSONArray("vacationSpots");
            } else if (responseObject.has("vacation_spots")) {
                vacationSpots = responseObject.getJSONArray("vacation_spots");
            }else if (responseObject.has("recommended_spots")) {
                vacationSpots = responseObject.getJSONArray("recommended_spots");
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
        System.out.println("[VBI] Received responses: " + responses);
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
