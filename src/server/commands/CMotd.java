package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CMotd implements server.ICommand {
    /**
     * Show server message of the day.
     * @param chatServerThread Thread created for user by server
     * @param msg (not used)
     */
	@Override
    public void execute(ChatServerThread chatServerThread, String msg) {
    	ChatServerThread ct = chatServerThread;
   		ct.sendMessageToUser("Message of the day: " + ChatServer.serverSettings.getMotd());
    }
    @Override
	public String getInfo() {
		return "/motd - Show server message of the day";
	}
}