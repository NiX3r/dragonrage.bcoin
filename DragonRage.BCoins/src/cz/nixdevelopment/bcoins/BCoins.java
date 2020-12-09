package cz.nixdevelopment.bcoins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import cz.nixdevelopment.bcoins.commands.BCoinCommand;
import cz.nixdevelopment.bcoins.commands.BShopCommand;
import cz.nixdevelopment.bcoins.events.*;
import cz.nixdevelopment.bcoins.listeners.OnJoinListener;
import cz.nixdevelopment.bcoins.utils.BCoinsUtil;
import cz.nixdevelopment.bcoins.utils.DefaultFiles;
import cz.nixdevelopment.bcoins.utils.MySQL;
import cz.nixdevelopment.bcoins.utils.PAPIManager;

public class BCoins extends JavaPlugin{

    public static JavaPlugin inst;
    public static ArrayList<ShopItemEvent> items = new ArrayList<ShopItemEvent>();
    public static String Prefix = "§4§l[§cBCoin§4§l] §7»";
    
    private static Connection connection;
    
    public void onEnable() {

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPIManager().register();
        } else {
            this.getPluginLoader().disablePlugin(this);
        }
        
        DefaultFiles.BShop();
        
        inst = this;
        setUpDatabase();
        
        this.getCommand("bcoin").setExecutor(new BCoinCommand());
        this.getCommand("bshop").setExecutor(new BShopCommand());
        
        Bukkit.getPluginManager().registerEvents(new OnJoinListener(), this);
        MySQL.createTable();
        BCoinsUtil.LoadBShop();
        
    }
    
    public void onDisable() {
        BCoinsUtil.UnloadBShop();
    }
    
    private void setUpDatabase() {
        
        try {
            
            synchronized(this) {
                
                if(getConnection() != null && !getConnection().isClosed())
                    return;
                
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://dragon-rage.eu:3306/s7_litebans", "u7_MO4RsqbDCO", "XF0BQJe3J!P7J6!0o@EkPYQR");
                System.out.println("[BCoins] MySQL connection is successfully conected");
                
            }
            
        }
        catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        catch(ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public static Connection getConnection() {
        return connection;
    }
    
}
