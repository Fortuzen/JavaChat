package server;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class User {
    Socket socket;
    public String name;
    public Room currentRoom;
    int mode;

    PrintWriter output;
    BufferedReader input;

    public User(Socket s) {
        this.socket = s;
        this.name = "Default";
        this.currentRoom = null;
        this.mode = 0;
    }
}