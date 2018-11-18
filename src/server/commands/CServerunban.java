package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CServerunban implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        // Check mode
        if(chatServerThread.user.getMode() < 3) {
            // Message
            return;
        }
        //Check if address is banned
        if(!chatServerThread.isBanned(msg)) {
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
		return "/serverunban (ip) - Remove server ban";
	}
}