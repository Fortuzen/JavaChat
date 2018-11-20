package server.commands;

import server.ChatServer;
import server.Messages;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

/**
 * 
 */
public class CMutes implements server.ICommand {
    /**
     * Show muted users in room, requires mode 1.
     * @param chatServerThread Thread created for user by server
     * @param msg (not used)
     */
	@Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        if(chatServerThread.user.getMode()<1) {
            chatServerThread.sendMessageToUser(Messages.permissionDeniedMessage());
            return;
        }
        Room room = chatServerThread.user.getCurrentRoom();
        if(room==null) {
            chatServerThread.sendMessageToUser(Messages.notInRoomMessage());
            return;             
        }
        String mutes = "";
        for(String mute : room.roomSettings.getMutedAddresses()) {
            mutes += ">"+ mute + "\n";
        }
            
        chatServerThread.sendMessageToUser("Muted users:\n"+mutes);
    }
    @Override
	public String getInfo() {
		return "/mutes - Show muted users in room";
	}
}