package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CServerdesc implements server.ICommand {
    /**
     * Set server description, requires mode 2.
     * @param chatServerThread Thread created for user by server
     * @param msg New server description
     */
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        
        if ((chatServerThread.user.getMode() < 2)) {
            chatServerThread.sendMessageToUser("You do not have the permission to use this command.");
        	return;
        }
    	ChatServer.serverSettings.setDescription(msg);
    	chatServerThread.sendMessageToUser("Server description is now: " + ChatServer.serverSettings.getDescription());
    }
    @Override
	public String getInfo() {
		return "/serverdesc description - Set server description";
	}
}