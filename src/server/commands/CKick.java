package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;
// TODO: DOES NOT WORK, kicks the kicker (sometimes)
public class CKick implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	Room room = user.getCurrentRoom();
        
        if(room==null) {
            return;
        }
        if ((user.getMode() < 1)) {
        	try {
        		user.getCommunication().sendMessage("You do not have the permission to use this command.");
        	} catch (Exception ex) {
        		System.out.println("Could not send message.");
        	}
        	return;
        }
    	
        String reason = "";
        String[] splittedMsg = msg.split(" ");
        String kickReciever = splittedMsg[0];
        int index = msg.indexOf(' ');
        reason = msg.substring(index+1);
        
        for(User u : room.users) {
        	if (u.getName().equals(kickReciever)) {
                	chatServerThread.sendMessageToCurrentRoom((u.getName() + " was kicked from the room for " + reason), "SERVER");
        			room.users.remove(u);
        			u.setCurrentRoom(null);
        	}
        }
    }
}