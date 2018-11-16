package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

/**
 * 
 */
public class CBans implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        if(chatServerThread.user.getMode()<1) {
            return;
        }
        Room room = chatServerThread.user.getCurrentRoom();
        String bans = "";
        if(room==null) {
            if(chatServerThread.user.getMode() < 3) {
                return;
            }
            for(String ban : ChatServer.serverSettings.bannedAddresses) {
                bans += ">"+ ban + "\n";
            }
            
        } else {
            for(String ban : room.roomSettings.bannedAddresses) {
                bans += ">"+ ban + "\n";
            }
        }
      
        chatServerThread.sendMessageToMode("Banned users:\n"+bans, 1);
    }
}