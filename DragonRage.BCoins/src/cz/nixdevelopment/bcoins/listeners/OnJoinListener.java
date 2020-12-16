package cz.nixdevelopment.bcoins.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import cz.nixdevelopment.bcoins.BCoins;
import cz.nixdevelopment.bcoins.instances.BCoinPlayerInstance;
import cz.nixdevelopment.bcoins.utils.MySQL;

public class OnJoinListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public static void OnJoinEvent(PlayerJoinEvent event) {
        
        MySQL.checkPlayerExists(event.getPlayer().getUniqueId().toString(), event.getPlayer().getName());
        BCoins.players.add(new BCoinPlayerInstance(event.getPlayer().getUniqueId(), event.getPlayer().getName(), MySQL.getTokens(event.getPlayer().getUniqueId().toString())));
        
    }
    
}
