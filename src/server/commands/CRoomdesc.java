package server.commands;

import server.ChatServer;
import server.Messages;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CRoomdesc implements server.ICommand {
    /**
     * Set room description, requires mode 2.
     * @param chatServerThread Thread created for user by server
     * @param msg New room description
     */
	@Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	ChatServerThread ct = chatServerThread;
    	Room room = user.getCurrentRoom();
        if ((user.getMode() < 2)) {
            ct.sendMessageToUser(Messages.permissionDeniedMessage());
        	return;
        }
             
        if(room==null) {
            ct.sendMessageToUser(Messages.notInRoomMessage());
            return;
        }

    	room.roomSettings.setDescription(msg);
    	ct.sendMessageToUser("Room description is now: " + room.roomSettings.getDescription());
    }
    @Override
	public String getInfo() {
		return "/roomdesc <description> - Set room's description";
	}
}