package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;

public class CJoinroom implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        Room room = ChatServer.rooms.get(msg);
        // Check if the room exists
        if(room==null) {
            chatServerThread.sendMessageToUser(msg+" room doesn't exist!");
            return;
        }
        // Check for same names
        for(User u : room.users) {
            String name = u.name;
            if(chatServerThread.user.name.equals(name)) {
                chatServerThread.sendMessageToUser(chatServerThread.user.name+" already taken! Change your username!");
                return;
            }
        }
        // Check if user is banned from the room
        // Doesn't work if client connects using localhost
        if(room.roomSettings.bannedAddresses.contains(chatServerThread.user.socket.getInetAddress().getHostAddress())) {
            chatServerThread.sendMessageToUser("You are banned from this room!");
            return;
        }
        // Check mode
        String[] splitMsg = msg.split(" ");
        if(splitMsg.length > 1) {
            String password = splitMsg[1];
            if(password.equals(room.roomSettings.roomModeratorPassword)) {
                chatServerThread.user.mode = 1;
            } else if(password.equals(room.roomSettings.roomModeratorPassword)) {
                chatServerThread.user.mode = 2;
            }
        }

        room.users.add(chatServerThread.user);
        chatServerThread.user.currentRoom = room;
        chatServerThread.sendMessageToCurrentRoom((chatServerThread.user.name + " joined room " + room.roomSettings.name), "SERVER");
    }
}