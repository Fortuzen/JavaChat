package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CJoinroom implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        String[] splitMsg = msg.split(" ");
        Room room = ChatServer.rooms.get(splitMsg[0]);
        System.out.println(chatServerThread.user.getName()+ " exec /joinroom");
        // Check if the room exists
        if(room==null) {
            chatServerThread.sendMessageToUser(msg+" room doesn't exist!");
            return;
        }
        // Check for same names
        for(User u : room.users) {
            String name = u.getName();
            if(chatServerThread.user.getName().equals(name)) {
                chatServerThread.sendMessageToUser(chatServerThread.user.getName()+" already taken! Change your username!");
                return;
            }
        }
        // Check if user/username is banned from the room
        // Doesn't work if client connects using localhost
        if(room.isBanned(chatServerThread.user.getSocket().getInetAddress().getHostAddress(), chatServerThread.user.getName())) {            
            chatServerThread.sendMessageToUser("You are banned from this room!");
            return;
        }
        // Check mode
        
        if(splitMsg.length > 1) {
            System.out.println(splitMsg[1]+":"+room.roomSettings.roomModeratorPassword);
            String password = splitMsg[1];
            if(password.equals(room.roomSettings.roomModeratorPassword)) {
                chatServerThread.user.setMode(1);
                chatServerThread.sendMessageToUser("You are room moderator!");
            } else if(password.equals(room.roomSettings.roomAdminPassword)) {
                chatServerThread.user.setMode(2);
                chatServerThread.sendMessageToUser("You are room admin!");
            }
        }

        room.users.add(chatServerThread.user);
        chatServerThread.user.setCurrentRoom(room);
        chatServerThread.sendMessageToCurrentRoom((chatServerThread.user.getName() + " joined room " + room.roomSettings.name), "SERVER");
    }
}