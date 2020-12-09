package cz.nixdevelopment.bcoins.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.nixdevelopment.bcoins.BCoins;
import cz.nixdevelopment.bcoins.events.ShopItemEvent;
import cz.nixdevelopment.bcoins.utils.BCoinsUtil;
import cz.nixdevelopment.bcoins.utils.Messages;
import cz.nixdevelopment.bcoins.utils.MySQL;

public class BShopCommand implements CommandExecutor {

    @SuppressWarnings("unused")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        // send player help message
        if(args.length == 0) {
            sender.sendMessage(Messages.BShopUsage(sender.hasPermission("bcoins.admin")));
        }
        
        // commands: /bshop list
        else if(args.length == 1) {
            
            if(args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(Messages.ListBShop());
            }
            else {
                sender.sendMessage(Messages.UnknownCommandBShop());
            }
            
        }
        
        // commands: /bshop [buy | remove] <identifier>
        else if(args.length == 2) {
            
            if(args[0].equalsIgnoreCase("buy")) {
                
                if(BCoinsUtil.IsItemExist(args[1])) {
                    
                    Player buier = Bukkit.getPlayer(sender.getName());
                    double pocket = MySQL.getTokens(buier.getUniqueId().toString());
                    double cost = 0.0;
                    ShopItemEvent item = null;
                    
                    for(ShopItemEvent sie : BCoins.items) {
                        if(sie.GetIdentifier().equals(args[1]))
                            item = sie;
                    }
                    
                    if(item != null) {
                        
                        cost = item.GetCost();
                        
                        if(pocket >= cost) {
                            
                            pocket -= cost;
                            
                            MySQL.setTokens(buier.getUniqueId().toString(), pocket);
                            
                            BCoinsUtil.DispatchItemCommands(item, buier.getName());
                            
                            sender.sendMessage(Messages.BoughtItem(pocket, item.GetIdentifier()));
                            
                        }
                        else {
                            sender.sendMessage(Messages.TooPoor((cost - pocket)));
                        }
                        
                    }
                    else {
                        System.out.println("Some error's here!");
                    }
                    
                }
                else {
                    Messages.ItemNotExist();
                }
                
            }
            else if(args[0].equalsIgnoreCase("remove")) {
                if(sender.hasPermission("bcoins.admin")) {
                    if(BCoinsUtil.IsItemExist(args[1])) {
                        
                        ShopItemEvent item = null;
                        
                        for(ShopItemEvent sie : BCoins.items) {
                            if(sie.GetIdentifier().equals(args[1]))
                                item = sie;
                        }
                        
                        if(item != null) {
                            
                            BCoins.items.remove(item);
                            sender.sendMessage(Messages.RemoveItem(item.GetIdentifier()));
                            
                        }
                        else {
                            System.out.println("Some error's here!");
                        }
                        
                    }
                    else {
                        Messages.ItemNotExist();
                    }
                }
                else {
                    sender.sendMessage(Messages.HaventPerm("bcoins.admin"));
                }
            }
            else {
                sender.sendMessage(Messages.UnknownCommandBShop());
            }
            
        }
        
