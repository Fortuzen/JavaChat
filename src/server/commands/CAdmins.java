package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CAdmins implements server.ICommand {
    /**
     * Show server and room admins.
     * @param chatServerThread Thread created for user by server
     * @param msg (not used)
     */
	@Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	ChatServerThread ct = chatServerThread;
    	Room r = user.getCurrentRoom();
        if(r==null) {
            return;
        }
        
    	int i = 0;
        for(User u : r.users) {
        	if (u.getMode() == 3) {
                	i++;
                	if (i == 1) {
                		ct.sendMessageToUser("** Server administrators in current room **");
                	}
                	ct.sendMessageToUser(u.getName());
        	}
        }
        
        i = 0;
        for(User u : r.users) {
        	if (u.getMode() == 2) {
                	i++;
                	if (i == 1) {
                		ct.sendMessageToUser("** Room administrators in current room **");
                	}
                	ct.sendMessageToUser(u.getName());
        	}
        }
        
        i = 0;
        for(User u : r.users) {
        	if (u.getMode() == 1) {
                	i++;
                	if (i == 1) {
                		ct.sendMessageToUser("** Room moderators in current room **");
                	}
                	ct.sendMessageToUser(u.getName());
        	}
        }
	}
	@Override
	public String getInfo() {
		return "/admins - Show admins in the room";
	}
}