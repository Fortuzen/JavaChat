package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CRoompassword implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	ChatServerThread ct = chatServerThread;
    	Room room = user.getCurrentRoom();
        
        if(room==null) {
            return;
        }
        if ((user.getMode() < 2)) {
       		ct.sendMessageToUser("You do not have the permission to use this command.");
        	return;
        }
    	room.roomSettings.roomPassword = msg;
    		ct.sendMessageToUser("Room password is now " + room.roomSettings.roomPassword);
    }
}