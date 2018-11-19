package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

public class CBans implements server.ICommand {
    /**
     * Show bans from room or server, depending on the user who's using the command.
     * @param chatServerThread Thread created for user by server
     * @param msg (not used)
     */
	@Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        if(chatServerThread.user.getMode()<1) {
            return;
        }
        Room room = chatServerThread.user.getCurrentRoom();
        String bans = "";
        if(room==null) {
            if(chatServerThread.user.getMode() < 3) { // Show server bans only to mode 3 users
                return;
            }
            for(String ban : ChatServer.serverSettings.getBannedAddresses()) {
                bans += ">"+ ban + "\n";
            }
            
        } else {
            for(String ban : room.roomSettings.getBannedAddresses()) {
                bans += ">"+ ban + "\n";
            }
        }
      
        chatServerThread.sendMessageToUser("Banned users:\n"+bans);
    }

    @Override
	public String getInfo() {
		return "/bans - Show banned users";
	}
}