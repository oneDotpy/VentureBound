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

import java.util.HashMap;
import java.util.Map;

public class FirestoreUserDataAccessObject{

    private UserFactory userFactory;
    private FirestoreGroupDataAccessObject firestoreGroupDataAccessObject;

    public FirestoreUserDataAccessObject(UserFactory userFactory,
                                         FirestoreGroupDataAccessObject firestoreGroupDataAccessObject) {
        this.userFactory = userFactory;
        this.firestoreGroupDataAccessObject = firestoreGroupDataAccessObject;
    }


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
            String groupName = document.get("group").toString();
            Group group = firestoreGroupDataAccessObject.get("groupName");
            return userFactory.create(email, password, email, group);
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
        data.put("group", user.getGroup());

        ApiFuture<WriteResult> future = db.collection("users").document(user.getName()).set(data);
        try {
            System.out.println(future.get());
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
    }
}