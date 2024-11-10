package interface_adapter.chat;

import use_case.chat.ChatOutputBoundary;
import use_case.chat.ChatOutputData;
import java.util.List;

public class ChatPresenter implements ChatOutputBoundary {
    private final ChatViewModel chatViewModel;

    public ChatPresenter(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
    }

    @Override
    public void presentMessage(ChatOutputData response) {
        System.out.println("[ChatPresenter] Presenting message: " + response.getMessage()); // Debug statement
        chatViewModel.addMessage(response.getMessage());
    }

    @Override
    public void updateMembers(List<String> members) {
        chatViewModel.setMembers(members);
    }
}
