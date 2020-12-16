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
import cz.nixdevelopment.bcoins.instances.DatabaseInstance;
import cz.nixdevelopment.bcoins.listeners.OnJoinListener;
import cz.nixdevelopment.bcoins.utils.BCoinsUtil;
import cz.nixdevelopment.bcoins.utils.DefaultFiles;
import cz.nixdevelopment.bcoins.utils.MySQL;
import cz.nixdevelopment.bcoins.utils.PAPIManager;

public class BCoins extends JavaPlugin{
    
    private DatabaseInstance DBS;
    
    public static JavaPlugin inst;
    public static ArrayList<ShopItemEvent> items = new ArrayList<ShopItemEvent>();
    public static String Prefix;
    
    private static Connection connection;
    
    public void onEnable() {

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPIManager().register();
        } else {
            this.getPluginLoader().disablePlugin(this);
        }
        
        getConfig().options().copyDefaults(true);
        saveConfig();
        DefaultFiles.BShop();
        
        DBS = new DatabaseInstance(getConfig().getString("SQL.Host"), getConfig().getInt("SQL.Port"), getConfig().getString("SQL.Database"), getConfig().getString("SQL.User"), getConfig().getString("SQL.Pass"));
        Prefix = getConfig().getString("Prefix");
        
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
                connection = DriverManager.getConnection(DBS.GetConnectionString(), DBS.GetUsername(), DBS.GetPassword());
                System.out.println("[BCoins] MySQL connection is successfully conected");
                
            }
            
        }
        catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
            this.getPluginLoader().disablePlugin(this);
        }
        catch(ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            this.getPluginLoader().disablePlugin(this);
        }
        
    }
    
    public static Connection getConnection() {
        return connection;
    }
    
}
