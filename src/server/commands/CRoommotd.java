package server.commands;

import server.ChatServer;
import server.ChatServer.ChatServerThread;
import server.Room;
import server.User;
import server.Messages;

public class CRoommotd implements server.ICommand {
    /**
     * Change room's message of the day
     * @param chatServerThread Thread created for user by server
     * @param msg text for the message of the day
     */
    @Override
    public void execute(ChatServerThread chatServerThread, String msg) {
        //Check permissions
        if(chatServerThread.user.getMode() < 2) {
            chatServerThread.sendMessageToUser(Messages.permissionDeniedMessage());
            return;
        }
        // Check room
        if(chatServerThread.user.getCurrentRoom() == null) {
            chatServerThread.sendMessageToUser(Messages.notInRoomMessage());
            return;
        }
        // Check msg
        if(msg.isEmpty()) {
            chatServerThread.sendMessageToUser("You need to have some text!");
            return;
        }
        // Change motd
        chatServerThread.user.getCurrentRoom().roomSettings.setMotd(msg);
        chatServerThread.sendMessageToUser("MOTD was changed!");
	}
	@Override
	public String getInfo() {
		return "/roommotd <text> - Change room's message of the day";
	}
}