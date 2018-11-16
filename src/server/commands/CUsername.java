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
        if(r==null) {
            return;
        }
        try {
            if (msg.equals("SERVER")) {
            	u.getCommunication().sendMessage("This name is not valid");
            } else {
                chatServerThread.sendMessageToCurrentRoom((u.getName() + " changed their name to " + msg), "SERVER");
                u.setName(msg);
            }
        } catch (Exception e) {
        }
    }
}