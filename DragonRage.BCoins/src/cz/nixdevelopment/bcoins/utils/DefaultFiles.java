package cz.nixdevelopment.bcoins.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import cz.nixdevelopment.bcoins.BCoins;

public class DefaultFiles {

    public static void BShop() {
        
        File msg = new File("plugins/BCoins/bshop.yml");
        if (!msg.exists()) {
          
            try {
              
                msg.createNewFile();
              
            } catch (IOException ex) {
              
                System.out.println("ERROR: Failed to create bshop.yml file!");
                ex.printStackTrace();
              
            }
          
            FileConfiguration msgfc = YamlConfiguration.loadConfiguration(msg);
            
            ArrayList<String> commands = new ArrayList<String>();
            
            commands.add("tellraw @a NiX3r is da best!");
            commands.add("fly %NICK%");
            
            msgfc.set("BShop.Test.Commands", commands);
            msgfc.set("BShop.Test.Cost", 30.0);
            
            try {
                msgfc.save(msg);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
}
