package data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import entity.*;
import use_case.create_group.CreateGroupDataAccessInterface;

import com.google.firebase.cloud.FirestoreClient;

import java.util.*;


public class FirestoreGroupDataAccessObject implements CreateGroupDataAccessInterface {

    private final GroupFactory groupFactory;
    private final ResponseFactory responseFactory;
    private final MessageFactory messageFactory;
    private final RecommendationFactory recommendationFactory;

    public FirestoreGroupDataAccessObject(GroupFactory groupFactory,
                                          ResponseFactory responseFactory,
                                          MessageFactory messageFactory,
                                          RecommendationFactory recommendationFactory) {
        this.groupFactory = groupFactory;
        this.responseFactory = responseFactory;
        this.messageFactory = messageFactory;
        this.recommendationFactory = recommendationFactory;
    }

    @Override
    public Group get(String groupName) {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("groups").document(groupName);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                System.out.println("Document data: " + document.getData());
            } else {
                System.out.println("No such document!");
                return null;
            }

            List<String> usernames = (ArrayList)document.get("usernames");
            List<String> chosen = (ArrayList)document.get("chosenLocations");

            List<Recommendation> recommendations = new ArrayList<>();
            List<Object> recommendationArrayList = (ArrayList)document.get("recommendations");
            for (Object obj : recommendationArrayList) {
                Map<String, Object> recommendation = (Map)obj;
                String location = (String)recommendation.get("location");
                String description = (String)recommendation.get("description");
                GeoPoint coordinates = (GeoPoint)recommendation.get("coordinates");
                int rating = (int)(long)recommendation.get("rating");
                ArrayList<Boolean> votes = (ArrayList<Boolean>)document.get("votes");
                recommendations.add(recommendationFactory.create(location, description, coordinates, rating, votes));
            }

            List<Response> responses = new ArrayList<>();
            List<Object> responseArrayList = (ArrayList)document.get("responses");
            for (Object obj : responseArrayList) {
                Map<String, String> response = (Map)obj;
                String question = response.get("question");
                String answer = response.get("answer");
                responses.add(responseFactory.create(question, answer));
            }

            List<Message> messages = new ArrayList<>();
            List<Object> messageArrayList = (ArrayList)document.get("messages");
            for (Object obj : messageArrayList) {
                Map<String, Object> message = (Map)obj;
                String user = (String)message.get("user");
                Timestamp timestamp = (Timestamp)message.get("timestamp");
                String content = (String)message.get("content");
                messages.add(messageFactory.createMessage(user, content, timestamp));
            }

            return groupFactory.create(groupName, usernames, responses, recommendations, chosen, messages);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Group group) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        Map<String, Object> data = new HashMap<>();
        data.put("groupName", group.getGroupName());
        data.put("chosenLocations", group.getChosenLocations());
        data.put("messages", group.getMessages());
        data.put("recommendations", group.getRecommendedLocations());
        data.put("responses", group.getResponses());
        data.put("usernames", group.getUsernames());

        ApiFuture<WriteResult> future = db.collection("groups").document(group.getGroupName()).set(data);
        try {
            System.out.println("Successfully updated at: " + future.get().getUpdateTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        FirestoreDataAccessObject db = new FirestoreDataAccessObject();
        GroupFactory groupFactory = new CommonGroupFactory();
        ResponseFactory responseFactory = new CommonResponseFactory();
        MessageFactory messageFactory = new CommonMessageFactory();
        RecommendationFactory recommendationFactory = new CommonRecommendationFactory();
        FirestoreGroupDataAccessObject groupDataAccessObject = new FirestoreGroupDataAccessObject(groupFactory, responseFactory, messageFactory, recommendationFactory);

        String groupName = "testGroup";

        List<String> usernames = new ArrayList<>();
        usernames.add("Bob");

        List<Response> responses = new ArrayList<>();
        responses.add(responseFactory.create("Question", "Answer"));

        List<Recommendation> recommendations = new ArrayList<>();
        List<Boolean> votes = new ArrayList<>();
        votes.add(true);
        votes.add(false);
        recommendations.add(recommendationFactory.create("location",
                "description", new GeoPoint(0, 0), 5, votes));

        List<String> chosenLocations = new ArrayList<>();
        chosenLocations.add("chosenLocation");

        List<Message> messages = new ArrayList<>();
        messages.add(messageFactory.createMessage("Bob", "content"
                , Timestamp.now()));

        Group group = groupDataAccessObject.get(groupName);
        System.out.println(group.getGroupName());
    }
}

