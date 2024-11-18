package use_case.chat;

import java.util.List;

public interface ChatOutputBoundary {
    void presentMessage(ChatOutputData outputData);
    void updateMembers(List<String> members);
}
