package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

//TODO: ATM requires roomname to leave
public class CLeaveroom implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        Room r = ChatServer.rooms.get(msg);
        if(r==null) {
            return;
        }
        r.users.remove(chatServerThread.user);
        chatServerThread.sendMessageToCurrentRoom((chatServerThread.user.name + " left the room " + r.roomSettings.name), "SERVER");
        chatServerThread.user.currentRoom = null;
    }
}