        // commands: /bshop [buy | give | create | cmd] <identifier> (<cost | nick> | [list])
        else if(args.length == 3) {
            if(args[0].equalsIgnoreCase("buy")) {
                if(Bukkit.getPlayer(args[2]) != null) {
                    if(BCoinsUtil.IsItemExist(args[1])) {
                        
                        Player buier = Bukkit.getPlayer(sender.getName());
                        Player taker = Bukkit.getPlayer(args[2]);
                        double pocket = MySQL.getTokens(buier.getUniqueId().toString());
                        double cost = 0.0;
                        ShopItemEvent item = null;
                        
                        for(ShopItemEvent sie : BCoins.items) {
                            if(sie.GetIdentifier().equals(args[1]))
                                item = sie;
                        }
                        
                        if(item != null) {
                            
                            cost = item.GetCost();
                            
                            if(pocket >= cost) {
                                
                                pocket -= cost;
                                
                                MySQL.setTokens(buier.getUniqueId().toString(), pocket);
                                
                                BCoinsUtil.DispatchItemCommands(item, taker.getName());
                                
                                sender.sendMessage(Messages.BoughtItem(pocket, item.GetIdentifier(), taker.getName()));
                                taker.sendMessage(Messages.BoughtYouItem(item.GetIdentifier(), buier.getName()));
                                
                            }
                            else {
                                sender.sendMessage(Messages.TooPoor((cost - pocket)));
                            }
                            
                        }
                        else {
                            System.out.println("Some error's here!");
                        }
                        
                    }
                    else {
                        Messages.ItemNotExist();
                    }
                }
                else {
                    sender.sendMessage(Messages.TargetOffline(args[2]));
                }
            }
            else if(args[0].equalsIgnoreCase("give")) {
                if(sender.hasPermission("bcoins.admin")) {
                    if(Bukkit.getPlayer(args[2]) != null) {
                        if(BCoinsUtil.IsItemExist(args[1])) {
                            
                            Player admin = Bukkit.getPlayer(sender.getName());
                            Player taker = Bukkit.getPlayer(args[2]);
                            ShopItemEvent item = null;
                            
                            for(ShopItemEvent sie : BCoins.items) {
                                if(sie.GetIdentifier().equals(args[1]))
                                    item = sie;
                            }
                            
                            if(item != null) {
                                
                                BCoinsUtil.DispatchItemCommands(item, taker.getName());
                                
                                sender.sendMessage(Messages.GiveItem(item.GetIdentifier(), taker.getName()));
                                taker.sendMessage(Messages.GiveYouItem(item.GetIdentifier(), admin.getName()));
                                
                            }
                            else {
                                System.out.println("Some error's here!");
                            }
                            
                        }
                        else {
                            Messages.ItemNotExist();
                        }
                    }
                    else {
                        sender.sendMessage(Messages.TargetOffline(args[2]));
                    }
                }
                else {
                    sender.sendMessage(Messages.HaventPerm("bcoins.admin"));
                }
            }
            else if(args[0].equalsIgnoreCase("create")) {
                if(sender.hasPermission("bcoins.admin")) {
                    if(!BCoinsUtil.IsItemExist(args[1])) {
                        if(BCoinsUtil.IsDouble(args[2])) {
                            ShopItemEvent item = new ShopItemEvent(args[1], Double.valueOf(args[2]));
                            BCoins.items.add(item);
                            sender.sendMessage(Messages.CreateItem(args[1]));
                        }
                        else {
                            sender.sendMessage(Messages.UncorrectFormatCoins());
                        }
                    }
                    else {
                        sender.sendMessage(Messages.ItemExist());
                    }
                }
                else {
                    sender.sendMessage(Messages.HaventPerm("bcoins.admin"));
                }
            }
            else if(args[0].equalsIgnoreCase("cmd") && args[2].equalsIgnoreCase("list")) {
                if(sender.hasPermission("bcoins.admin")) {
                    if(BCoinsUtil.IsItemExist(args[1])) {
                        sender.sendMessage(Messages.ListItemCommands(args[1]));
                    }
                    else {
                        sender.sendMessage(Messages.ItemExist());
                    }
                }
                else {
                    sender.sendMessage(Messages.HaventPerm("bcoins.admin"));
                }
            }
            else {
                sender.sendMessage(Messages.UnknownCommandBShop());
            }
        }
        else if(args[0].equalsIgnoreCase("cmd") && args[2].equalsIgnoreCase("add") && args.length > 3) {
            if(sender.hasPermission("bcoins.admin")) {
                if(BCoinsUtil.IsItemExist(args[1])) {
                    String command = "";
                    ShopItemEvent item = null;
                    for(ShopItemEvent sie : BCoins.items) {
                        if(sie.GetIdentifier().equals(args[1])) {
                            item = sie;
                        }
                    }
                    if(item != null) {
                        for(int i = 3; i < args.length; i++) {
                            command += " " + args[i];
                        }
                        command = command.replaceFirst(" ", "");
                        
                        for(int i = 0; i <= BCoins.items.size(); i++) {
                            if(BCoins.items.get(i).GetIdentifier().equals(item.GetIdentifier())) {
                                BCoins.items.get(i).AddCommand(command);
                                break;
                            }
                        }
                        
                        sender.sendMessage(Messages.AddCommand(command, item.GetIdentifier()));
                        
                    }
                    else {
                        System.out.println("Some error's here!");
                    }
                }
                else {
                    sender.sendMessage(Messages.ItemExist());
                }
            }
            else {
                sender.sendMessage(Messages.HaventPerm("bcoins.admin"));
            }
        }
        else if(args[0].equalsIgnoreCase("cmd") && args[2].equalsIgnoreCase("remove") && args.length > 3) {
            if(sender.hasPermission("bcoins.admin")) {
                if(BCoinsUtil.IsItemExist(args[1])) {
                    String command = "";
                    ShopItemEvent item = null;
                    for(ShopItemEvent sie : BCoins.items) {
                        if(sie.GetIdentifier().equals(args[1])) {
                            item = sie;
                        }
                    }
                    if(item != null) {
                        for(int i = 3; i < args.length; i++) {
                            command += " " + args[i];
                        }
                        command = command.replaceFirst(" ", "");
                        
                        for(int i = 0; i <= BCoins.items.size(); i++) {
                            if(BCoins.items.get(i).GetIdentifier().equals(item.GetIdentifier())) {
                                BCoins.items.get(i).GetCommands().remove(command);
                                break;
                            }
                        }
                        
                        sender.sendMessage(Messages.RemoveCommand(command, item.GetIdentifier()));
                        
                    }
                    else {
                        System.out.println("Some error's here!");
                    }
                }
                else {
                    sender.sendMessage(Messages.ItemExist());
                }
            }
            else {
                sender.sendMessage(Messages.HaventPerm("bcoins.admin"));
            }
        }
        else {
            sender.sendMessage(Messages.BShopUsage(sender.hasPermission("bcoins.admin")));
        }
        
        return false;
    }

}
