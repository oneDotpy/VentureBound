package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.signup.SignupState;
import interface_adapter.welcome.WelcomeState;
import interface_adapter.welcome.WelcomeViewModel;
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

        if (response.isUseCaseFailed()) {
            ChatState chatState = chatViewModel.getState();
            chatState.setCurrentUser(response.getUser());
            chatViewModel.setState(chatState);
            chatViewModel.firePropertyChanged();

            viewManagerModel.setState(chatViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
        }

        // Set the login state whether the user has a group
        LoginState loginState = loginViewModel.getState();
        loginState.setUserHasGroup(response.getUser().getGroup() != null);

        WelcomeState welcomeState = welcomeViewModel.getState();
        welcomeState.setUser(response.getUser());
        System.out.println("Username: " + response.getUser().getName());
        welcomeViewModel.setState(welcomeState);
        welcomeViewModel.firePropertyChanged("username");

        viewManagerModel.setState(welcomeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        // TODO: Implement the chat functionality here

        ChatState chatState = chatViewModel.getState();
        chatState.setCurrentUser(response.getUser());




    }

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();
    }
}
