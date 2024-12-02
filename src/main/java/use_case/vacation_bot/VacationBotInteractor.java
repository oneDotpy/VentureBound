package use_case.vacation_bot;

import java.util.*;

import app.OpenAIChatGPT;
import com.google.cloud.Timestamp;
import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.send_message.SendMessageInputData;

/**
 * Interactor for managing the Vacation Bot functionality.
 */
public class VacationBotInteractor implements VacationBotInputBoundary {
    private final OpenAIChatGPT chatGPT;

    /**
     * Enum to represent the current state of the bot.
     */
    private enum BotState { INACTIVE, AWAITING_LOCATION, AWAITING_HOBBIES, GENERATING_RECOMMENDATIONS }
    private BotState botState = BotState.INACTIVE;

    private final VacationBotUserDataAccessInterface firestoreUserDataAccessObject;
    private final VacationBotGroupDataAccessInterface groupDataAccessObject;
    private final MessageFactory messageFactory;
    private User user;

    private String chosenLocation = "";

    private final Map<String, String> locationResponses = new HashMap<>();
    private final Map<String, String> hobbyResponses = new HashMap<>();
    private StringBuilder activities = new StringBuilder();
    private int threshold = 1;

    // List of hobby-related questions
    private final List<String> hobbyQuestions = Arrays.asList(
            "What kind of activities do you enjoy during vacations? (e.g., hiking, shopping, etc.)",
            "What is your favorite type of cuisine when traveling?",
            "Do you prefer urban adventures or rural escapes?",
            "What is your favorite mode of transportation during trips?",
            "What type of accommodation do you prefer? (e.g., hotel, camping, etc.)"
    );
    private int currentHobbyQuestionIndex = 0; // Tracks the current hobby question

    /**
     * Constructor to initialize the VacationBotInteractor.
     *
     * @param firestoreUserDataAccessObject Firestore DAO for user data.
     * @param groupDataAccessObject         Firestore DAO for group data.
     * @param messageFactory                Factory for creating messages.
     */
    public VacationBotInteractor(VacationBotUserDataAccessInterface firestoreUserDataAccessObject,
                                 VacationBotGroupDataAccessInterface groupDataAccessObject,
                                 MessageFactory messageFactory) {
        this.chatGPT = new OpenAIChatGPT();
        this.messageFactory = messageFactory;
        this.firestoreUserDataAccessObject = firestoreUserDataAccessObject;
        this.groupDataAccessObject = groupDataAccessObject;
    }

    /**
     * Starts the bot and prompts the group for the first question (vacation location).
     *
     * @param groupID   ID of the group interacting with the bot.
     * @param groupSize Number of members in the group.
     */
    @Override
    public void startBot(String groupID, int groupSize) {
        if (botState.equals(BotState.INACTIVE)) {
            createBotUser(groupID);
            threshold = groupSize;
            botState = BotState.AWAITING_LOCATION;

            sendBotMessage("🌍 Vacation Bot started! 🛫\nPlease answer the following questions or send /stop to stop the bot.\n\n**Question 1:** Where would you like to go for a vacation?");
        } else {
            sendBotMessage("The Bot Already Started");
        }
    }

    /**
     * Stops the bot and resets its state to inactive.
     */
    @Override
    public void stopBot() {
        if (!botState.equals(BotState.INACTIVE)) {
            botState = BotState.INACTIVE;
            sendBotMessage("Vacation Bot has been stopped.");
        }
    }

    /**
     * Checks if the bot is currently active.
     *
     * @return True if the bot is active, false otherwise.
     */
    @Override
    public boolean isBotActive() {
        return botState != BotState.INACTIVE;
    }

    /**
     * Sends a message from the bot to the group.
     *
     * @param botMessage The content of the bot's message.
     */
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

    /**
     * Creates a bot user for interacting with the group.
     *
     * @param groupID ID of the group interacting with the bot.
     */
    public void createBotUser(String groupID) {
        System.out.println("[VBI2] Create bot at " + groupID);
        UserFactory userFactory = new CommonUserFactory();
        this.user = userFactory.create("Bot", null,"", null, groupID);
    }


