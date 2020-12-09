package cz.nixdevelopment.bcoins.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;

import cz.nixdevelopment.bcoins.BCoins;

public class MySQL {
    
    public static void createTable() {
        
        //CREATE TABLE IF NOT EXISTS TokensManager(User varchar(36),Tokens double);
        
        try {
            
            PreparedStatement statement = BCoins.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS BCoins(User varchar(36),Tokens double)");
            
            if(statement.execute()) {
                System.out.println("Table was created");
            }
            else {
                System.out.println("Table wasn't created");
            }
            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void checkPlayerExists(String UUID) {
        
        try {
            PreparedStatement statement = BCoins.getConnection()
                    .prepareStatement("SELECT * FROM BCoins WHERE User=?");
            statement.setString(1, UUID);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                //BCoins.inst.getServer().broadcastMessage(ChatColor.YELLOW + "Player Found");
            }
            else {
                
                //BCoins.inst.getServer().broadcastMessage(ChatColor.GREEN + "Player Inserted");
                
                PreparedStatement insert = BCoins.getConnection()
                        .prepareStatement("INSERT INTO BCoins (User,Tokens) VALUES (?,?)");
                insert.setString(1, UUID);
                insert.setDouble(2, Double.valueOf("0"));
                insert.executeUpdate();
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public static double getTokens(String UUID) {
        
        try {
            PreparedStatement statement = BCoins.getConnection()
                    .prepareStatement("SELECT * FROM BCoins WHERE User=?");
            statement.setString(1, UUID);
            
            ResultSet results = statement.executeQuery();
            results.next();
            
            return results.getDouble("Tokens");

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
        
    }
    
    public static boolean setTokens(String UUID, double toSet) {
        
        try {
            PreparedStatement statement = BCoins.getConnection()
                    .prepareStatement("UPDATE BCoins SET Tokens=? WHERE User=?");
            statement.setDouble(1, toSet);
            statement.setString(2, UUID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
}
