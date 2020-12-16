package cz.nixdevelopment.bcoins.instances;

public class DatabaseInstance {

    private String host, database, user, pass;
    private int port;
    
    public DatabaseInstance(String host, int port, String database, String user, String pass) {
        this.host = host;
        this.database = database;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }
    
    public String GetConnectionString() {
        return "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
    }
    
    public String GetUsername() {
        return this.user;
    }
    
    public String GetPassword() {
        return this.pass;
    }
    
}
