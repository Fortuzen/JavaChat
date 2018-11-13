package server;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import shared.ICommunication;
import shared.DefaultCommunication;

public class User {
    Socket socket;
    public String name;
    public Room currentRoom;
    public ICommunication communication;
    int mode;

    public User(Socket s) {
        this.socket = s;
        this.name = "Default";
        this.currentRoom = null;
        this.communication = new DefaultCommunication(s);
        this.mode = 0;
        
        
    }
}