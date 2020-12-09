package cz.nixdevelopment.bcoins.events;

import java.util.ArrayList;

public class ShopItemEvent {

    private String identifier;
    private double cost;
    private ArrayList<String> commands = new ArrayList<String>();
    
    public ShopItemEvent(String identifier, double cost) {
        this.identifier = identifier;
        this.cost = cost;
    }
    
    public String GetIdentifier() {
        return this.identifier;
    }
    public double GetCost() {
        return this.cost;
    }
    public ArrayList<String> GetCommands(){
        return this.commands;
    }
    
    public void SetCost(double value) {
        this.cost = value;
    }
    public void SetCommands(ArrayList<String> commands) {
        this.commands = commands;
    }
    public void AddCommand(String value) {
        this.commands.add(value);
    }
    public void RemCommand(String value) {
        this.commands.remove(value);
    }
    
}
