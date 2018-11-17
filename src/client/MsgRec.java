package client;

public class MsgRec extends Thread {
    
	public MsgRec() {
    }
	  
	public void run() {
	    		try {
		            while(true) {
						String msg = ChatClient.communication.receiveMessage();
						if(msg == null) {
							break;
						}
						ChatClient.taMessages.appendText(msg+"\n");
						ChatClient.taMessages.setScrollTop(Double.MAX_VALUE);
		            }
		        } catch (Exception er) {
					System.out.println("MsgRec disconnect");
		        }
    }
}