package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;

public class CHelp implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        try {
            chatServerThread.user.getCommunication().sendMessage("**********\nHere are the available commands:\n/help - View commands"
            + "\n/joinroom roomname - Join a room\n/leaveroom - Leave the current room\n/privmsg name msg - Send a private message to a user"
            + "\n/quit - Close the chat\n/users - Show users currently in room\n/motd - Show message of the day\n**********"); //TODO: add all cmds, replace with more modular solution
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}