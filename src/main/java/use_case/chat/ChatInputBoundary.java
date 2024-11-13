package use_case.chat;

import java.util.List;

public interface ChatInputBoundary {
    void sendMessage(ChatInputData inputData);

    List<String> getMembers();
}
