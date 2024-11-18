package app;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;

public class OpenAIChatGPT {
    private static final String API_KEY = loadApiKey(); // Replace with actual API key
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private static OkHttpClient client;

    public OpenAIChatGPT() {
        client = new OkHttpClient();
    }

    private static String loadApiKey() {
        Dotenv dotenv = Dotenv.configure().directory("src/main/resources/.env").load();
        String apiKey = dotenv.get("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("API_KEY not found in ..env file");
        }
        return apiKey;
    }
    public static String getVacationRecommendations(String activities, String locations) throws IOException {
        String prompt = String.format(
                "Me and my friends like %s. Sticking with %s locations, provide a JSON object with 5 recommended vacation spots based of our interests. " +
                        "Include name, latitude, longitude, and a Google Maps link for each spot. ",
                activities, locations
        );

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("model", "gpt-3.5-turbo");
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);
        messages.put(message);
        jsonBody.put("messages", messages);

        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseString = response.body().string();
            System.out.println("Full API Response: " + responseString);

            JSONObject responseObject = new JSONObject(responseString);
            JSONArray choices = responseObject.getJSONArray("choices");
            String reply = choices.getJSONObject(0).getJSONObject("message").getString("content");

            // Extract JSON from the reply
            int jsonStartIndex = reply.indexOf("{");
            if (jsonStartIndex != -1) {
                String jsonResponse = reply.substring(jsonStartIndex).trim();
                return addGoogleMapsLinks(jsonResponse);
            } else {
                throw new IOException("No JSON response received from OpenAI.");
            }
        }
    }

    private static String addGoogleMapsLinks(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);

            JSONArray vacationSpots = null;
            if (jsonObject.has("vacationSpots")) {
                vacationSpots = jsonObject.getJSONArray("vacationSpots");
                System.out.println(vacationSpots);
            } else if (jsonObject.has("vacation_spots")) {
                vacationSpots = jsonObject.getJSONArray("vacation_spots");
            } else {
                System.err.println("No 'vacationSpots' or 'vacation_spots' key found in response");
                return "Error: No 'vacationSpots' or 'vacation_spots' key found in response.";
            }

            // Process each vacation spot
            for (int i = 0; i < vacationSpots.length(); i++) {
                JSONObject place = vacationSpots.getJSONObject(i);

                // Check if latitude and longitude are present
                if (place.has("latitude") && place.has("longitude")) {
                    double latitude = place.getDouble("latitude");
                    double longitude = place.getDouble("longitude");

                    // Generate Google Maps link
                    String googleMapsLink = String.format(
                            "https://www.google.com/maps/search/?api=1&query=%f,%f",
                            latitude, longitude
                    );
                    place.put("maps_link", googleMapsLink);
                }
            }

            return jsonObject.toString(4); // Pretty-print JSON
        } catch (Exception e) {
            System.err.println("Error processing JSON response: " + e.getMessage());
            e.printStackTrace();
            return "Error processing JSON response.";
        }
    }
}
