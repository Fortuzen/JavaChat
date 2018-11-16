package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CAdmins implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	Room r = user.getCurrentRoom();
        if(r==null) {
            return;
        }
        
    	int i = 0;
        for(User u : r.users) {
        	if (u.getMode() == 3) {
                try {
                	i++;
                	if (i == 1) {
                		user.getCommunication().sendMessage("** Server administrators in current room **");
                	}
                	user.getCommunication().sendMessage(u.getName());
                } catch (Exception e) {
            		System.out.println("Could not send message");
                }
        	}
        }
        
        i = 0;
        for(User u : r.users) {
        	if (u.getMode() == 2) {
                try {
                	i++;
                	if (i == 1) {
                		user.getCommunication().sendMessage("** Room administrators in current room **");
                	}
                	user.getCommunication().sendMessage(u.getName());
                } catch (Exception e) {
            		System.out.println("Could not send message");
                }
        	}
        }
        
        i = 0;
        for(User u : r.users) {
        	if (u.getMode() == 1) {
                try {
                	i++;
                	if (i == 1) {
                		user.getCommunication().sendMessage("** Room moderators in current room **");
                	}
                	user.getCommunication().sendMessage(u.getName());
                } catch (Exception e) {
            		System.out.println("Could not send message");
                }
        	}
        }
        

    }
}