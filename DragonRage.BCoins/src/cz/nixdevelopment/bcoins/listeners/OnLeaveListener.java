package cz.nixdevelopment.bcoins.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import cz.nixdevelopment.bcoins.BCoins;
import cz.nixdevelopment.bcoins.instances.BCoinPlayerInstance;

public class OnLeaveListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public static void OnLeaveEvent(PlayerQuitEvent event) {
        
        if(!BCoins.players.isEmpty()) {
            
            if(BCoins.players.size() == 1) {
                BCoins.players.remove(0);
                return;
            }
            
            for(BCoinPlayerInstance bpi : BCoins.players) {
                
                if(bpi.GetUUID().equals(event.getPlayer().getUniqueId())) {
                    BCoins.players.remove(bpi);
                }
                
            }
        }
        
    }
}
