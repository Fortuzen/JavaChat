package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Integer;

/**
 * Class containing settings for the server
 */
public class ServerSettings {
    //From config.ini
    String name;
    String description;
    String serverPassword;
    String serverAdminPassword;
    public String rules;
    public String motd; // TODO: getterit ja setterit vai

    String[] roomNames;

    int maxUsers;
    int port;
    
    boolean logging;

    //From bans.txt
    public List<String> bannedAddresses;

    // TODO: Fix paths
    private String configFilePath = "bin/server/config.ini";
    private String banListPath = "bin/server/bans.txt";

    public ServerSettings() {

    }

    public void load() {
        File configFile = new File(configFilePath);
        File banFile = new File(banListPath);

        Scanner reader = null;
        HashMap<String,String> settings = new HashMap();
        bannedAddresses = Collections.synchronizedList(new ArrayList<String>());

        try {
            configFile.createNewFile();
            banFile.createNewFile();
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
                    //System.out.println(splitted[0]+"="+"");
                    settings.put(splitted[0],"");   
                } else {
                    //System.out.println(splitted[0]+"="+splitted[1]);
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
        } catch (Exception e) {
            System.out.println("Exception occurred: "+e);
        } finally {
            if(reader != null) {
                reader.close();
            }
            name = settings.getOrDefault("name", "DefaultServerName");
            description = settings.getOrDefault("description", "DefaultDescription");
            serverPassword = settings.getOrDefault("serverPassword", "");
            serverAdminPassword = settings.getOrDefault("serverAdminPassword", "admin");
            rules = settings.getOrDefault("rules", "No rules");
            motd = settings.getOrDefault("motd", "Welcome");
        
            roomNames = settings.getOrDefault("roomNames", "room1").split(",");
        
            maxUsers = Integer.parseInt(settings.getOrDefault("maxUsers", "256"));
            port = Integer.parseInt(settings.getOrDefault("port", "8000"));
            
            logging = Boolean.parseBoolean(settings.getOrDefault("logging", "true"));

            // Save
            saveSettings();
        }       
    }

    void saveSettings() {
        File configFile = new File(configFilePath);
        File banFile = new File(banListPath);
        PrintWriter pw = null;
        // Awful
        try {
            pw = new PrintWriter(configFile);
            pw.println("name"+"="+this.name);
            pw.println("description"+"="+this.description);
            pw.println("serverPassword"+"="+this.serverPassword);
            pw.println("serverAdminPassword"+"="+this.serverAdminPassword);
            pw.println("rules"+"="+this.rules);
            pw.println("motd"+"="+this.motd);
            pw.println("roomNames"+"="+arrayToString(roomNames, ","));
            pw.println("maxUsers"+"="+this.maxUsers);
            pw.println("port"+"="+this.port);
            pw.println("logging"+"="+this.logging);

            pw.close();
            pw = new PrintWriter(banFile);
            for(String addr : bannedAddresses) {
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

    String arrayToString(String[] array,String end) {
        String out = "";
        for(String s : array) {
            out += s+end;
        }
        if(end != "") {
            return out.substring(0, out.length()-1);
        } else {
            return out;
        }
    }
}