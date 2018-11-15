package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

public class CLeaveroom implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	Room r = chatServerThread.user.currentRoom;
        if(r==null) {
            return;
        }
        chatServerThread.sendMessageToCurrentRoom((chatServerThread.user.name + " left the room " + r.roomSettings.name), "SERVER");
        r.users.remove(chatServerThread.user);
        chatServerThread.user.currentRoom = null;
    }
}