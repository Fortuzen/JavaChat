package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CPrivmsg implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	Room r = chatServerThread.user.currentRoom;
        if(r==null) {
            return;
        }
        
        String msgbody = "";
        String[] splittedMsg = msg.split(" ");
        String msgReciever = splittedMsg[0];
        int index = msg.indexOf(' ');
        msgbody = msg.substring(index+1);      
        
        for(User u : r.users) {
        	if (u.name.equals(msgReciever)) {
                try {
                	u.communication.sendMessage(chatServerThread.user.name + " whispers: " + msgbody);
                	chatServerThread.user.communication.sendMessage("You whispered to " + u.name + ": " + msgbody);
                } catch (Exception e) {
                }
        	}
        }
    }
}