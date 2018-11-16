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
    // Ban format:  address:username:(reason)
    public boolean isBanned(String address, String username) {
        for(String ba : roomSettings.bannedAddresses) {
            String[] splitted = ba.split(":");
            if(splitted[0].equals(address)) {
                return true;
            } 
        }
        return false;
    }

    public boolean isMuted(String address, String username) {
        for(String ba : roomSettings.mutedAddresses) {
            String[] splitted = ba.split(":");
            if(splitted[0].equals(address)) {
                return true;
            } 
        }
        return false;
    }

    public User getUser(String name) {
        for(User u : users) {
            if(u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }
}