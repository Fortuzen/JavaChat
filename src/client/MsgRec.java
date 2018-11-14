package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class MsgRec extends Thread {
    Socket s;
    public MsgRec(Socket socket) {
        this.s = socket;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader( s.getInputStream() ) );
            while(true) {
                String msg = reader.readLine();
                System.out.println(msg);
//              ChatClient.taMessages.appendText(msg+"\n");
//              ChatClient.taMessages.setScrollTop(Double.MAX_VALUE);
            }
            
        } catch (Exception er) {

        }
    }
}