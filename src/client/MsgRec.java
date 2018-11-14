package client;

import java.sql.Timestamp;

public class MsgRec extends Thread {
    public MsgRec() {
    }

    public void run() {
        try {
            while(true) {
            	String msg = ChatClient.communication.receiveMessage();
                ChatClient.taMessages.appendText("[" + new Timestamp(System.currentTimeMillis()).toLocalDateTime() + "] " + msg+"\n"); //TODO: Timestamp too precise
                ChatClient.taMessages.setScrollTop(Double.MAX_VALUE);
            }
            
        } catch (Exception er) {
        	System.out.println("MsgRec error");
        }
    }
}