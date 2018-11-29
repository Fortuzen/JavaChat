package server.commands;

import server.ChatServer;
import server.Messages;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CServerdesc implements server.ICommand {
    /**
     * Set server description, requires mode 3.
     * @param chatServerThread Thread created for user by server
     * @param msg New server description
     */
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        
        if ((chatServerThread.user.getMode() < 3)) {
            chatServerThread.sendMessageToUser(Messages.permissionDeniedMessage());
        	return;
        }
    	ChatServer.serverSettings.setDescription(msg);
    	chatServerThread.sendMessageToUser("Server description is now: " + ChatServer.serverSettings.getDescription());
    }
    @Override
	public String getInfo() {
		return "/serverdesc <description> - Set server's description";
	}
}