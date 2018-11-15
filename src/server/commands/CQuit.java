package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;

import java.util.ArrayList;
// Depends on CLeaveroom
public class CQuit implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        User user = chatServerThread.user;
        ChatServer.commands.get("/leaveroom").execute(chatServerThread, msg);
        try {
            user.socket.close();
        } catch (Exception e) {
            //TODO: handle exception
        }      
    }
}