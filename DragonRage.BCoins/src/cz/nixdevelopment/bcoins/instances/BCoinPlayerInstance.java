package cz.nixdevelopment.bcoins.instances;

import java.util.UUID;

public class BCoinPlayerInstance {

    private String nick;
    private double bc;
    private UUID uuid;
    
    public BCoinPlayerInstance(UUID uuid, String nick, double bc) {
        this.uuid = uuid;
        this.nick = nick;
        this.bc = bc;
    }
    
    public String GetNick() {
        return this.nick;
    }
    public double GetBC() {
        return this.bc;
    }
    public UUID GetUUID() {
        return this.uuid;
    }
    
    public void SetNick(String nick) {
        this.nick = nick;
    }
    public void SetBC(double bc) {
        this.bc = bc;
    }
    
}
