package server.commands;

import server.ChatServer;
import server.ICommand;
import server.ChatServer.ChatServerThread;
import server.Room;
import java.util.ArrayList;
import java.util.Map;

public class CHelp implements server.ICommand {
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        String helptext = "All commands:\n";

        for(Map.Entry<String,ICommand> cmd : ChatServer.commands.entrySet()) {
            helptext += cmd.getValue().getInfo()+"\n";
        }
        chatServerThread.sendMessageToUser(helptext);

    }
    @Override
	public String getInfo() {
		return "/help - Prints all commands";
	}
}