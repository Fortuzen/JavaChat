package client;

import java.net.Socket;
import java.util.Scanner;

import shared.ICommunication;
import shared.DefaultCommunication;


public class ChatClient {
	public static ICommunication communication = null;
	
	public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
		String ip = "localhost";
		int port = 8000;
		Socket socket;
//		System.out.print("Port: ");
//		port = scanner.nextInt();
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to " + ip + " " + port);
            communication = new DefaultCommunication(socket);
            MsgRec rec = new MsgRec();
            rec.start();

        } catch (Exception error) {
            System.out.println("Could not connect to " + ip + " " + port);
        }

        
        System.out.print("Insert nickname: ");
        String nick = scanner.next();
        communication.sendMessage(nick);
        
        String msg = "";
        while (!(msg.contains("quit"))) {
        	msg = scanner.nextLine();
            communication.sendMessage(msg);
        }
        
        scanner.close();

    }
}