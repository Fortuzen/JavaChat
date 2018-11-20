package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;
import server.Messages;

public class CServermotd implements server.ICommand {
    /**
     * Change server's message of the day
     * @param chatServerThread Thread created for user by server
     * @param msg text for the message of the day
     */
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        //Check permissions
        if(chatServerThread.user.getMode() < 3) {
            chatServerThread.sendMessageToUser(Messages.permissionDeniedMessage());
            return;
        }
        // Check msg
        if(msg.isEmpty()) {
            chatServerThread.sendMessageToUser("You need to have some text!");
            return;
        }
        // Change motd
        ChatServer.serverSettings.setMotd(msg);
        chatServerThread.sendMessageToUser("MOTD was changed!");
	}
	@Override
	public String getInfo() {
		return "/servermotd <text> - Change server's message of the day";
	}
}