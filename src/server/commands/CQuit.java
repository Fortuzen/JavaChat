package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CQuit implements server.ICommand {
    /**
     * Disconnect from server.
     * @param chatServerThread Thread created for user by server
     * @param msg (not used)
     */
	@Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        // Partially from CLeaveroom.java. 
        Room room = chatServerThread.user.getCurrentRoom();
        if(room!=null) {
            chatServerThread.sendMessageToCurrentRoom((chatServerThread.user.getName() + " left the room " + room.roomSettings.getName()), "SERVER");
            room.users.remove(chatServerThread.user);
            chatServerThread.user.setCurrentRoom(null);
            chatServerThread.user.setMode( (chatServerThread.user.getMode() >= 3) ? chatServerThread.user.getMode() : 0);
        }

        try {
            chatServerThread.sendMessageToUser("You left the server!");
            chatServerThread.user.getSocket().close();
        } catch (Exception e) {
            //TODO: handle exception
        }      
    }
    @Override
	public String getInfo() {
		return "/quit - Quit";
	}
}