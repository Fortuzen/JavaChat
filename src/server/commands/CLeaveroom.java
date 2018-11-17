package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

public class CLeaveroom implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	Room room = chatServerThread.user.getCurrentRoom();
        if(room==null) {
            return;
        }
        chatServerThread.sendMessageToCurrentRoom((chatServerThread.user.getName() + " left the room " + room.roomSettings.getName()), "SERVER");
        room.users.remove(chatServerThread.user);
        chatServerThread.user.setCurrentRoom(null);
        chatServerThread.user.setMode((chatServerThread.user.getMode() >= 3) ? chatServerThread.user.getMode() : 0);
    }
    @Override
	public String getInfo() {
		return "";
	}
}