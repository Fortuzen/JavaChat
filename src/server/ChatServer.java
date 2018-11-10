package server;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * ChatServer
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
     * Main method. 
     */
    public static void main(String[] args) {
        initServer();

    }
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
    }
    static void initRooms() {
        rooms = new HashMap<String,Room>();
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
        
    }
}