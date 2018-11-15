package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

public class CHelp implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        try {
            chatServerThread.user.communication.sendMessage("**********\nHere are the commands you can use:\n/help - View commands"
            + "\n/joinroom roomname - Join a room\n/leaveroom - Leave a room\n/privmsg name msg - Send a private message to a user\n**********"); //TODO: add all cmds
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}