package server;
import java.util.HashMap;
import java.util.Map;

import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import server.commands.*;

import shared.ICommunication;
import shared.DefaultCommunication;

import java.time.LocalDateTime;
import java.sql.Timestamp;
/**
 * ChatServer. Main class.
 * @author Antti Neuvonen
 */
public class ChatServer {
    // List of users in the server
    public static List<User> users;
    // All available commands
    public static Map<String,ICommand> commands;
    // Map of all rooms
    public static Map<String, Room> rooms;
    //
    public static ServerSettings serverSettings;

    /**
     * Load settings and create rooms
     */
    static void initServer() {
        users = Collections.synchronizedList(new ArrayList<User>());
        // ConcurrentHashMap another option.
        commands = Collections.synchronizedMap(new HashMap<String, ICommand>());
        rooms = Collections.synchronizedMap(new HashMap<String,Room>());
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
        //User level commands
        commands.put("/joinroom",new CJoinroom());
        commands.put("/leaveroom", new CLeaveroom());
        commands.put("/help",  new CHelp());
        commands.put("/privmsg", new CPrivmsg());
        commands.put("/username", new CUsername());
        commands.put("/users",  new CUsers());
        commands.put("/motd",  new CMotd());
        commands.put("/quit", new CQuit());
        //Moderator

        //Room admin

        //Server admin
 
    }
    /**
     * Main method. 
     */
    public static void main(String[] args) {
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
                    if(user.getSocket().isClosed()) {
                        break;
                    }
                    String msg = user.getCommunication().receiveMessage();
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
                    sendMessageToCurrentRoom(msgbody, user.getName());
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                users.remove(user);
            }
        }

        public void authenticate() {       	
            try {
                user.getCommunication().sendMessage("Please give your nickname and the server password (if any) in the message area below");
                String msg = user.getCommunication().receiveMessage();
                if(msg.isEmpty()) {
                    user.getCommunication().sendMessage("Error");
                    user.getSocket().close();
                }
                //Username
                String[] s_msg = msg.split(" ");
                user.setName(s_msg[0]);
                if(user.getName().equals("SERVER")) {
                    user.getCommunication().sendMessage("Error");
                    user.getSocket().close();
                }
                //Server password if any
                if(s_msg.length > 1 && serverSettings.serverPassword != "") {
                    String pass = s_msg[1];
                    if(pass.equals(serverSettings.serverPassword)) {
    
                    } else {
                        user.getCommunication().sendMessage("Error");
                        user.getSocket().close();
                    }
                }
                //Success, welcome
                users.add(user);                    
                user.getCommunication().sendMessage(serverSettings.motd);
                System.out.println(user.getName() +" joined the server");                
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void sendMessageToCurrentRoom(String msg, String MsgSender) {
            if(user.getCurrentRoom() == null) {
                return;
            }
            if(user.getCurrentRoom().roomSettings.mutedAddresses.contains(user.getSocket().getInetAddress().getHostAddress())) {
                return;
            }
            try {
                if ((MsgSender.equals("SERVER"))) {
                    System.out.println("Message to be sent: "+msg);
                    for(User u : user.getCurrentRoom().users) {
                        u.getCommunication().sendMessage("** " + msg + " **");
                    }
                } else {
                    LocalDateTime date = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
                    String timestr = "["+String.valueOf(date.getHour()) + ":" + String.valueOf(date.getMinute()) + ":"+String.valueOf(date.getSecond())+"] ";
                    System.out.println("Message to be sent: "+msg);
                    for(User u : user.getCurrentRoom().users) {
                        u.getCommunication().sendMessage(timestr + MsgSender + ": " + msg);
                    }
                }
            } catch (Exception e) {
                //TODO: handle exception
            }

        }
        public void sendMessageToUser(String msg) {
            try {
                user.getCommunication().sendMessage(msg);
            } catch (Exception e) {
                //TODO: handle exception
            }
        	
        }
    }

}