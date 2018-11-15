package shared;

public interface ICommunication {
    public void sendMessage(String msg) throws Exception;
    public String receiveMessage() throws Exception;
}