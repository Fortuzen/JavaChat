package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

public class CRules implements server.ICommand {
    /**
     * Shows the rules.
     * @param chatServerThread Thread created for user by server
     * @param msg (not used)
     */
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        String serverRules = ChatServer.serverSettings.getRules().replaceAll(":", "\n");
        
        if(chatServerThread.user.getCurrentRoom() != null) {
            String roomRules = chatServerThread.user.getCurrentRoom().roomSettings.getRules().replaceAll(":", "\n");
            chatServerThread.sendMessageToUser("**Room rules:\n"+roomRules);
        } else {
            chatServerThread.sendMessageToUser("**Server rules:\n"+serverRules);
        }
    }
    @Override
	public String getInfo() {
		return "/rules - Show server's and room's rules";
	}
}