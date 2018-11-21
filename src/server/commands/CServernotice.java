package server.commands;

import server.ChatServer;
import server.Messages;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CServernotice implements server.ICommand {
    /**
     * Send a message to every user on server, requires mode 3.
     * @param chatServerThread Thread created for user by server
     * @param msg Notice
     */
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
        
    	if(user.getMode() < 3) {
            chatServerThread.sendMessageToUser(Messages.permissionDeniedMessage());
            return;
        }

        chatServerThread.sendMessageToAll("** SERVER NOTIFICATION: "+msg+"**");
    	
    }
    @Override
	public String getInfo() {
		return "/servernotice <msg> - Send a server message to all users";
	}
}