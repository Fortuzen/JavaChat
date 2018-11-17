package server;

public interface ICommand {
    public void execute(ChatServer.ChatServerThread chatServerThread, String msg);
    public String getInfo();
}