package data_access;

import entity.CommonUserFactory;
import entity.Group;
import entity.User;
import entity.UserFactory;
import use_case.signup.SignupUserDataAccessInterface;

public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface {
    UserFactory userFactory;
    InMemoryGroupDataAccessObject inMemoryGroupDataAccessObject;

    public InMemoryUserDataAccessObject() {
        userFactory = new CommonUserFactory();
        inMemoryGroupDataAccessObject = new InMemoryGroupDataAccessObject();
    }

    @Override
    public void save(User user) {

    }

    @Override
    public User get(String username) {
        String password = "password";
        String email = "email";
        String groupID = "groupID";
        Group group = inMemoryGroupDataAccessObject.get(groupID);
        return userFactory.create(username, password, email, group, groupID);
    }
}
