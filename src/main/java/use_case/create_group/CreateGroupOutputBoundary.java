package use_case.create_group;

public interface CreateGroupOutputBoundary {
    public void prepareChatView(CreateGroupOutputData createGroupOutputData);

    public void prepareFailView(String message);
}
