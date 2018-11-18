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
				//If mode > 0, send also ip address
	        	user.getCommunication().sendMessage("> " + u.getName() +" "+ (user.getMode() > 0 ? u.getSocket().getInetAddress().getHostAddress() : ""));
	        }
        } catch (Exception e) {
        }
	}
	@Override
	public String getInfo() {
		return "/users - Show users in room";
	}
}