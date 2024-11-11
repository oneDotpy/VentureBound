package use_case.group;

public interface GroupInputBoundary {
    void createGroup(GroupInputData inputData);
    void joinGroup(GroupInputData inputData);
}
