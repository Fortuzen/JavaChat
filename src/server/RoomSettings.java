package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Integer;

public class RoomSettings {

    //From config.ini
    public String name;
    public String description;
    public String roomPassword;
    public String roomModeratorPassword;
    public String roomAdminPassword;
    public String rules;
    public String motd;

    public int maxUsers;
    
    //From bans.txt
    public ArrayList<String> bannedAddresses;
    //From mutes.txt
    public ArrayList<String> mutedAddresses;

    // TODO: Fix paths
    private String path = "bin/server/";
    private String configPath;
    private String banPath;
    private String mutePath;
    public RoomSettings() {

    }

    public void load(String roomName) {
        path += roomName+"/";
        configPath = path+"config.ini";
        banPath = path+"bans.txt";
        mutePath = path+"mutes.txt";
        File folder = new File(path);
        File configFile = new File(configPath);
        File banFile = new File(banPath);
        File muteFile = new File(mutePath);

        Scanner reader = null;
        HashMap<String,String> settings = new HashMap();
        bannedAddresses = new ArrayList<String>();
        mutedAddresses = new ArrayList<String>();

        try {
            if(!folder.exists()) {
                folder.mkdir();
            }
            configFile.createNewFile();
            banFile.createNewFile();
            muteFile.createNewFile();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            reader = new Scanner(configFile);
            // Note: long config lines eg. rules
            while(reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if(line.isEmpty()) break;

                String[] splitted = line.split("=");
                if(splitted.length==1) {
                    System.out.println(splitted[0]+"="+"");
                    settings.put(splitted[0],"");   
                } else {
                    System.out.println(splitted[0]+"="+splitted[1]);
                    settings.put(splitted[0],splitted[1]);   
                }          
            }
            System.out.println("");
            reader.close(); // Is this safe?
            reader = new Scanner(banFile);
            while(reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if(line.isEmpty()) break;
                bannedAddresses.add(line);
                System.out.println(line);
            }
            System.out.println("");
            reader.close(); // Is this safe?
            reader = new Scanner(muteFile);
            while(reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if(line.isEmpty()) break;
                mutedAddresses.add(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: "+e);
        } finally {
            if(reader != null) {
                reader.close();
            }
            name = settings.getOrDefault("name", roomName);
            description = settings.getOrDefault("description", "DefaultDescription");
            roomPassword = settings.getOrDefault("roomPassword", "");
            roomModeratorPassword = settings.getOrDefault("roomModeratorPassword", "moderator");
            roomAdminPassword = settings.getOrDefault("roomAdminPassword", "admin");
            rules = settings.getOrDefault("rules", "No rules");
            motd = settings.getOrDefault("motd", "Welcome");
        
            maxUsers = Integer.parseInt(settings.getOrDefault("maxUsers", "128"));

            // Save
            saveSettings();
        }       
    }

    void saveSettings() {
        File configFile = new File(configPath);
        File banFile = new File(banPath);
        File muteFile = new File(mutePath);

        PrintWriter pw = null;
        // Awful
        try {
            pw = new PrintWriter(configFile);
            pw.println("name"+"="+this.name);
            pw.println("description"+"="+this.description);
            pw.println("roomPassword"+"="+this.roomPassword);
            pw.println("roomModeratorPassword"+"="+this.roomModeratorPassword);
            pw.println("roomAdminPassword"+"="+this.roomAdminPassword);
            pw.println("rules"+"="+this.rules);
            pw.println("motd"+"="+this.motd);
            pw.println("maxUsers"+"="+this.maxUsers);


            pw.close();
            pw = new PrintWriter(banFile);
            for(String addr : bannedAddresses) {
                pw.println(addr);
            }
            pw.close();
            pw = new PrintWriter(muteFile);
            for(String addr : mutedAddresses) {
                pw.println(addr);
            }

        } catch (Exception e) {
            System.out.println("Exception in SaveSettings: "+e);
        } finally {
            if(pw!=null) {
                pw.close();
            }
        }
    }

    synchronized public void saveBannedUsers() {
        try {
            File banFile = new File(banPath);
            PrintWriter pw = new PrintWriter(banFile);
            for(String addr : bannedAddresses) {
                pw.println(addr);
            }
            pw.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    synchronized public void saveMutedUsers() {
        try {
            File muteFile = new File(mutePath);
            PrintWriter pw = new PrintWriter(muteFile);
            for(String addr : mutedAddresses) {
                pw.println(addr);
            }
            pw.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }   

}