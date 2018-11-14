package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

//TODO: Make better
public class CJoinroom implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        Room r = ChatServer.rooms.get(msg);
        if(r==null) {
            return;
        }
        r.users.add(chatServerThread.user);
        chatServerThread.user.currentRoom = r;

        chatServerThread.sendMessageToCurrentRoom((chatServerThread.user.name + " joined room " + r.roomSettings.name), "SERVER");

    }
}