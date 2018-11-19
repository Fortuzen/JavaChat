package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CRoomnotice implements server.ICommand {
    /**
     * Sends a message to every user in room, requires mode 1.
     * @param chatServerThread Thread created for user by server
     * @param msg Notice
     */
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
		return "/roomnotice notice - Send a message to everyone in room";
	}
}