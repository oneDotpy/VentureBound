package data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.EventListener;
import com.google.firebase.database.annotations.Nullable;
import entity.*;

import com.google.firebase.cloud.FirestoreClient;

import java.util.*;


public class FirestoreGroupDataAccessObject {

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

            List<QueryDocumentSnapshot> recommendationDocs = docRef.collection("recommendations").get().get().getDocuments();
            List<Recommendation> recommendations = new ArrayList<>();
            for (DocumentSnapshot doc : recommendationDocs) {
                String location = (String)doc.get("location");
                String description = (String)doc.get("description");
                GeoPoint coordinates = (GeoPoint)doc.get("coordinates");
                int rating = (int)(long)doc.get("rating");
                recommendations.add(recommendationFactory.create(location, description, coordinates, rating));
            }

            List<QueryDocumentSnapshot> responseDocs = docRef.collection("responses").get().get().getDocuments();
            List<Response> responses = new ArrayList<>();
            for (DocumentSnapshot doc : responseDocs) {
                String user = (String)doc.get("user");
                String answer = (String)doc.get("answer");
                responses.add(responseFactory.create(user, answer));
            }

            List<QueryDocumentSnapshot> messageDocs = docRef.collection("messages").get().get().getDocuments();
            List<Message> messages = new ArrayList<>();
            for (DocumentSnapshot doc : messageDocs) {
                String user = (String)doc.get("user");
                Timestamp timestamp = (Timestamp)doc.get("timestamp");
                String content = (String)doc.get("content");
                messages.add(messageFactory.createMessage(user, content, timestamp));
            }

            return groupFactory.create(groupName, usernames, responses, recommendations, chosen, messages);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateResponses(String groupName, List<Response> responses) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        DocumentReference ref = db.collection("groups").document(groupName);
        for (Response response : responses) {
            ApiFuture<WriteResult> future = ref.collection("responses").document(response.getUser()).set(response);
            try {
                System.out.println("Successfully updated at: " + future.get().getUpdateTime());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateRecommendations(String groupName, List<Recommendation> recommendations) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        DocumentReference ref = db.collection("groups").document(groupName);
        for (Recommendation recommendation : recommendations) {
            ApiFuture<WriteResult> future = ref.collection("recommendations").document(recommendation.getLocation()).set(recommendation);
            try {
                System.out.println("Successfully updated at: " + future.get().getUpdateTime());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateMessages(String groupName, List<Message> messages) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        DocumentReference ref = db.collection("groups").document(groupName);
        for (Message message : messages) {
            ApiFuture<WriteResult> future = ref.collection("messages").document(message.getTimestamp().toString()).set(message);
            try {
                System.out.println("Successfully updated at: " + future.get().getUpdateTime());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void save(Group group) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        DocumentReference ref = db.collection("groups").document(group.getGroupName());
        String groupName = group.getGroupName();

        Map<String, Object> data = new HashMap<>();
        data.put("groupName", group.getGroupName());
        data.put("usernames", group.getUsernames());
        data.put("chosenLocations", group.getChosenLocations());
        ApiFuture<WriteResult> future = ref.set(data);
        try {
            System.out.println("Successfully updated at: " + future.get().getUpdateTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
        updateResponses(groupName, group.getResponses());
        updateRecommendations(groupName, group.getRecommendedLocations());
        updateMessages(groupName, group.getMessages());
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
        responses.add(responseFactory.create("Bob", "Answer"));

        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(recommendationFactory.create("location",
                "description", new GeoPoint(0, 0), 5));

        List<String> chosenLocations = new ArrayList<>();
        chosenLocations.add("chosenLocation");

        List<Message> messages = new ArrayList<>();
        messages.add(messageFactory.createMessage("Bob", "content", Timestamp.now()));

        Group group = groupFactory.create(groupName, usernames, responses, recommendations, chosenLocations, messages);
        groupDataAccessObject.save(group);
    }
}

