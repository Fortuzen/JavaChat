package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

/**
 * 
 */
public class CMutes implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        if(chatServerThread.user.getMode()<1) {
            return;
        }
        Room room = chatServerThread.user.getCurrentRoom();
        if(room==null) {
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