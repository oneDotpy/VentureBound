package data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import entity.User;
import entity.UserFactory;
import entity.Group;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
//import use_case.logout.LogoutUserDataAccessInterface;
//import use_case.signup.SignupUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class FirestoreUserDataAccessObject extends FirestoreDataAccessObject
        implements //SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface {
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
            String groupName = document.get("groups").toString();
            Group group = firestoreGroupDataAccessObject.get("groupName");
            return userFactory.create(username, password, email, group);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(User user) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getName());
        data.put("password", user.getPassword());
//        data.put("email", user.getEmail());
//        data.put("group", user.getGroup());
        db.collection("users").document(user.getName()).set(data);
    }


    @Override
    public void changePassword(User user) {
        Firestore db = FirestoreDataAccessObject.getFirestore();
        db.collection("users")
                .document(user.getName())
                .update("password", user.getPassword());

    }

    @Override
    public String getCurrentUsername() {
        return "";
    }

    @Override
    public void setCurrentUsername(String username) {

    }

    @Override
    public boolean existsByName(String username) { return false; }
}
