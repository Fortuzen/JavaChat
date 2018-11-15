package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

public class CLeaveroom implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	Room room = chatServerThread.user.currentRoom;
        if(room==null) {
            return;
        }
        chatServerThread.sendMessageToCurrentRoom((chatServerThread.user.name + " left the room " + room.roomSettings.name), "SERVER");
        room.users.remove(chatServerThread.user);
        chatServerThread.user.currentRoom = null;
        chatServerThread.user.mode = (chatServerThread.user.mode >= 3) ? chatServerThread.user.mode : 0;
    }
}