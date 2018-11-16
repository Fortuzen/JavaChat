package server;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import shared.ICommunication;
import shared.DefaultCommunication;

public class User {
    public Socket socket;
    public String name;
    public Room currentRoom;
    public ICommunication communication;
    public int mode; // 0=user, 1=room moderator, 2=room admin, 3=server admin

    public User(Socket s) {
        this.socket = s;
        this.name = "Default";
        this.currentRoom = null;
        this.communication = new DefaultCommunication(s);
        this.mode = 0;      
    }
}