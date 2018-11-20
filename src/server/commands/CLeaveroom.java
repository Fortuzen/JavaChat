package server.commands;

import server.ChatServer;
import server.Messages;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

public class CLeaveroom implements server.ICommand {
    /**
     * Leave current room.
     * @param chatServerThread Thread created for user by server
     * @param msg (not used)
     */
	@Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	Room room = chatServerThread.user.getCurrentRoom();
        if(room==null) {
            chatServerThread.sendMessageToUser(Messages.notInRoomMessage());
            return;
        }
        chatServerThread.sendMessageToCurrentRoom((chatServerThread.user.getName() + " left the room " + room.roomSettings.getName()), "SERVER");
        room.users.remove(chatServerThread.user);
        chatServerThread.user.setCurrentRoom(null);
        chatServerThread.user.setMode((chatServerThread.user.getMode() >= 3) ? chatServerThread.user.getMode() : 0);
    }
    @Override
	public String getInfo() {
		return "/leaveroom - Leave room";
	}
}