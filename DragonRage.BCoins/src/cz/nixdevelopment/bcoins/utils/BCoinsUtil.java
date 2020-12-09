package cz.nixdevelopment.bcoins.utils;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.util.ArrayList;
import java.util.List;

import cz.nixdevelopment.bcoins.BCoins;
import cz.nixdevelopment.bcoins.events.ShopItemEvent;

public class BCoinsUtil {

    public static Boolean IsDouble(String value) {
        
        try {
            Double.parseDouble(value);
            return true;
        }
        catch(Exception ex) {
            return false;
        }
        
    }
    
    public static Boolean IsItemExist(String identifier) {
        
        for(ShopItemEvent sie : BCoins.items) {
            if(sie.GetIdentifier().equals(identifier))
                return true;
        }
        
        return false;
        
    }
    
    public static void DispatchItemCommands(ShopItemEvent item, String nick) {
        
        for(String cmd : item.GetCommands()) {
            
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%NICK%", nick));
            
        }
        
    }
    
    public static void LoadBShop() {
        
        File bshop = new File("plugins/BCoins/bshop.yml");
        FileConfiguration bshopFC = YamlConfiguration.loadConfiguration(bshop);
        ConfigurationSection sec = bshopFC.getConfigurationSection("BShop");
        if (sec != null) {
            for (String key : sec.getKeys(false)) {
                ShopItemEvent sie = new ShopItemEvent(key, bshopFC.getDouble("BShop." + key + ".Cost"));
                if(((ArrayList<String>) bshopFC.getList("BShop." + key + ".Commands")) != null)
                    sie.SetCommands((ArrayList<String>) bshopFC.getList("BShop." + key + ".Commands"));
                BCoins.items.add(sie);
            }
        }
        
    }
    
    public static void UnloadBShop() {

        File bshop = new File("plugins/BCoins/bshop.yml");
        FileConfiguration bshopFC = YamlConfiguration.loadConfiguration(bshop);
        
        for(ShopItemEvent sie : BCoins.items) {
            
            bshopFC.set("BShop." + sie.GetIdentifier() + ".Cost", sie.GetCost());
            bshopFC.set("BShop." + sie.GetIdentifier() + ".Commands", sie.GetCommands());
            
        }
        
        try {
            bshopFC.save(bshop);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
}
