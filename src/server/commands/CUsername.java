package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

//TODO: Check if name taken (in room)
//TODO: return your name if no parameters
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
            } else {
                
                if(r != null) {
                    chatServerThread.sendMessageToCurrentRoom((u.getName() + " changed their name to " + msg), "SERVER");
                } else {
                    chatServerThread.sendMessageToUser(u.getName() + " changed their name to " + msg);
                }
                u.setName(msg);
            }
        } catch (Exception e) {
        }
    }
}