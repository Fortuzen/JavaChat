package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

/**
 * Change server password, requires mode 3.
 * @param chatServerThread Thread created for user by server
 * @param msg New password
 */
public class CServerpassword implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {

        if(chatServerThread.user.getMode() < 3) {
            chatServerThread.sendMessageToUser("You do not have the permission to use this command.");
            return;
        }
        msg = msg.trim();
        ChatServer.serverSettings.setServerPassword(msg);
        chatServerThread.sendMessageToModeServer("Server password is now: "+msg, 3);
    }
    @Override
	public String getInfo() {
		return "/serverpassword password - Change server password";
	}
}