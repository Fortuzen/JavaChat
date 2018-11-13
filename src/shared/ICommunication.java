package shared;

public interface ICommunication {
    public void sendMessage(String msg);
    public String receiveMessage();
}