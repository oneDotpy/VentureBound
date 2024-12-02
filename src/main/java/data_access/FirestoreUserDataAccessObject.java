package data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import entity.User;
import entity.UserFactory;
import entity.Group;
import use_case.create_group.CreateGroupUserDataAccessInterface;
import use_case.join_group.JoinGroupUserDataAccessInterface;
import use_case.leave_group.LeaveGroupUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.send_message.SendMessageUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.vacation_bot.VacationBotUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirestoreUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        JoinGroupUserDataAccessInterface,
        CreateGroupUserDataAccessInterface,
        LeaveGroupUserDataAccessInterface,
        SendMessageUserDataAccessInterface,
        VacationBotUserDataAccessInterface {

    private UserFactory userFactory;
    private FirestoreGroupDataAccessObject firestoreGroupDataAccessObject;

    public FirestoreUserDataAccessObject(UserFactory userFactory,
                                         FirestoreGroupDataAccessObject firestoreGroupDataAccessObject) {
        this.userFactory = userFactory;
        this.firestoreGroupDataAccessObject = firestoreGroupDataAccessObject;
    }

    @Override
    public User get(String username) {
        Firestore db = FirestoreDataAccessObject.getInstance();
        DocumentReference docRef = db.collection("users").document(username);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                System.out.println("Document data: " + document.getData());
            } else {
                System.out.println("No such document!");
                return null;
            }

            String password = document.get("password").toString();
            String email = document.get("email").toString();
            String groupID = document.get("group").toString();
            Group group = null;
            if (firestoreGroupDataAccessObject.existByID(groupID)) {
                group = firestoreGroupDataAccessObject.get(groupID);
            }
            return userFactory.create(username, password, email, group, groupID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setGroupID(String groupID, String username) {
        Firestore db = FirestoreDataAccessObject.getInstance();
        DocumentReference docRef = db.collection("users").document(username);
        docRef.update("group", groupID);
    }

    @Override
    public void save(User user) {
        Firestore db = FirestoreDataAccessObject.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getName());
        data.put("password", user.getPassword());
        data.put("email", user.getEmail());
        if (user.getGroup() != null) {
            data.put("group", user.getGroup().getGroupID());
        } else {
            data.put("group", "");
        }
        ApiFuture<WriteResult> future = db.collection("users").document(user.getName()).set(data);
        try {
            System.out.println(future.get());
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void changePassword(String username, String password) {
        Firestore db = FirestoreDataAccessObject.getInstance();
        db.collection("users")
                .document(username)
                .update("password", password);

    }

    @Override
    public Timestamp getTimestamp(String username) {
        System.out.println("Get Timestamp Called [GroupDAO]");
        Firestore db = FirestoreDataAccessObject.getInstance();
        DocumentReference docRef = db.collection("users").document(username);
        // Update the timestamp field with the value from the server
        ApiFuture<WriteResult> writeResult = docRef.update("timestamp", FieldValue.serverTimestamp());
        try {
            System.out.println("Update time : " + writeResult.get());
        } catch (InterruptedException e) {
            System.out.println("InterruptedException [GetTimestamp GroupDAO]");
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            System.out.println("ExecutionException [GetTimestamp GroupDAO]");
            throw new RuntimeException(e);
        }
        // waiting for the server to update
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // getting the timestamp from the server
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                System.out.println("Document data: " + document.getData());
            } else {
                System.out.println("No such document!");
            }
            return (Timestamp)document.get("timestamp");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
