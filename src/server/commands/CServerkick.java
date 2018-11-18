package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;
public class CServerkick implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	ChatServerThread ct = chatServerThread;
    	Room room = user.getCurrentRoom();
        
        if(room==null) {
            return;
        }
        if ((user.getMode() < 3)) {
       		ct.sendMessageToUser("You do not have the permission to use this command.");
        	return;
        }
    	
        String reason = "";
        String[] splittedMsg = msg.split(" ");
        String kickReciever = splittedMsg[0];
        int index = msg.indexOf(' ');
        reason = msg.substring(index+1);
        
        for (int i = 0; i < room.users.size(); i++) {
        	User u = room.users.get(i);
        	if (u.getName().equals(kickReciever)) {
            	chatServerThread.sendMessageToCurrentRoom((u.getName() + " was kicked from the server for " + reason), "SERVER");
    			room.users.remove(u);
    			u.setCurrentRoom(null);
    	        try {
    	            u.getSocket().close();
    	            ChatServer.users.remove(u);
    	        } catch (Exception e) {
    	            //TODO: handle exception
    	        }      
        	}
        }
	}
	@Override
	public String getInfo() {
		return "/serverkick user reason - Kick user from server";
	}
}