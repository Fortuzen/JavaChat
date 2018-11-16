package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CRoommode implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	ChatServerThread ct = chatServerThread;
    	Room r = user.getCurrentRoom();
        if(r==null) {
            return;
        }
        if ((user.getMode() < 2)) {
       		ct.sendMessageToUser("You do not have the permission to use this command.");
        	return;
        }
        	
        int modelevel = 100;
        String[] splittedMsg = msg.split(" ");
        String modeReciever = splittedMsg[0];
        int index = msg.indexOf(' ');
        try {
        	modelevel = Integer.parseInt(msg.substring(index+1));
        } catch (Exception e) {
       		ct.sendMessageToUser("Not valid number.");
        }
        if ((modelevel == 1) || (modelevel == 0)) {
        	boolean found = false;
            for(User u : r.users) {
            	if (u.getName().equals(modeReciever)) {
                    	u.setMode(modelevel);
                    	found = true;            	
                    	try {
                    		String modelevelString = "error";
                    		switch (modelevel) {
	                    		case 0: modelevelString = "normal user.";
	                    		break;
	                    		case 1: modelevelString = "room moderator.";
	                    		break;
                    		}
                    		user.getCommunication().sendMessage("User " + u.getName() + " is now " + modelevelString);
                    	} catch (Exception ex) {
                    		System.out.println("Could not send message.");
                    	}
            	}
            }
            if (!(found)) {
           		ct.sendMessageToUser("Could not find that user.");
            }
        } else {
       		ct.sendMessageToUser("Not valid number.");
        }
    }
}