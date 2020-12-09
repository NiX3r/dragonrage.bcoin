package cz.nixdevelopment.bcoins.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.nixdevelopment.bcoins.utils.BCoinsUtil;
import cz.nixdevelopment.bcoins.utils.Messages;
import cz.nixdevelopment.bcoins.utils.MySQL;

public class BCoinCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // send player help message
        if(args.length == 0) {
            sender.sendMessage(Messages.BCoinUsage(sender.hasPermission("bcoins.admin")));
        }
        
        // commands: /bcoin check
        else if(args.length == 1) {
            
            if(args[0].equalsIgnoreCase("check")) {
                double bc = MySQL.getTokens(Bukkit.getPlayer(sender.getName()).getUniqueId().toString());
                sender.sendMessage(Messages.HaveBCoins(bc));
            }
            else {
                sender.sendMessage(Messages.UnknownCommandBCoin());
            }
            
        }
        
        // commands: /bcoin check <nick>
        else if(args.length == 2) {
            
            if(args[0].equalsIgnoreCase("check")) {
                
                if(Bukkit.getPlayer(args[1]) != null) {
                    double bc = MySQL.getTokens(Bukkit.getPlayer(args[1]).getUniqueId().toString());
                    sender.sendMessage(Messages.HasBCoins(bc, args[1]));
                }
                else {
                    sender.sendMessage(Messages.TargetOffline(args[1]));
                }
                
            }
            else {
                sender.sendMessage(Messages.UnknownCommandBCoin());
            }
            
        }
        
        // commands: /bcoin [send | add | remove | set] <nick> <value>
        else if(args.length == 3) {
            
            if(args[0].equalsIgnoreCase("send")) {
                
                if(Bukkit.getPlayer(args[1]) != null) {
                    
                    Player pTaker = Bukkit.getPlayer(args[1]);
                    Player pSender = Bukkit.getPlayer(sender.getName());
                    
                    if(BCoinsUtil.IsDouble(args[2])) {

                        double bcFrom = MySQL.getTokens(pSender.getUniqueId().toString());
                        double bcTo = MySQL.getTokens(pTaker.getUniqueId().toString());
                        double bcSend = Double.parseDouble(args[2]);
                        
                        if(bcSend <= bcFrom) {
                            
                            bcFrom -= bcSend;
                            bcTo += bcSend;
                            
                            MySQL.setTokens(pSender.getUniqueId().toString(), bcFrom);
                            MySQL.setTokens(pTaker.getUniqueId().toString(), bcTo);
                            
                            sender.sendMessage(Messages.SendBCoins(bcFrom, bcSend, args[1]));
                            pTaker.sendMessage(Messages.TakeSendBCoins(bcTo, bcSend, sender.getName()));
                            
                        }
                        else {
                            sender.sendMessage(Messages.TooPoor((bcSend - bcFrom)));
                        }
                        
                    }
                    else {
                        sender.sendMessage(Messages.UncorrectFormatCoins());
                    }
                    
                }
                else {
                    sender.sendMessage(Messages.TargetOffline(args[1]));
                }
                
            }
            else if(args[0].equalsIgnoreCase("add")) {
                
                if(sender.hasPermission("bcoins.admin")) {
                    
                    if(Bukkit.getPlayer(args[1]) != null) {
                        
                        Player pTaker = Bukkit.getPlayer(args[1]);
                        Player pSender = Bukkit.getPlayer(sender.getName());
                        
                        if(BCoinsUtil.IsDouble(args[2])) {

                            double bcFrom = MySQL.getTokens(pSender.getUniqueId().toString());
                            double bcAdd = Double.parseDouble(args[2]);
                            
                            bcFrom += bcAdd;
                            
                            MySQL.setTokens(pTaker.getUniqueId().toString(), bcFrom);
                            
                            sender.sendMessage(Messages.AddBCoins(bcFrom, bcAdd, args[1]));
                            pTaker.sendMessage(Messages.TakeAddBCoins(bcFrom, bcAdd, sender.getName()));
                            
                        }
                        else {
                            sender.sendMessage(Messages.UncorrectFormatCoins());
                        }
                        
                    }
                    else {
                        sender.sendMessage(Messages.TargetOffline(args[1]));
                    }
                    
                }
                else {
                    sender.sendMessage(Messages.HaventPerm("bcoins.admin"));
                }
                
            }
            else if(args[0].equalsIgnoreCase("remove")) {
                
                if(sender.hasPermission("bcoins.admin")) {
                    
                    if(Bukkit.getPlayer(args[1]) != null) {
                        
                        Player pTaker = Bukkit.getPlayer(args[1]);
                        Player pSender = Bukkit.getPlayer(sender.getName());
                        
                        if(BCoinsUtil.IsDouble(args[2])) {

                            double bcFrom = MySQL.getTokens(pSender.getUniqueId().toString());
                            double bcRemove = Double.parseDouble(args[2]);
                            
                            bcFrom -= bcRemove;
                            
                            MySQL.setTokens(pTaker.getUniqueId().toString(), bcFrom);
                            
                            sender.sendMessage(Messages.RemoveBCoins(bcFrom, bcRemove, args[1]));
                            pTaker.sendMessage(Messages.TakeRemoveBCoins(bcFrom, bcRemove, sender.getName()));
                            
                        }
                        else {
                            sender.sendMessage(Messages.UncorrectFormatCoins());
                        }
                        
                    }
                    else {
                        sender.sendMessage(Messages.TargetOffline(args[1]));
                    }
                    
                }
                else {
                    sender.sendMessage(Messages.HaventPerm("bcoins.admin"));
                }
                
            }
            else if(args[0].equalsIgnoreCase("set")) {
                
                if(sender.hasPermission("bcoins.admin")) {
                    
                    if(Bukkit.getPlayer(args[1]) != null) {
                        
                        Player pTaker = Bukkit.getPlayer(args[1]);
                        Player pSender = Bukkit.getPlayer(sender.getName());
                        
                        if(BCoinsUtil.IsDouble(args[2])) {

                            double bcFrom = MySQL.getTokens(pSender.getUniqueId().toString());
                            double bcSet = Double.parseDouble(args[2]);
                            
                            bcFrom = bcSet;
                            
                            MySQL.setTokens(pTaker.getUniqueId().toString(), bcFrom);
                            
                            sender.sendMessage(Messages.SetBCoins(bcFrom, args[1]));
                            pTaker.sendMessage(Messages.TakeSetBCoins(bcFrom, sender.getName()));
                            
                        }
                        else {
                            sender.sendMessage(Messages.UncorrectFormatCoins());
                        }
                        
                    }
                    else {
                        sender.sendMessage(Messages.TargetOffline(args[1]));
                    }
                    
                }
                else {
                    sender.sendMessage(Messages.HaventPerm("bcoins.admin"));
                }
                
            }
            else {
                sender.sendMessage(Messages.UnknownCommandBCoin());
            }
            
        }
        else {
            sender.sendMessage(Messages.BCoinUsage(sender.hasPermission("bcoins.admin")));
        }
        
        return false;
    }

}