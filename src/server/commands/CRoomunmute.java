package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CRoomunmute implements server.ICommand {
    /**
     * Remove user's room mute, requires mode 1.
     * @param chatServerThread Thread created for user by server
     * @param msg address:username
     */
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
        for(String muted : room.roomSettings.getMutedAddresses()) {
            String[] splitMuted = muted.split(":");
            if(splitMuted[0].equals(msg) || splitMuted[1].equals(msg)) {
                room.roomSettings.getMutedAddresses().remove(muted);
                room.roomSettings.saveMutedUsers();
                username = splitMuted[1];
                break;
            }
            return;
        }
        chatServerThread.sendMessageToCurrentRoom(username + " was unmuted!", "SERVER");

    }
    @Override
	public String getInfo() {
		return "/roomunmute <username|ip> - Unmute user in the room";
	}
}