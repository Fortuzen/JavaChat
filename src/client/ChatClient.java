package client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import shared.ICommunication;
import shared.DefaultCommunication;


public class ChatClient {
	public static ICommunication communication = null;
	
	public static void main(String[] args) {
		String ip = "localhost";
		int port = 8000;
		Socket socket;
		
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to " + ip + " " + port);
            communication = new DefaultCommunication(socket);
            MsgRec rec = new MsgRec();
            rec.start();

        } catch (Exception error) {
            System.out.println("Could not connect to " + ip + " " + port);
        }

        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Insert nickname: ");
        String nick = scanner.next();
        communication.sendMessage(nick);
        
        String msg = "";
        while (!(msg.contains("quit"))) {
            System.out.print(nick + ": ");
        	msg = scanner.next();
            communication.sendMessage(msg);
        }
        
        scanner.close();

    }
}