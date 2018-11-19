package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

//TODO: Check if name taken (in room)
public class CUsername implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	Room r = chatServerThread.user.getCurrentRoom();
    	User u = chatServerThread.user;
        System.out.println(msg.length());
        try {
            if (msg.equals("SERVER")) {
                u.getCommunication().sendMessage("This name is not valid");
            } else if(msg.trim().equals("")) {
                chatServerThread.sendMessageToUser("Your username: "+u.getName());
            } else if(chatServerThread.isIllegalName(msg)) {
                chatServerThread.sendMessageToUser("Invalid characters!");
            } else {               
                if(r != null) {
                    if(r.getUser(msg) == null) {
                        chatServerThread.sendMessageToCurrentRoom((u.getName() + " changed their name to " + msg), "SERVER");
                        u.setName(msg);
                    } else {
                        chatServerThread.sendMessageToUser("Name already taken!");
                    }
                } else {
                    chatServerThread.sendMessageToUser(u.getName() + " changed their name to " + msg);
                    u.setName(msg);
                }               
            }
        } catch (Exception e) {
        }
    }
    @Override
	public String getInfo() {
		return "/username [name] - Show your username/Change your username";
	}
}