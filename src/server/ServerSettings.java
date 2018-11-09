package server;

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.lang.Integer;

/**
 * Class containing settings for the server
 */
public class ServerSettings {
    String name;
    int maxUsers;

    private String configFilePath = "bin/server/config.ini";
    public ServerSettings() {

    }

    public void load() {
        File file = new File(configFilePath);
        Scanner reader = null;
        HashMap<String,String> settings = new HashMap();
        try {
            reader = new Scanner(file);   
            while(reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if(line.isEmpty()) break;

                String[] splitted = line.split("=");
                System.out.println(splitted[0]);
                settings.put(splitted[0],splitted[1]);             
            }

        } catch (Exception e) {
            System.out.println("Exception occurred: "+e);
        } finally {
            if(reader != null) {
                reader.close();
            }
            name = settings.getOrDefault("name", "DefaultServerName");
            maxUsers = Integer.parseInt(settings.getOrDefault("maxUsers", "256"));

        }       
    }
}