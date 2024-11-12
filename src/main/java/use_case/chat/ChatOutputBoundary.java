package use_case.chat;

import java.util.List;

public interface ChatOutputBoundary {
    void presentMessage(ChatOutputData response);
    void updateMembers(List<String> members);
}
