package cz.nixdevelopment.bcoins.utils;

import cz.nixdevelopment.bcoins.BCoins;
import cz.nixdevelopment.bcoins.events.ShopItemEvent;

public class Messages {

    public static String BCoinUsage(Boolean IsAdmin) {
        
        if(!IsAdmin) {
            return "§4§l-----===== §cBCoins §4§l=====-----\n" +
                   "§c/bcoin check §7- zobrazi pocet BCoinu\n" +
                   "§c/bcoin check <hrac> §7- zobrazi pocet BCoinu\n" +
                   "§c/bcoin send <hrac> <castka>§7- zobrazi pocet BCoinu";
        }
        else {
            return "§4§l-----===== §cBCoins §4§l=====-----\n" +
                   "§c/bcoin check §7- zobrazi pocet BCoinu\n" +
                   "§c/bcoin check <hrac> §7- zobrazi pocet BCoinu\n" +
                   "§c/bcoin send <hrac> <castka>§7- zobrazi pocet BCoinu\n" +
                   "§c/bcoin add <hrac> <castka> §7- prida hraci BCoiny\n" +
                   "§c/bcoin remove <hrac> <castka> §7- odebere hraci BCoiny\n" +
                   "§c/bcoin set <hrac> <castka> §7- nastavi hraci BCoiny";
        }
        
    }
    
    public static String BShopUsage(Boolean IsAdmin) {
        if(!IsAdmin) {
            return "§4§l-----===== §cBShop §4§l=====-----\n" +
                   "§c/bshop list §7- zobrazi pocet BCoinu\n" +
                   "§c/bshop buy <klic> §7- zobrazi pocet BCoinu\n" +
                   "§c/bshop buy <klic> <hrac>§7- zobrazi pocet BCoinu";
        }
        else {
            return "§4§l-----===== §cBShop §4§l=====-----\n" +
                   "§c/bshop list §7- zobrazi pocet BCoinu\n" +
                   "§c/bshop buy <klic> §7- zobrazi pocet BCoinu\n" +
                   "§c/bshop buy <klic> <hrac>§7- zobrazi pocet BCoinu\n" +
                   "§c/bshop give <klic> <hrac> §7- prida hraci BCoiny\n" +
                   "§c/bshop create <klic> <castka> §7- odebere hraci BCoiny\n" +
                   "§c/bshop remove <klic> §7- nastavi hraci BCoiny\n" +
                   "§c/bshop cmd <klic> list §7- odebere hraci BCoiny\n" +
                   "§c/bshop cmd <klic> add <cmd> §7- odebere hraci BCoiny\n" +
                   "§c/bshop cmd <klic> remove <cmd> §7- odebere hraci BCoiny\n";
        }
    }
    
    public static String ListBShop() {
        String output = "§4§l-----===== §cBShop Predmety §4§l=====-----\n";
        for(ShopItemEvent sie : BCoins.items) {
            output += "§7 ,§c" + sie.GetIdentifier() + "§7[" + sie.GetCost() + "BC]";
        }
        return output.replaceFirst(" ,", "");
    }
    
    public static String ListItemCommands(String identifier) {
        String output = "§4§l-----===== §cBShop Predmety §4§l=====-----§7\n";
        for(ShopItemEvent sie : BCoins.items) {
            if(sie.GetIdentifier().equals(identifier)) {
                if(sie.GetCommands() != null) {
                    for(String cmd : sie.GetCommands()) {
                        output += "\n - " + cmd;
                    }
                }
                else {
                    output += "\nItem neni nastaven";
                }
            }
        }
        return output.replaceFirst("\n", "");
    }
    
    public static String ItemNotExist() {
        return BCoins.Prefix + " Item neexistuje. Napis §c/bshop list";
    }
    
    public static String ItemExist() {
        return BCoins.Prefix + " Item jiz existuje. Napis §c/bshop list";
    }
    
    public static String RemoveItem(String identifier) {
        return BCoins.Prefix + " Uspesne jsi odebral item §c" + identifier;
    }
    public static String CreateItem(String identifier) {
        return BCoins.Prefix + " Uspesne jsi vytvoril item §c" + identifier;
    }
    
    public static String UnknownCommandBCoin() {
        return BCoins.Prefix + " Neznamy prikaz. Napis §c/bcoin";
    }
    
