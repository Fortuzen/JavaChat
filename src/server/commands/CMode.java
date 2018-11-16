package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CMode implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	Room r = user.getCurrentRoom();
        if(r==null) {
            return;
        }
        if (!(user.getMode() == 3)) {
        	try {
        		user.getCommunication().sendMessage("You do not have the permission to use this command.");
        	} catch (Exception ex) {
        		System.out.println("Could not send message.");
        	}
        	return;
        }
        	
        int modelevel = 100;
        String[] splittedMsg = msg.split(" ");
        String modeReciever = splittedMsg[0];
        int index = msg.indexOf(' ');
        try {
        	modelevel = Integer.parseInt(msg.substring(index+1));
        } catch (Exception e) {
        	try {
        		user.getCommunication().sendMessage("Not valid number.");
        	} catch (Exception ex) {
        		System.out.println("Could not send message.");
        	}
        }
        if ((modelevel == 1) || (modelevel == 2) || (modelevel == 3) || (modelevel == 0)) {
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
	                    		case 2: modelevelString = "room administrator.";
	                    		break;
	                    		case 3: modelevelString = "server administrator.";
	                    		break;
                    		}
                    		user.getCommunication().sendMessage("User " + u.getName() + " is now " + modelevelString);
                    	} catch (Exception ex) {
                    		System.out.println("Could not send message.");
                    	}
            	}
            }
            if (!(found)) {
            	try {
            		user.getCommunication().sendMessage("Could not find that user.");
            	} catch (Exception ex) {
            		System.out.println("Could not send message.");
            	}
            }
        } else {
        	try {
        		user.getCommunication().sendMessage("Not valid number.");
        	} catch (Exception e) {
        		System.out.println("Could not send message.");
        	}
        }
    }
}