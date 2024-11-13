package interface_adapter.chat;

import interface_adapter.ViewModel;

public class ChatViewModel extends ViewModel<ChatState> {
    public ChatViewModel() {
        super("chat");
        // Use the singleton ChatState instance
        setState(ChatState.getInstance());
    }
}