    public static String UnknownCommandBShop() {
        return BCoins.Prefix + " Neznamy prikaz. Napis §c/bshop";
    }
    
    public static String HaventPerm(String perm) {
        return BCoins.Prefix + " Bohuzel nemas povoleni na tuto akci. Chybi Ti: §c" + perm;
    }
    
    public static String TargetOffline(String target) {
        return BCoins.Prefix + " Neznamy prikaz. Napis §c/bcoin";
    }
    
    public static String UncorrectFormatCoins() {
        return BCoins.Prefix + " Nenapsal jsi spravny format BCoinu! Musis napsat cislo";
    }
    
    public static String AddCommand(String command, String identifier) {
        return BCoins.Prefix + " Uspesne jsi pridal prikaz do itemu §c" + identifier + "§7:\n" + command;
    }
    
    public static String RemoveCommand(String command, String identifier) {
        return BCoins.Prefix + " Uspesne jsi odebral prikaz z itemu §c" + identifier + "§7:\n" + command;
    }
    
    public static String BoughtItem(double value, String identifier) {
        return BCoins.Prefix + " Koupil jsi item §c" + identifier + "§7. Zbyva Ti jeste §c" + value + "BC";
    }
    
    public static String BoughtItem(double value, String identifier, String nick) {
        return BCoins.Prefix + " Koupil jsi hraci §c" + nick + " §7item §c" + identifier + "§7. Zbyva Ti jeste §c" + value + "BC";
    }
    
    public static String BoughtYouItem(String identifier, String nick) {
        return BCoins.Prefix + " Hrac §c" + nick + " §7Ti koupil item §c" + identifier;
    }
    
    public static String GiveItem(String identifier, String nick) {
        return BCoins.Prefix + " Dal jsi hraci §c" + nick + " §7item §c" + identifier;
    }
    
    public static String GiveYouItem(String identifier, String nick) {
        return BCoins.Prefix + " Admin §c" + nick + " §7Ti dal item §c" + identifier;
    }
    
    public static String SetBCoins(double value, String nick) {
        return BCoins.Prefix + " Nastavil jsi hraci §c" + nick + "§7 castku §c" + value + "BC";
    }
    
    public static String TakeSetBCoins(double value, String nick) {
        return BCoins.Prefix + " Admin §c" + nick + "§7 ti nastavil castku §c" + value + "BC";
    }
    
    public static String AddBCoins(double value, double add, String nick) {
        return BCoins.Prefix + " Pridal jsi hraci §c" + nick + "§7 castku §c" + add + "BC §7 nyni vlastni §c" + value + " BC";
    }
    
    public static String TakeAddBCoins(double value, double add, String nick) {
        return BCoins.Prefix + " Admin §c" + nick + "§7 Ti pridal castku §c" + add + "BC §7 nyni mas §c" + value + " BC";
    }
    
    public static String RemoveBCoins(double value, double add, String nick) {
        return BCoins.Prefix + " Odebral jsi hraci §c" + nick + "§7 castku §c" + add + "BC §7 nyni vlastni §c" + value + " BC";
    }
    
    public static String TakeRemoveBCoins(double value, double add, String nick) {
        return BCoins.Prefix + " Admin §c" + nick + "§7 Ti odebral castku §c" + add + "BC §7 nyni mas §c" + value + " BC";
    }
    
    public static String SendBCoins(double value, double send, String nick) {
        return BCoins.Prefix + " Poslal jsi hraci §c" + nick + "§7 castku §c" + send + "BC §7 zbyva Ti §c" + value + " BC";
    }
    
    public static String TakeSendBCoins(double value, double send, String nick) {
        return BCoins.Prefix + " Hrac §c" + nick + "§7 Ti poslal castku §c" + send + "BC §7 nyni mas §c" + value + " BC";
    }
    
    public static String TooPoor(double value) {
        return BCoins.Prefix + " Na tuto akci nemas dostatecny obnos. Chybi Ti §c" + value + " BC";
    }
    
    public static String HaveBCoins(double value) {
        return BCoins.Prefix + " Mas " + value + " BC";
    }
    
    public static String HasBCoins(double value, String nick) {
        return BCoins.Prefix + " Hrac " + nick + " ma " + value + " BC";
    }
    
}
