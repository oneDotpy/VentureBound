package data_access;

import entity.Group;
import use_case.create_group.CreateGroupDataAccessInterface;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import entity.UserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class FirestoreGroupDataAccessObject implements CreateGroupDataAccessInterface {

    private final Group group;

    public FirestoreGroupDataAccessObject(UserFactory userFactory, Group group){
        this.group = group;

    }


    public static Firestore getFirestore() throws IOException {
        try{
            FileInputStream inputStream = new FileInputStream("src/main/resources/firestoretesting-natuap-firebase-adminsdk-co880-01fe6ae1db.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setProjectId("VentureBound") // Your project ID
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("Firestore successfully loaded");
            // Return Firestore client
            return FirestoreClient.getFirestore();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("BP 3");
    }


    @Override
    public void save(Group group) {
        try{
            Firestore firestore = getFirestore();
            Map<String, Object> data = new HashMap<>();
            data.put("ChosenDestinations", group.getChosenLocations());
            data.put("groupName", group.getGroupName());
            data.put("ChosenLocations", group.getChosenLocations());
            data.put("Messages", null);
            data.put("RecommendedLocations", group.getRecommendedLocations());
            data.put("Responses", group.getResponses());
            data.put("Users", group.getUsers());

            firestore.collection("groups").document(group.getGroupName()).set(data);
        }
        catch (IOException e){
            System.out.println("Failed to catch database");;
        }
    }
}

