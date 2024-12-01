package data_access;

import app.PasswordEncryption;
import com.google.cloud.Timestamp;
import entity.CommonUserFactory;
import entity.Group;
import entity.User;
import entity.UserFactory;
import use_case.login.LoginUserDataAccessInterface;
import use_case.send_message.SendMessageUserDataAccessInterface;
import use_case.create_group.CreateGroupUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;


public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        CreateGroupUserDataAccessInterface,
        SendMessageUserDataAccessInterface {
    UserFactory userFactory;
    InMemoryGroupDataAccessObject inMemoryGroupDataAccessObject;
    List<User> users = new ArrayList<>();

    public InMemoryUserDataAccessObject() {
        userFactory = new CommonUserFactory();
        inMemoryGroupDataAccessObject = new InMemoryGroupDataAccessObject();

        Group group = inMemoryGroupDataAccessObject.get("groupID");

        PasswordEncryption passwordEncryption = new PasswordEncryption();
        String password = passwordEncryption.execute("password");

        User user = userFactory.create("user", password, "email", group, "groupID");
        users.add(user);
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public User get(String username) {
        for (User user : users) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }



    @Override
    public void setGroupID(String GroupID, String username) {
        for (User user1 : users) {
            if (username.equals(user1.getName())){
                User new_user = userFactory.create(user1.getName(), user1.getPassword(), user1.getEmail(), inMemoryGroupDataAccessObject.get(GroupID), GroupID);
                users.add(new_user);
                users.remove(user1);
                return;
            }
        }
    }

    @Override
    public Timestamp getTimestamp(String username) {
        return Timestamp.now();
    }

}
