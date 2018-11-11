package server;
import java.util.HashMap;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import server.commands.*;

/**
 * ChatServer. Main class.
 * @author Antti Neuvonen
 */
public class ChatServer {
    //List of users in the server
    public static ArrayList<User> users;
    //All available commands
    public static HashMap<String,ICommand> commands;
    //Map of all rooms
    public static HashMap<String, Room> rooms;

    public static ServerSettings serverSettings;

    /**
     * Load settings and create rooms
     */
    static void initServer() {
        // Load server settings
        serverSettings = new ServerSettings();
        System.out.println("Loading settings");
        serverSettings.load();
        //Create rooms
        initRooms();
        //Create commands
        initCommands();
    }
    static void initRooms() {
        for(String roomName : serverSettings.roomNames) {
            Room room = new Room();
            room.initRoom(roomName);
            rooms.put(roomName,room);
        }
    }
    /**
     * 
     */
    static void initCommands() {
        // All commands here
        commands.put("/joinroom",new CJoinroom());
    }
    /**
     * Main method. 
     */
    public static void main(String[] args) {
        users = new ArrayList<User>();
        commands = new HashMap<String, ICommand>();
        rooms = new HashMap<String,Room>();
        initServer();
        initCommands();

        ChatServer cs = new ChatServer();
        cs.listen();
    }
    public void listen() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(serverSettings.port);
            while(true) {
                Socket clientSocket = server.accept();
                new ChatServerThread(clientSocket).start();
            }
        } catch (Exception e) {
            //TODO: handle exception
        } finally {

        }
    }

    public class ChatServerThread extends Thread {
        public User user;

        public ChatServerThread(Socket socket) {
            user = new User(socket);
            //TODO: Refactor?
            try {
                user.output = new PrintWriter(socket.getOutputStream(), true);
                user.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                //TODO: handle exception
            }


        }

        public void run() {
            try {
                authenticate();
                while(true) {
                    String msg = user.input.readLine();
                    if(msg.isEmpty() || msg.equals("\n")) {
                        continue;
                    }
                    //Handle possible command
                    ICommand cmd = null;
                    String msgbody = "";
                    String[] splitMsg = msg.split(" ");
                    int index = msg.indexOf(' ');
                    if(index!=-1) {
                        cmd = commands.get(msg.substring(0,index));
                        msgbody = msg.substring(index+1);
                        System.out.println(cmd);
                        System.out.println(msgbody);
                    }
                    if(cmd!=null) {
                        cmd.execute(this,msgbody);
                        continue;
                    }
                    System.out.println("Commands done");
                    if(user.currentRoom == null) {
                        continue;
                    } 

                    sendMessage(msg);

                }
                
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void authenticate() {
            try {
                String msg = user.input.readLine();
                if(msg.isEmpty()) {
                    user.output.println("Error");
                    user.socket.close();
                }
                //Username
                String[] s_msg = msg.split(" ");
                user.name = s_msg[0];
                //Server password if any
                if(s_msg.length > 1 && serverSettings.serverPassword != "") {
                    String pass = s_msg[1];
                    if(pass.equals(serverSettings.serverPassword)) {
    
                    } else {
                        user.output.println("Error");
                        user.socket.close();
                    }
                }
                //Success, welcome
                user.output.println(serverSettings.motd);
                System.out.println(user.name +" joined the server");                
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void sendMessage(String msg) {
            try {
                for(User u : user.currentRoom.users) {
                    u.output.println(user.name+": "+msg);
                }
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }

}