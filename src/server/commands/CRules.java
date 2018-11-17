package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

public class CRules implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        String serverRules = ChatServer.serverSettings.rules.replaceAll(":", "\n");
        chatServerThread.sendMessageToUser("**Server rules:\n"+serverRules);
        if(chatServerThread.user.getCurrentRoom() != null) {
            String roomRules = chatServerThread.user.getCurrentRoom().roomSettings.rules.replaceAll(":", "\n");
            chatServerThread.sendMessageToUser("**Room rules:\n"+roomRules);
        }
    }
}