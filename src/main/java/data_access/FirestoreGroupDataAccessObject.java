package data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import entity.*;
import use_case.create_group.CreateGroupDataAccessInterface;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.*;


public class FirestoreGroupDataAccessObject implements CreateGroupDataAccessInterface {

    private final GroupFactory groupFactory;
    private final ResponseFactory responseFactory;
    private final MessageFactory messageFactory;

    public FirestoreGroupDataAccessObject(GroupFactory groupFactory,
                                          ResponseFactory responseFactory, MessageFactory messageFactory) {
        this.groupFactory = groupFactory;
        this.responseFactory = responseFactory;
        this.messageFactory = messageFactory;
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
            List<String> recommendations = (ArrayList)document.get("recommendedLocations");
            List<String> chosen = (ArrayList)document.get("chosenDestinations");

            List<Response> responses = new ArrayList<>();
            List<Object> responseArrayList = (ArrayList)document.get("responses");
            for (Object obj : responseArrayList) {
                Map<String, String> response = (Map<String, String>)obj;
                String question = response.get("question");
                String answer = response.get("answer");
                responses.add(responseFactory.create(question, answer));
            }

            List<Message> messages = new ArrayList<>();
            List<Object> messageArrayList = (ArrayList)document.get("messages");
            for (Object obj : messageArrayList) {
                Map<String, Object> message = (Map<String, Object>)obj;
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
        data.put("ChosenDestinations", group.getChosenLocations());
        data.put("groupName", group.getGroupName());
        data.put("ChosenLocations", group.getChosenLocations());
        data.put("Messages", group.getMessages());
        data.put("RecommendedLocations", group.getRecommendedLocations());
        data.put("Responses", group.getResponses());
        data.put("Users", group.getUsernames());

        ApiFuture<WriteResult> future = db.collection("groups").document(group.getGroupName()).set(data);
        try {
            System.out.println("Successfully updated at: " + future.get().getUpdateTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}

