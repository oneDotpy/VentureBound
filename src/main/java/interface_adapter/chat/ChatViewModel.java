package interface_adapter.chat;

import interface_adapter.ViewModel;
import java.util.List;

public class ChatViewModel extends ViewModel<ChatState> {

    public ChatViewModel() {
        super("chat");
        setState(new ChatState());
    }

    public void addMessage(String sender, String message) {
        ChatState state = getState();
        state.addMessage(sender, message);
        setState(state);
        System.out.println("[ChatViewModel] New message from " + sender + ": " + message);
        firePropertyChanged("messages"); // Notify listeners
    }

    public void setMembers(List<String> members) {
        ChatState state = getState();
        state.setMembers(members);
        setState(state);
        firePropertyChanged("members"); // Notify listeners
    }

    public void addMember(String member) {
        ChatState state = getState();
        state.addMember(member);
        setState(state);
        firePropertyChanged(); // Notify listeners
    }

    public void simulateMemberMessage(String member, String message) {
        ChatState state = getState();
        state.addMessage(member, message);
        setState(state);
        System.out.println("[ChatViewModel] Simulating message from " + member + ": " + message);
        firePropertyChanged("messages"); // Notify listeners
    }

    public String getCurrentUser() {
        return getState().getCurrentUser();
    }

    public List<String> getMembers() {
        return getState().getMembers();
    }

    public void setCurrentUser(String you) {
        ChatState state = getState();
        state.setCurrentUser(you);
        setState(state);
        firePropertyChanged();
    }
}