    /**
     * Processes location responses and determines the most common response.
     * Updates the bot's state to await hobbies if the threshold is met.
     */
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

    /**
     * Processes hobby responses and moves to the next question or finalizes the responses.
     */
    private void processHobbyResponses() {
        if (hobbyResponses.size() >= threshold) {
            if (currentHobbyQuestionIndex < 3) {
                askNextHobbyQuestion();
            } else {
                finalizeHobbyResponses();
            }
        }
    }

    /**
     * Sends the next hobby-related question to the group.
     */
    private void askNextHobbyQuestion() {
        if (currentHobbyQuestionIndex < hobbyQuestions.size()) {
            sendBotMessage("\n**Question " + (currentHobbyQuestionIndex + 2) + ":** " + hobbyQuestions.get(currentHobbyQuestionIndex));
            for (String hobby : hobbyResponses.values()) {
                this.activities.append(hobby).append(", ");
            }
            currentHobbyQuestionIndex++;
            hobbyResponses.clear();
        }
    }

    /**
     * Finalizes the hobby responses and triggers the generation of recommendations.
     */
    private void finalizeHobbyResponses() {
        sendBotMessage("Generating your perfect holiday destination...");
        StringBuilder activities = new StringBuilder();
        for (String hobby : hobbyResponses.values()) {
            this.activities.append(hobby).append(", ");
        }
        generateRecommendations(this.activities.toString(), chosenLocation);
        System.out.println(hobbyResponses);
        hobbyResponses.clear();
        locationResponses.clear();
        currentHobbyQuestionIndex = 0; // Reset for future sessions
    }

    /**
     * Generates recommendations based on the group's location and hobbies.
     *
     * @param activities Combined string of hobbies and preferences.
     * @param location   Chosen vacation location.
     */
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

    /**
     * Determines the most common response from a collection of responses.
     *
     * @param responses Collection of user responses.
     * @return The most common response.
     */
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

    /**
     * Displays the generated vacation recommendations.
     *
     * @param recommendationsJson JSON string containing recommendations.
     */
    private void displayRecommendations(String recommendationsJson) {
        try {
            System.out.println("[VBI] Trying to display recommendation");
            JSONObject responseObject = new JSONObject(recommendationsJson);
            JSONArray vacationSpots = null;
            if (responseObject.has("vacationSpots")) {
                vacationSpots = responseObject.getJSONArray("vacationSpots");
                System.out.println(vacationSpots);
            } else if (responseObject.has("vacation_spots")) {
                vacationSpots = responseObject.getJSONArray("vacation_spots");
            }else if (responseObject.has("recommended_spots")) {
                vacationSpots = responseObject.getJSONArray("recommended_spots");
            } else {
                System.err.println("No 'vacationSpots' or 'vacation_spots' key found in response");
            }
            System.out.print(vacationSpots);

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

    /**
     * Handles the message passed.
     *
     * @param username The sender of the message.
     * @param message The message received.
     * @param groupSize The size of the current group.
     * @param groupID The group ID.
     */
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

    /**
     * Sends the groupID to the chat.
     * @param groupID The GroupID.
     */

    public void sendGroupID(String groupID) {
        sendBotMessage(groupID);
    }

    /**
     * Sets the threshold based off the size of the group.
     *
     * @param threshold The threshold of the group.
     */
    @Override
    public void setThreshold(int threshold) {
        this.threshold = threshold;
        if (botState == BotState.AWAITING_LOCATION) {
            processLocationResponses();
        } else if (botState == BotState.AWAITING_HOBBIES) {
            processHobbyResponses();
        }
    }

    /**
     * Removes responses from members who are no longer part of the group.
     *
     * @param members List of current group members.
     */
    public void removeResponse(List<String> members) {
        if (botState == BotState.AWAITING_LOCATION) {
            removeMissingMemberResponse(members, locationResponses);
        } else if (botState == BotState.AWAITING_HOBBIES) {
            removeMissingMemberResponse(members, hobbyResponses);
        }
    }

    /**
     * Helper method to remove responses from missing members.
     *
     * @param members   List of current group members.
     * @param responses Map of responses to check.
     */
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

}