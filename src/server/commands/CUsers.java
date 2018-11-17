package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CUsers implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	Room r = user.getCurrentRoom();

    	if(r==null) {
            return;
        }
    	try {
        	user.getCommunication().sendMessage("** Users currently in room **");
	        for(User u : r.users) {
	        	user.getCommunication().sendMessage("> " + u.getName());
	        }
        } catch (Exception e) {
        }
	}
	@Override
	public String getInfo() {
		return "";
	}
}