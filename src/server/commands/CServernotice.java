package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CServernotice implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
        
    	if(user.getMode() < 3) {
            return;
        }

        chatServerThread.sendMessageToAll("**"+msg+"**");
    	
    }
    @Override
	public String getInfo() {
		return "/servernotice <msg> - Send a server message to all users";
	}
}