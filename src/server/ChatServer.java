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
        initSettings();
        initCommands();

    }
    /**
     * Load settings and create rooms
     */
    static void initSettings() {
        // Load server settings
        serverSettings = new ServerSettings();
        System.out.println("Loading settings");
        serverSettings.load();
    }
    /**
     * 
     */
    static void initCommands() {
        
    }
}