package server.commands;

import server.ChatServer;
import server.Messages;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CServerunban implements server.ICommand {
    /**
     * Unban user from server, requires mode 3.
     * @param chatServerThread Thread created for user by server
     * @param msg User to be unbanned
     */
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        // Check mode
        if(chatServerThread.user.getMode() < 3) {
            chatServerThread.sendMessageToUser(Messages.permissionDeniedMessage());
            return;
        }
        //Check if address is banned
        if(!chatServerThread.isBanned(msg)) {
            chatServerThread.sendMessageToUser("Address is not banned!");
            return;
        }
        for(String banned : ChatServer.serverSettings.getBannedAddresses()) {
            String[] splitBanned = banned.split(":");
            if(splitBanned[0].equals(msg)) {
                ChatServer.serverSettings.getBannedAddresses().remove(banned);
                ChatServer.serverSettings.saveBannedUsers();
                chatServerThread.sendMessageToUser(splitBanned[0]+" was unbanned from the server!");
                break;
            }
        }


    }
    @Override
	public String getInfo() {
		return "/serverunban <ip> - Unban ip address";
	}
}