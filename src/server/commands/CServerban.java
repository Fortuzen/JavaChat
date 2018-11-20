package server.commands;

import server.ChatServer;
import server.Messages;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CServerban implements server.ICommand {
    /**
     * Ban user from server, requires mode 3.
     * @param chatServerThread Thread created for user by server
     * @param msg User to be banned and reason for ban, seperated with space.
     */
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {

        // Check mode
        if(chatServerThread.user.getMode() < 3) {
            chatServerThread.sendMessageToUser(Messages.permissionDeniedMessage());
            return;
        }
        if(msg.isEmpty()) {
            chatServerThread.sendMessageToUser("Give ip address!");
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
        boolean banned = false;
        for(User u : ChatServer.users) {
            if(u.getSocket().getInetAddress().getHostAddress().equals(address) && u.getMode() < 3) {
                chatServerThread.sendMessageToCurrentRoom(u.getName()+" was banned from the server! Reason: "+reason, "SERVER");
                chatServerThread.sendMessageToUser(u.getName()+" was banned!");
                u.getCurrentRoom().users.remove(u);
                u.setCurrentRoom(null);
                u.setMode(0);

                String ban = address +":"+u.getName()+":"+reason;
                System.out.println(ban);
                ChatServer.serverSettings.getBannedAddresses().add(ban);
                ChatServer.serverSettings.saveBannedUsers();
                banned = true;
                try {
                    u.getSocket().close();
                } catch (Exception e) {
                    //TODO: handle exception
                }             
            }
        }  
        // If no users were found, ban the address
        if(!banned) {
            String ban = address +":"+""+":"+reason;
            System.out.println(ban);
            ChatServer.serverSettings.getBannedAddresses().add(ban);
            ChatServer.serverSettings.saveBannedUsers();
            chatServerThread.sendMessageToUser(address+" was banned!");
        }
    }
    @Override
	public String getInfo() {
		return "/serverban <ipaddress> - Ban address from the server";
	}
}