package data_access;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FirestoreDataAccessObject {

    static Firestore db;

    public FirestoreDataAccessObject() {
        try{
            FileInputStream inputStream = new FileInputStream("src/main/resources/venturebound-92fe9-firebase-adminsdk-h78vn-86fc202401.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setProjectId("venturebound-92fe9") // Your project ID
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("Firestore successfully loaded");
            // Return Firestore client
            db = FirestoreClient.getFirestore();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("BP 3");
        }
    }

    static Firestore getFirestore() { return db; }
}
