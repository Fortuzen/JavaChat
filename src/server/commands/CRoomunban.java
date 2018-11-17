package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CRoomunban implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        Room room = chatServerThread.user.getCurrentRoom();
        // Check mode
        if(!(chatServerThread.user.getMode() > 0) || room == null) {
            return;
        }
        //Check if address/username is banned
        if(!room.isBanned(msg, msg)) {
            return;
        }
        String username = "";
        for(String banned : room.roomSettings.bannedAddresses) {
            String[] splitBanned = banned.split(":");
            if(splitBanned[0].equals(msg) || splitBanned[1].equals(msg)) {
                room.roomSettings.bannedAddresses.remove(banned);
                room.roomSettings.saveBannedUsers();
                username = splitBanned[1];
                break;
            }
            return;
        }
        chatServerThread.sendMessageToCurrentRoom(username + " was unbanned from the room", "SERVER");

    }
}