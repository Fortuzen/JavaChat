package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CUsername implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	Room r = chatServerThread.user.currentRoom;
    	User u = chatServerThread.user;
        if(r==null) {
            return;
        }
        try {
            if (msg.equals("SERVER")) {
            	u.communication.sendMessage("This name is not valid");
            } else {
                chatServerThread.sendMessageToCurrentRoom((u.name + " changed their name to " + msg), "SERVER");
                u.name = msg;
            }
        } catch (Exception e) {
        }
    }
}