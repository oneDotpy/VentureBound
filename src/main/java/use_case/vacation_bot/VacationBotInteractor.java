package use_case.vacation_bot;

import java.util.*;

import app.OpenAIChatGPT;
import com.google.cloud.Timestamp;
import data_access.FirestoreGroupDataAccessObject;
import data_access.FirestoreUserDataAccessObject;
import entity.*;
import interface_adapter.chat.ChatViewModel;
import org.json.JSONArray;
import org.json.JSONObject;
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
    private final ResponseFactory responseFactory;
    private User user;

    private final Map<String, String> locationResponses = new HashMap<>();
    private final Map<String, String> hobbyResponses = new HashMap<>();

    public VacationBotInteractor(VacationBotOutputBoundary presenter,
                                 ChatViewModel chatViewModel,
                                 FirestoreUserDataAccessObject firestoreUserDataAccessObject,
                                 FirestoreGroupDataAccessObject groupDataAccessObject,
                                 MessageFactory messageFactory,
                                 ResponseFactory responseFactory) {
        this.presenter = presenter;
        this.chatViewModel = chatViewModel;
        this.chatGPT = new OpenAIChatGPT();
        this.messageFactory = messageFactory;
        this.responseFactory = responseFactory;
        this.firestoreUserDataAccessObject = firestoreUserDataAccessObject;
        this.groupDataAccessObject = groupDataAccessObject;
    }

    @Override
    public void startBot() {
        if (botState.equals(BotState.INACTIVE)) {
            System.out.println("[VBI1] start");
            this.user = createBotUser();
            botState = BotState.AWAITING_LOCATION;
            System.out.println("[VBI2.5] succesfully created bot and updated botstate");

            sendBotMessage("🌍 Vacation Bot started! 🛫\nPlease answer the following questions or send /stop to stop bot.\n\n**Question 1:** Where would you like to go for a vacation?");
        }
        else {
            sendBotMessage("The Bot Already Started");
        }
    }

    @Override
    public void stopBot() {
        if (!botState.equals(BotState.INACTIVE)) {
            botState = BotState.INACTIVE;
            sendBotMessage("Vacation Bot has been stopped.");
        }
        else {
            sendBotMessage("The Bot is currently inactive");
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
        System.out.println("[VBI2] Create bot at " + chatViewModel.getState().getCurrentUser().getGroupID());
        UserFactory userFactory = new CommonUserFactory();
        return userFactory.create("Bot", null, chatViewModel.getState().getUser().getGroupID());
    }

    private void processLocationResponses(String username) {
        String chosenLocation = determineMostCommon(locationResponses.values());
        String user = chatViewModel.getState().getCurrentUser().getName();
        if (username.equals(user)) {
            sendBotMessage("\n📍 The chosen vacation location is: **" + chosenLocation + "**");
        }

        botState = BotState.AWAITING_HOBBIES;

        if (username.equals(user)) {
            sendBotMessage("\n**Question 2:** What is your favorite hobbies? (Please choose one)");
        }
    }

    private void processHobbyResponses(String username) {
        StringBuilder activities = new StringBuilder();
        for (String hobby : hobbyResponses.values()) {
            activities.append(hobby).append(", ");
        }

        System.out.println("[VBI] Reached processHobbyResponse");

        String chosenHobby = determineMostCommon(hobbyResponses.values());
        System.out.println("Chosen location: " + chosenHobby);
        System.out.println("[VBI] before call generate");
        generateRecommendations(username, chosenHobby, activities.toString());
    }

    private void generateRecommendations(String username, String location, String activities) {
        System.out.println("[VBI] Generate recommendation before botstate");
        botState = BotState.GENERATING_RECOMMENDATIONS;
        System.out.println("[VBI] Genereate recommendation after botstate");
        try {
            if (chatViewModel.getState().getCurrentUser().getName().equals(username)) {
                System.out.println("[VBI] Trying to generate recommendation");
                String recommendationsJson = OpenAIChatGPT.getVacationRecommendations(activities, location);
                displayRecommendations(recommendationsJson);
            }
        } catch (Exception e) {
            sendBotMessage("❌ Error generating recommendations: " + e.getMessage());
        } finally {
            botState = BotState.INACTIVE;
        }
    }

    @Override
    public void handleMessage(String username, String message, int groupSize) {
        if (botState == BotState.AWAITING_LOCATION) {
            locationResponses.put(username, message);
            sendBotMessage(username + " chose: " + message);
            // Check if all members have responded
            if (locationResponses.size() == groupSize) {
                processLocationResponses(username);
                locationResponses.clear();
            }

        } else if (botState == BotState.AWAITING_HOBBIES) {
            hobbyResponses.put(username, message);
            sendBotMessage(username + " enjoys: " + message);

            // Check if all members have responded
            if (hobbyResponses.size() == groupSize) {
                botCalled = true;
                sendBotMessage("Generating your perfect holiday destination....");
                if (botCalled) {
                    processHobbyResponses(username);
                    hobbyResponses.clear();
                }
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
        System.out.println("[VBI] recieved responses: " + responses);
        System.out.println("[VBI] Determine most common response 1");
        Map<String, Integer> countMap = new HashMap<>();
        for (String response : responses) {
            countMap.put(response, countMap.getOrDefault(response, 0) + 1);
        }
        System.out.println("[VBI] Determine most common response 2");
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(countMap.entrySet());
        entries.sort((a, b) -> b.getValue() - a.getValue());

        if (entries.size() > 1 && entries.get(0).getValue().equals(entries.get(1).getValue())) {
            System.out.println("[VBI] Determine most common response 2.5");
            return entries.get(new Random().nextInt(entries.size())).getKey();
        }
        System.out.println("[VBI] Determine most common response 3");
        System.out.println("[VBI] Before return most common: " + entries);
        return entries.get(0).getKey();
    }
}
