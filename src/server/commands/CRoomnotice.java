package server.commands;

import server.ChatServer;
import server.Messages;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CRoomnotice implements server.ICommand {
    /**
     * Sends a notification to every user in the room, requires mode 1.
     * @param chatServerThread Thread created for user by server
     * @param msg Notice
     */
	@Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	Room room = user.getCurrentRoom();
        
    	if(!(user.getMode() > 0)) {
            chatServerThread.sendMessageToUser(Messages.permissionDeniedMessage());
            return;
        }
        if(room == null) {
            chatServerThread.sendMessageToUser(Messages.notInRoomMessage());
        }
    	
    	chatServerThread.sendMessageToCurrentRoom("Room notification: " + msg, "SERVER");
    }
    @Override
	public String getInfo() {
		return "/roomnotice <message> - Send a notification to everyone in the room";
	}
}