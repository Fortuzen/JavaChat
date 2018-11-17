package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CRoomnotice implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	Room room = user.getCurrentRoom();
        
    	if(!(user.getMode() > 0) || room == null) {
            return;
        }
    	
    	chatServerThread.sendMessageToCurrentRoom("Room notice: " + msg, "SERVER");
    }
    @Override
	public String getInfo() {
		return "";
	}
}