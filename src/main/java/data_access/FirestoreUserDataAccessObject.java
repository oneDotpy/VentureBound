package data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import entity.User;
import entity.UserFactory;
import entity.Group;
//import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.change_password.ChangePasswordDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
//import use_case.logout.LogoutUserDataAccessInterface;
//import use_case.signup.SignupUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class FirestoreUserDataAccessObject extends FirestoreDataAccessObject
        implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordDataAccessInterface {
        //LogoutUserDataAccessInterface {

    private UserFactory userFactory;
    private FirestoreGroupDataAccessObject firestoreGroupDataAccessObject;

    public FirestoreUserDataAccessObject(UserFactory userFactory,
                                         FirestoreGroupDataAccessObject firestoreGroupDataAccessObject) {
        this.userFactory = userFactory;
        this.firestoreGroupDataAccessObject = firestoreGroupDataAccessObject;
    }

    @Override
    public User get(String username) {
        Firestore db = FirestoreClient.getFirestore();
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
            Group group = null;
            if (document.get("group") != "") {
                String groupId = document.get("group").toString();
                group = firestoreGroupDataAccessObject.get(groupId);
            }
            return userFactory.create(username, password, email, group);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(User user) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
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
        Firestore db = FirestoreDataAccessObject.getFirestore();
        db.collection("users")
                .document(username)
                .update("password", password);

    }
}
