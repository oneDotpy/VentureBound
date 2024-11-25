package interface_adapter.login;

import entity.Message;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.welcome.WelcomeState;
import interface_adapter.welcome.WelcomeViewModel;
import use_case.join_group.JoinGroupOutputData;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

import java.util.function.Consumer;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final WelcomeViewModel welcomeViewModel;
    private final ChatViewModel chatViewModel;
    private final ViewManagerModel viewManagerModel;
    private Consumer<String> onLoginSuccessListener;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          WelcomeViewModel welcomeViewModel,
                          LoginViewModel loginViewModel,
                          ChatViewModel chatViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.welcomeViewModel = welcomeViewModel;
        this.loginViewModel = loginViewModel;
        this.chatViewModel = chatViewModel;
    }

    // Method to set the success listener
    public void setOnLoginSuccessListener(Consumer<String> listener) {
        this.onLoginSuccessListener = listener;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        if (onLoginSuccessListener != null) {
            onLoginSuccessListener.accept(response.getUser().getName()); // Use the Consumer here
        }
        if (response.getGroup() != null) {
            switchToChatView(response);
        } else {
            switchToWelcomeView(response);
        }
    }

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();
    }

    public void switchToChatView(LoginOutputData loginOutputData) {
        // Set Members, Current Users
        ChatState chatState = chatViewModel.getState();
        User user = loginOutputData.getUser();
        chatState.setUser(user);
        chatState.setCurrentUser(user);
        chatState.setMembers(loginOutputData.getGroup().getUsernames());
        for (Message message: loginOutputData.getGroup().getMessages()) {
            System.out.println(message.getContent());
            chatState.addMessage(message.getSender(), message.getContent());
        }
        chatState.setGroupName(loginOutputData.getGroup().getGroupName());

        chatViewModel.setState(chatState);

        // Fire to switch into ChatViewModel
        viewManagerModel.setState(chatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        // Fire to notify chatViewModel to update the members
        chatViewModel.startListeningForUpdates(loginOutputData.getGroup().getGroupID());
        chatViewModel.firePropertyChanged("members");
        chatViewModel.firePropertyChanged("messages");
    }

    @Override
    public void switchToWelcomeView(LoginOutputData loginOutputData) {
        WelcomeState welcomeState = welcomeViewModel.getState();
        welcomeState.setUser(loginOutputData.getUser());
        welcomeViewModel.setState(welcomeState);

        viewManagerModel.setState(welcomeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        welcomeViewModel.firePropertyChanged("username");
    }
}
