package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;
// TODO: Crashes client (sometimes)
public class CQuit implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        // Partially from CLeaveroom.java. 
        Room room = chatServerThread.user.getCurrentRoom();
        if(room!=null) {
            chatServerThread.sendMessageToCurrentRoom((chatServerThread.user.getName() + " left the room " + room.roomSettings.name), "SERVER");
            room.users.remove(chatServerThread.user);
            chatServerThread.user.setCurrentRoom(null);
            chatServerThread.user.setMode( (chatServerThread.user.getMode() >= 3) ? chatServerThread.user.getMode() : 0);
        }

        try {
            chatServerThread.user.getSocket().close();
        } catch (Exception e) {
            //TODO: handle exception
        }      
    }
}