package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CRoomunmute implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        Room room = chatServerThread.user.getCurrentRoom();
        // Check mode
        if(!(chatServerThread.user.getMode() > 0) || room == null) {
            return;
        }
        
        if(!room.isMuted(msg, msg)) {
            return;
        }
        String username = "";
        for(String muted : room.roomSettings.mutedAddresses) {
            String[] splitMuted = muted.split(":");
            if(splitMuted[0].equals(msg) || splitMuted[1].equals(msg)) {
                room.roomSettings.mutedAddresses.remove(muted);
                room.roomSettings.saveMutedUsers();
                username = splitMuted[1];
                break;
            }
            return;
        }
        chatServerThread.sendMessageToCurrentRoom(username + " was unmuted!", "SERVER");

    }
}