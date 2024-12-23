package interface_adapter.login;

import entity.Group;

public class LoginState {
    private String username;
    private String password;
    private String loginError;
    private boolean userHasGroup;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginError() {
        return loginError;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }

    public boolean isUserHasGroup() {
        return userHasGroup;
    }

    public void setUserHasGroup(boolean userHasGroup) {
        this.userHasGroup = userHasGroup;
    }

}
