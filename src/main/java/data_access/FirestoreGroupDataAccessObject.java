package data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import entity.*;
import use_case.chat.RealTimeChatUpdatesUseCase;
import use_case.create_group.CreateGroupDataAccessInterface;

import com.google.firebase.cloud.FirestoreClient;
import use_case.join_group.JoinGroupGroupDataAccessInterface;
import use_case.send_message.SendMessageDataAccessInterface;

import java.util.*;
import java.util.concurrent.ExecutionException;


public class FirestoreGroupDataAccessObject implements CreateGroupDataAccessInterface, JoinGroupGroupDataAccessInterface,SendMessageDataAccessInterface {

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

    public boolean existByID(final String ID) {
        Firestore db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection("groups").document(ID);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<Recommendation> getRecommendations(String groupID) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("groups").document(groupID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        List<QueryDocumentSnapshot> recommendationDocs = docRef.collection("recommendations").get().get().getDocuments();
        List<Recommendation> recommendations = new ArrayList<>();
        for (DocumentSnapshot doc : recommendationDocs) {
            String location = (String)doc.get("location");
            String description = (String)doc.get("description");
            GeoPoint coordinates = (GeoPoint)doc.get("coordinates");
            int rating = (int)(long)doc.get("rating");
            recommendations.add(recommendationFactory.create(location, description, coordinates, rating));
        }
        return recommendations;
    }

    public List<Response> getResponses(String groupID) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("groups").document(groupID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        List<QueryDocumentSnapshot> responseDocs = docRef.collection("responses").get().get().getDocuments();
        List<Response> responses = new ArrayList<>();
        for (DocumentSnapshot doc : responseDocs) {
            String user = (String)doc.get("user");
            String answer = (String)doc.get("answer");
            responses.add(responseFactory.create(user, answer));
        }
        return responses;
    }

    public List<Message> getMessages(String groupID, int index) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("groups").document(groupID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        List<QueryDocumentSnapshot> messageDocs = docRef.collection("messages").get().get().getDocuments();
        List<Message> messages = new ArrayList<>();
        for (int i = index; i < messageDocs.size(); i++) {
            DocumentSnapshot doc = messageDocs.get(i);
            String user = (String)doc.get("sender");
            Timestamp timestamp = (Timestamp)doc.get("timestamp");
            String content = (String)doc.get("content");
            messages.add(messageFactory.createMessage(user, content, timestamp));
        }
        return messages;
    }

    @Override
    public Group get(String groupID) {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("groups").document(groupID);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                System.out.println("Document data: " + document.getData());
            } else {
                System.out.println("No such document!");
                return null;
            }

            String groupName = (String)document.get("groupName");
            List<String> usernames = (ArrayList)document.get("usernames");
            List<String> chosen = (ArrayList)document.get("chosenLocations");
            List<Recommendation> recommendations = getRecommendations(groupID);
            List<Response> responses = getResponses(groupID);
            List<Message> messages = getMessages(groupID, 0);

            for (Message message : messages) {
                System.out.println(message.getSender() + ": " + message.getContent());
            }

            return groupFactory.create(groupName, usernames, responses, recommendations, chosen, messages, groupID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUsers(Group group) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        DocumentReference ref = db.collection("groups").document(group.getGroupID());
        String groupName = group.getGroupName();

        Map<String, Object> data = new HashMap<>();
        data.put("usernames", group.getUsernames());
        ApiFuture<WriteResult> future = ref.update(data);
        try {
            System.out.println("Successfully updated at: " + future.get().getUpdateTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateChosenLocations(Group group) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        DocumentReference ref = db.collection("groups").document(group.getGroupID());
        String groupName = group.getGroupID();

        Map<String, Object> data = new HashMap<>();
        data.put("chosenLocations", group.getChosenLocations());
        ApiFuture<WriteResult> future = ref.update(data);
        try {
            System.out.println("Successfully updated at: " + future.get().getUpdateTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateResponses(String groupID, List<Response> responses) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        System.out.println(groupID);
        DocumentReference ref = db.collection("groups").document(groupID);
        for (Response response : responses) {
            ApiFuture<WriteResult> future = ref.collection("responses").document(response.getUser()).set(response);
            try {
                System.out.println("Successfully updated at: " + future.get().getUpdateTime());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateRecommendations(String groupID, List<Recommendation> recommendations) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        DocumentReference ref = db.collection("groups").document(groupID);
        for (Recommendation recommendation : recommendations) {
            ApiFuture<WriteResult> future = ref.collection("recommendations").document(recommendation.getLocation()).set(recommendation);
            try {
                System.out.println("Successfully updated at: " + future.get().getUpdateTime());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateMessages(String groupID, List<Message> messages) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        DocumentReference ref = db.collection("groups").document(groupID);
        for (Message message : messages) {
            ApiFuture<WriteResult> future = ref.collection("messages").document(message.getTimestamp().toString()).set(message);
            try {
                System.out.println("Successfully updated at: " + future.get().getUpdateTime());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateMessage(String groupID, Message message) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        updateMessages(groupID, messages);
    }

    @Override
    public String save(Group group) {
        String groupName = group.getGroupName();
        Firestore db = FirestoreDataAccessObject.getFirestore();
        String groupID = group.getGroupID();
        Map<String, Object> data = new HashMap<>();
        data.put("groupName", group.getGroupName());
        data.put("usernames", group.getUsernames());
        data.put("chosenLocations", group.getChosenLocations());
        data.put("groupID", group.getGroupID());
        if ("".equals(groupID)){
            try {
                DocumentReference docRef = db.collection("groups").add(data).get();
                String documentID = docRef.getId();
                groupID = documentID;
                db.collection("groups").document(documentID).update("groupID", documentID).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                DocumentReference docRef = db.collection("groups").document(group.getGroupID());
                docRef.set(data).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        // DocumentReference ref = db.collection("groups").document(group.getGroupName());
        // ApiFuture<WriteResult> future = ref.set(data);
        // try {
        //    System.out.println("Successfully updated at: " + future.get().getUpdateTime());
        // } catch(Exception e) {
            // e.printStackTrace();
        // }
        updateResponses(groupID, group.getResponses());
        updateRecommendations(groupID, group.getRecommendedLocations());
        updateMessages(groupID, group.getMessages());
        return groupID;
    }

    public void join(String groupID, String username) {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("groups").document(groupID);
        ApiFuture<WriteResult> arrayUnion =
                docRef.update("usernames", FieldValue.arrayUnion(username));
    }

    public void setListener(String groupID, GroupListener groupListener) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("groups").document(groupID).addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                e.printStackTrace();
            }
            if (snapshot != null) {
                List<String> usernames = (ArrayList)snapshot.get("usernames");
                groupListener.onDatabaseChanged(usernames);
            }
        });
    }

    public interface GroupListener{
        public void onDatabaseChanged(List<String> usernames);
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

        Group group = groupFactory.create(groupName, usernames, responses, recommendations, chosenLocations, messages, "10090fdas");
        groupDataAccessObject.save(group);
    }
    public void setGroupMemberListener(String groupID, RealTimeChatUpdatesUseCase.GroupMemberUpdateListener listener) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("groups").document(groupID).addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                listener.onError(error);
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                List<String> members = (List<String>) snapshot.get("usernames");
                listener.onGroupMembersUpdated(members);
            }
        });
    }

    public void setMessageListener(String groupID, RealTimeChatUpdatesUseCase.MessageUpdateListener listener) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("groups").document(groupID).collection("messages")
                .addSnapshotListener((snapshots, error) -> {
                    if (error != null) {
                        listener.onError(error);
                        return;
                    }
                    if (snapshots != null) {
                        Map<String, String> messages = new HashMap<>();
                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            String user = (String) doc.get("sender");
                            String content = (String) doc.get("content");
                            Timestamp timestamp = (Timestamp) doc.get("timestamp");
                            messages.put(user, content); // Assuming Message has such a constructor
                        }
                        listener.onMessagesUpdated(messages);
                    }
                });
    }

}

