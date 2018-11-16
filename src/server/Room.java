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
    // Ban format:  address:username
    public boolean isBanned(String address, String username) {
        for(String ba : roomSettings.bannedAddresses) {
            String[] splitted = ba.split(":");
            if(splitted[0].equals(address)) {
                return true;
            } else if(splitted[1].equals(username)) {
                return true;
            }
        }
        return false;
    }
}