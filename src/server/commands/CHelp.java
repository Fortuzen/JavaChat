package server.commands;

import server.ChatServer;
import server.ICommand;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;
import java.util.Map;

public class CHelp implements server.ICommand {
    /**
     * Show available commands.
     * @param chatServerThread Thread created for user by server
     * @param msg (not used)
     */
	@Override
    public void execute(ChatServerThread chatServerThread, String msg) {      
        chatServerThread.sendMessageToUser("All commands (<> compulsory, [] optional, | or):");
        // IRC/Quakenet way?
        for(Map.Entry<String,ICommand> cmd : ChatServer.commands.entrySet()) {
            String helptext = cmd.getValue().getInfo();
            chatServerThread.sendMessageToUser(helptext);
        }
        

    }
    @Override
	public String getInfo() {
		return "/help - Print all commands";
	}
}