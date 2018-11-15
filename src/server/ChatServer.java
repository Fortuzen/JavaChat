package server;
import java.util.HashMap;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import server.commands.*;

import shared.ICommunication;
import shared.DefaultCommunication;
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
        commands.put("/leaveroom", new CLeaveroom());
        commands.put("/help",  new CHelp());
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
        }

        public void run() {
            try {
                authenticate();
                while(true) {
                    String msg = user.communication.receiveMessage();
                    if(msg.isEmpty() || msg.equals("\n")) {
                        continue;
                    }
                    //Handle possible command
                    ICommand cmd = null;
                    String msgbody = "";

                    
                    if(msg.charAt(0) == '/') {                     
                        String[] splittedMsg = msg.split(" ");
                        cmd = commands.get(splittedMsg[0]);  
                        int index = msg.indexOf(' ');
                        msgbody = msg.substring(index+1);                     
                    } else {
                        msgbody = msg;
                    }
                                     
                    if(cmd!=null) {
                        cmd.execute(this,msgbody);
                        continue;
                    }
                    System.out.println("Commands done");
                    //Default command
                    sendMessageToCurrentRoom(msgbody, user.name);
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                users.remove(user);
            }
        }

        public void authenticate() {
        	user.communication.sendMessage("Please give your nickname and the server password (if any) in the message area below");
            try {
                String msg = user.communication.receiveMessage();
                if(msg.isEmpty()) {
                    user.communication.sendMessage("Error");
                    user.socket.close(); // TODO: Fix
                }
                //Username
                String[] s_msg = msg.split(" ");
                user.name = s_msg[0];
                if(user.name.equals("SERVER")) {
                    user.communication.sendMessage("Error");
                    user.socket.close(); // TODO: Fix
                }
                //Server password if any
                if(s_msg.length > 1 && serverSettings.serverPassword != "") {
                    String pass = s_msg[1];
                    if(pass.equals(serverSettings.serverPassword)) {
    
                    } else {
                        user.communication.sendMessage("Error");
                        user.socket.close();
                    }
                }
                //Success, welcome
                users.add(user);
                user.communication.sendMessage(serverSettings.motd);
                System.out.println(user.name +" joined the server");                
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void sendMessageToCurrentRoom(String msg, String MsgSender) {
            if(user.currentRoom == null) {
                return;
            } 
            if ((MsgSender.equals("SERVER"))) {
	            System.out.println("Message to be sent: "+msg);
	            for(User u : user.currentRoom.users) {
	                u.communication.sendMessage("** " + msg + " **");
	            }
            } else {
	            System.out.println("Message to be sent: "+msg);
	            for(User u : user.currentRoom.users) {
	                u.communication.sendMessage(MsgSender + ": " + msg);
	            }
            }
        }
        public void sendMessageToUser(String msg) {
        	user.communication.sendMessage(msg);
        }
    }

}