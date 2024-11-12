package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatGPTApp extends JFrame {
    private JTextField activitiesField;
    private JTextField locationsField;
    private JTextArea outputArea;
    private JButton sendButton;
    private OpenAIChatGPT openAIChatGPT;
    private static final Logger logger = Logger.getLogger(ChatGPTApp.class.getName());

    public ChatGPTApp() throws IOException {
        openAIChatGPT = new OpenAIChatGPT();

        setTitle("Vacation Recommendations");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Activities:"));
        activitiesField = new JTextField();
        inputPanel.add(activitiesField);
        inputPanel.add(new JLabel("Locations:"));
        locationsField = new JTextField();
        inputPanel.add(locationsField);
        sendButton = new JButton("Get Recommendations");
        inputPanel.add(sendButton);

        // Output area
        outputArea = new JTextArea(10, 40);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(outputScrollPane, BorderLayout.CENTER);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String activities = activitiesField.getText().trim();
                String locations = locationsField.getText().trim();
                if (!activities.isEmpty() && !locations.isEmpty()) {
                    try {
                        String jsonResponse = openAIChatGPT.getVacationRecommendations(activities, locations);
                        outputArea.setText(jsonResponse);
                        System.out.println("Received JSON Response: " + jsonResponse);
                    } catch (IOException ex) {
                        outputArea.setText("Error: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                } else {
                    outputArea.setText("Please enter both activities and locations.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatGPTApp app = null;
            try {
                app = new ChatGPTApp();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            app.setVisible(true);
        });
    }
}
