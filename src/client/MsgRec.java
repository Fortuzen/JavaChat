package client;

import javafx.application.Platform;

public class MsgRec extends Thread {
    
	public MsgRec() {
    }
	  
	public void run() {
	    		try {
		            while(true) {
						String msg = ChatClient.communication.receiveMessage();
						if(msg == null) {
							continue;
						}

						// TODO: Is this good solution?
						/*
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								// update your JavaFX controls here
								ChatClient.taMessages.appendText(msg+"\n");
								ChatClient.taMessages.setScrollTop(Double.MAX_VALUE);
							}
						});*/

						Platform.runLater(() -> { 	ChatClient.taMessages.appendText(msg+"\n");
													ChatClient.taMessages.setScrollTop(Double.MAX_VALUE);});

		            }
		        } catch (Exception er) {
					System.out.println("MsgRec disconnect");
		        }
    }
}