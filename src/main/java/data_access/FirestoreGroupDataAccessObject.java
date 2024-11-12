package data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import entity.Group;
import entity.GroupFactory;
import entity.Message;
import entity.Response;
import use_case.create_group.CreateGroupDataAccessInterface;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.*;


public class FirestoreGroupDataAccessObject implements CreateGroupDataAccessInterface {

    private final GroupFactory groupFactory;

    public FirestoreGroupDataAccessObject(GroupFactory groupFactory) {
        this.groupFactory = groupFactory;
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
                // TODD: Fix group
                System.out.println("No such document!");
                return null;
            }

            List<String> usernames = Arrays.asList(document.get("usernames").toString().split(","));
            List<String> recommendations = Arrays.asList(document.get("recommendedLocations").toString().split(","));
            List<String> chosen = Arrays.asList(document.get("chosenDestinations").toString().split(","));
            List<Response> responses = new ArrayList<>();
            List<Message> messages = new ArrayList<>();

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
        data.put("ChosenDestinations", group.getChosenLocations());
        data.put("groupName", group.getGroupName());
        data.put("ChosenLocations", group.getChosenLocations());
        data.put("Messages", null);
        data.put("RecommendedLocations", group.getRecommendedLocations());
        data.put("Responses", group.getResponses());
        data.put("Users", group.getUsernames());

        db.collection("groups").document(group.getGroupName()).set(data);

    }


}

