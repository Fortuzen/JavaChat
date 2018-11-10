package server;

import java.util.ArrayList;

public class Room {
    RoomSettings roomSettings;
    ArrayList<User> users;

    public Room() {

    }

    public void initRoom(String roomName) {
        roomSettings = new RoomSettings();
        roomSettings.load(roomName);
        users = new ArrayList<User>();
    }
}