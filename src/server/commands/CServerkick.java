package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;
import server.Messages;

import java.util.ArrayList;
public class CServerkick implements server.ICommand {
    /**
     * Kick user from server, requires mode 3.
     * @param chatServerThread Thread created for user by server
     * @param msg User and reason for kick, seperated with space
     */
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	ChatServerThread ct = chatServerThread;
    	Room room = user.getCurrentRoom();
        if ((user.getMode() < 3)) {
       		ct.sendMessageToUser(Messages.permissionDeniedMessage());
        	return;
        }        
        if(room==null) {
			chatServerThread.sendMessageToUser(Messages.notInRoomMessage());
            return;
        }
   	
        String reason = "";
        String[] splittedMsg = msg.split(" ");
        String kickReciever = splittedMsg[0];
		int index = msg.indexOf(' ');
		if(splittedMsg.length > 1) {
			reason = msg.substring(index+1);
		}
        
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
		return "/serverkick <user> [reason] - Kick user from the server";
	}
}