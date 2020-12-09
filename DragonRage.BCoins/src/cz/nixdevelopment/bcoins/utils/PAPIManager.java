package cz.nixdevelopment.bcoins.utils;

import org.bukkit.entity.Player;

import cz.nixdevelopment.bcoins.BCoins;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PAPIManager extends PlaceholderExpansion{

	@Override
    public String getAuthor() {
        return "NiX3r";
    }
 
    @Override
    public String getIdentifier() {
        return "bcoins";
    }
 
    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getPlugin() {
        // TODO Auto-generated method stub
        return BCoins.inst.getName();
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        
        String output = "ERR#404";
        
        if(identifier.equalsIgnoreCase("withbc"))
            output = String.valueOf(MySQL.getTokens(p.getUniqueId().toString())) + "BC";
        else if(identifier.equalsIgnoreCase("withoutbc"))
            output = String.valueOf(MySQL.getTokens(p.getUniqueId().toString()));
        else
            output = "WrongIdentifier";
        
        return output;
    }
	
}
