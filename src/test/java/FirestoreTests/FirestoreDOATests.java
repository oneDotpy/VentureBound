package FirestoreTests;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.opentelemetry.resource.GcpResource;
import data_access.FirestoreDataAccessObject;
import data_access.FirestoreGroupDataAccessObject;
import data_access.FirestoreUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.Test;

public class FirestoreDOATests {

    @Test
    void connectionTest() {
        UserFactory userFactory = new CommonUserFactory();
        GroupFactory groupFactory = new CommonGroupFactory();
        FirestoreGroupDataAccessObject firestoreGroupDataAccessObject = new FirestoreGroupDataAccessObject(groupFactory);
        FirestoreUserDataAccessObject firestoreUserDataAccessObject = new FirestoreUserDataAccessObject(userFactory, firestoreGroupDataAccessObject);

        Firestore db = firestoreUserDataAccessObject.getFirestore();
        DocumentReference docRef = db.collection("users").document("Bob");

        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                System.out.println("Document data: " + document.getData());
            } else {
                System.out.println("No such document!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void successLoginTest() {
        UserFactory userFactory = new CommonUserFactory();
        GroupFactory groupFactory = new CommonGroupFactory();
        FirestoreGroupDataAccessObject firestoreGroupDataAccessObject = new FirestoreGroupDataAccessObject(groupFactory);
        FirestoreUserDataAccessObject firestoreUserDataAccessObject = new FirestoreUserDataAccessObject(userFactory, firestoreGroupDataAccessObject);

        Firestore db = firestoreUserDataAccessObject.getFirestore();
        DocumentReference docRef = db.collection("users").document("Bob");

        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                System.out.println("Document data: " + document.getData());
            } else {
                System.out.println("No such document!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
