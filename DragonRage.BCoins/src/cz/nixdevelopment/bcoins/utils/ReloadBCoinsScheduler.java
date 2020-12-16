package cz.nixdevelopment.bcoins.utils;

import org.bukkit.Bukkit;

import cz.nixdevelopment.bcoins.BCoins;

public class ReloadBCoinsScheduler {

    public static void Scheduler() {
        
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(BCoins.inst, new Runnable() {
            public void run() {
                
                for(int i = 0; i < BCoins.players.size(); i++) {
                    
                    BCoins.players.get(i).SetBC(MySQL.getTokens(BCoins.players.get(i).GetUUID().toString()));
                    
                }
                
            }
        },0, 20L * 100);
        
    }
    
}
