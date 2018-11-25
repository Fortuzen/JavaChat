package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
/**
 * Room class. 
 */
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
    /**
     * Check if user/address is banned
     * address:username:(reason)
     */
    public boolean isBanned(String address, String username) {
        for(String ba : roomSettings.getBannedAddresses()) {
            String[] splitted = ba.split(":");
            if(splitted[0].equals(address)) {
                return true;
            }
            if(splitted[1].equals(username)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check if address is banned
     */
    public boolean isAddressBanned(String address) {
        for(String ba : roomSettings.getBannedAddresses()) {
            String[] splitted = ba.split(":");
            if(splitted[0].equals(address)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check if username is banned
     */
    public boolean isUsernameBanned(String username) {
        for(String ba : roomSettings.getBannedAddresses()) {
            String[] splitted = ba.split(":");
            if(splitted[1].equals(username)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check if user/address is muted
     */
    public boolean isMuted(String address, String username) {
        for(String ba : roomSettings.getMutedAddresses()) {
            String[] splitted = ba.split(":");
            if(splitted[0].equals(address)) {
                return true;
            }
            if(splitted[1].equals(username)) {
                return true;
            } 
        }
        return false;
    }
    /**
     * Get user from the room using name.
     */
    public User getUser(String name) {
        for(User u : users) {
            if(u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }
}