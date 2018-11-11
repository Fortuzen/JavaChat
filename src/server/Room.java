package server;

import java.util.ArrayList;

public class Room {
    public RoomSettings roomSettings;
    public ArrayList<User> users;

    public Room() {

    }

    public void initRoom(String roomName) {
        roomSettings = new RoomSettings();
        roomSettings.load(roomName);
        users = new ArrayList<User>();
    }
}