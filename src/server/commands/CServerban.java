package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CServerban implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {

        // Check mode
        if(chatServerThread.user.getMode() < 3) {
            //Message
            return;
        }
        if(msg.isEmpty()) {
            return;
        }
        //Determine if username or address
        int index = msg.indexOf(" ");
        String reason = "";
        String address = "";
        if(index == -1) {
            address = msg;
        } else {
            address = msg.substring(0,index);
            reason = msg.substring(index+1);
        }
        // Find and ban possible users
        for(User u : ChatServer.users) {
            if(u.getSocket().getInetAddress().getHostAddress().equals(address) && u.getMode() < 3) {
                chatServerThread.sendMessageToCurrentRoom(u.getName()+" was banned from the server! Reason: "+reason, "SERVER");
                chatServerThread.sendMessageToUser("You were banned from the server!");
                u.getCurrentRoom().users.remove(u);
                u.setCurrentRoom(null);
                u.setMode(0);

                String ban = address +":"+u.getName()+":"+reason;
                System.out.println(ban);
                ChatServer.serverSettings.getBannedAddresses().add(ban);
                ChatServer.serverSettings.saveSettings();
                try {
                    u.getSocket().close();
                } catch (Exception e) {
                    //TODO: handle exception
                }
                
            }
        }       
    }
    @Override
	public String getInfo() {
		return "/serverban ipaddress - Ban address from server";
	}
}