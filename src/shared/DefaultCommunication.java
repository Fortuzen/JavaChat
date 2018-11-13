package shared;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DefaultCommunication implements ICommunication {
    BufferedReader input;
    PrintWriter output;

    public DefaultCommunication(Socket socket) {
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    @Override
    public void sendMessage(String msg) {
        try {
            output.println(msg);
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }

    @Override
    public String receiveMessage() {
        try {
            return input.readLine();
        } catch (Exception e) {
            return e.toString();
        }
        
    }  
}