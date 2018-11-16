package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CMotd implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	User user = chatServerThread.user;
    	ChatServerThread ct = chatServerThread;
    	Room r = user.getCurrentRoom();

    	if(r==null) {
            return;
        }
    		ct.sendMessageToUser("Message of the day: " + ChatServer.serverSettings.motd);
    }
}