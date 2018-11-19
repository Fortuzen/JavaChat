package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

public class CDesc implements server.ICommand {
    /**
     * Shows room's and server's description.
     * @param chatServerThread Thread created for user by server
     * @param msg (not used)
     */
	@Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        Room room = chatServerThread.user.getCurrentRoom();
        if(room != null) {
            chatServerThread.sendMessageToUser("Room's description:\n" + room.roomSettings.getDescription());
        } else {
            chatServerThread.sendMessageToUser("Server's description:\n" + ChatServer.serverSettings.getDescription());
        }
    }
    @Override
	public String getInfo() {
		return "/desc - Get room/server description";
	}
}