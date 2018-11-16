package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Room {
    public RoomSettings roomSettings;
    public List<User> users;

    public Room() {

    }

    public void initRoom(String roomName) {
        roomSettings = new RoomSettings();
        roomSettings.load(roomName);

        users = Collections.synchronizedList(new ArrayList<User>());
    }
}