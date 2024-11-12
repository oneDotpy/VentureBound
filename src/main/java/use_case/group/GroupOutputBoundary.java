package use_case.group;

public interface GroupOutputBoundary {
    void presentCreateGroupResult(GroupOutputData outputData);
    void presentJoinGroupResult(GroupOutputData outputData);
}
