package interface_adapter.chat;

import entity.Message;
import entity.User;
import interface_adapter.ViewModel;
import use_case.chat.RealTimeChatUpdatesUseCase;
import use_case.send_message.SendMessageInputData;
import use_case.send_message.SendMessageInteractor;
import use_case.vacation_bot.VacationBotInputBoundary;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChatViewModel extends ViewModel<ChatState> {

    private RealTimeChatUpdatesUseCase chatUpdatesUseCase;
    private SendMessageInteractor sendMessageInteractor;
    private VacationBotInputBoundary botInteractor; // Bot integration

    public ChatViewModel() {
        super("chat");
        setState(ChatState.getInstance());
    }

    public void setChatUpdatesUseCase(RealTimeChatUpdatesUseCase chatUpdatesUseCase) {
        this.chatUpdatesUseCase = chatUpdatesUseCase;
    }

    public void setSendMessageInteractor(SendMessageInteractor sendMessageInteractor) {
        this.sendMessageInteractor = sendMessageInteractor;
    }

    public void setBotInteractor(VacationBotInputBoundary botInteractor) {
        this.botInteractor = botInteractor; // Inject the bot interactor
    }

    public void startListeningForUpdates(String groupID) {
        ChatState state = getState();

        // Listen for group member updates
        chatUpdatesUseCase.listenForGroupMembers(groupID, new RealTimeChatUpdatesUseCase.GroupMemberUpdateListener() {
            @Override
            public void onGroupMembersUpdated(List<String> members) {
                state.setMembers(members); // Update state directly
                System.out.println("[CVM]" + members);
                firePropertyChanged("members");
            }

            @Override
            public void onError(Exception e) {
                System.err.println("[ChatViewModel] Error updating members: " + e.getMessage());
            }
        });

        // Listen for message updates
        chatUpdatesUseCase.listenForMessages(groupID, new RealTimeChatUpdatesUseCase.MessageUpdateListener() {
            @Override
            public void onMessagesUpdated(Map<String, String> messages) {
                System.out.println("[CVM1]" + messages);

                messages.forEach((key, value) -> {
                    if (value.trim().equalsIgnoreCase("/start")) {
                        System.out.println("Reached here [to start bot]");
                        // Start the bot if not already active
                        if (botInteractor != null && !botInteractor.isBotActive()  && !Objects.equals(key, "Bot")) {
                            if ((state.getMembers().size() == 1 )|| (!Objects.equals(key, state.getCurrentUser().getName()) && state.getMembers().size() > 1)) {
                                System.out.println("Reached here [to start bot] 2");
                                firePropertyChanged("messages");
                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt(); // Restore interrupted status
                                System.err.println("Delay interrupted: " + e.getMessage());
                            }

                            botInteractor.startBot();
                            System.out.println("[CVM] Bot started by: " + key);
                        }
                    } else if (value.trim().equalsIgnoreCase("/stop")) {
                        // Stop the bot if it is active
                        if (botInteractor != null && botInteractor.isBotActive()) {
                            botInteractor.stopBot();
                            firePropertyChanged("messages");
                            System.out.println("[CVM] Bot stopped by: " + key);
                        }
                    } else {
                        // If the bot is active, pass the message to the bot
                        if (botInteractor != null && botInteractor.isBotActive()  && !Objects.equals(key, "Bot")) {
                            firePropertyChanged("messages");
                            botInteractor.handleMessage(key, value);
                        } else {
                            // Otherwise, treat it as a normal chat message
                            System.out.println("[CVM] Reaches This Spot");
                            firePropertyChanged("message");
                            state.addMessage(key, value);
                        }
                    }
                });

                // Clear the messages map to avoid reprocessing
                messages.clear();
                System.out.println("[CVM2]" + messages);

                setState(state); // Update the state in ViewModel
                firePropertyChanged("messages");
            }


            @Override
            public void onError(Exception e) {
                System.err.println("[ChatViewModel] Error updating messages: " + e.getMessage());
            }
        });
    }

    public void sendMessage(String content, User user) {
        ChatState state = getState();
        System.out.println("[CVM3] Receive: " + content + "from" + user.getName());

        // Add message to local state for immediate feedback
        firePropertyChanged("messages"); // Notify listeners about the updated messages

        // Create input data and call the use case
        SendMessageInputData inputData = new SendMessageInputData(user, content);
        System.out.println(inputData.getContent() + inputData.getUser().getName());
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt(); // Restore interrupted status
//            System.err.println("Delay interrupted: " + e.getMessage());
//        }
        sendMessageInteractor.sendMessage(inputData); // Send the message to the database
    }
}