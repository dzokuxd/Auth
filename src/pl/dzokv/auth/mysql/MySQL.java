package pl.dzokv.auth.mysql;

import net.md_5.bungee.*;
import pl.dzokv.auth.AuthPlugin;
import pl.dzokv.auth.utils.Logger;
import java.util.concurrent.*;
import net.md_5.bungee.api.plugin.*;
import java.sql.*;

public class MySQL
{
    private Connection conn;
    private final String host;
    private final int port;
    private final String pass;
    private final String data;
    private final String user;
    
    public MySQL(final String host, final int port, final String pass, final String data, final String user) {
        this.host = host;
        this.port = port;
        this.pass = pass;
        this.data = data;
        this.user = user;
        BungeeCord.getInstance().getScheduler().schedule( AuthPlugin.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                AuthPlugin.getMySQL().execute("SELECT CURTIME()");
            }
        }, 15L, TimeUnit.MINUTES);
    }
    
    public boolean connect() {
        final long now = System.currentTimeMillis();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.data, this.user, this.pass);
            Logger.info("Czas polaczenia " + (System.currentTimeMillis() - now) + "ms");
            return true;
        }
        catch (ClassNotFoundException e) {
            Logger.info("JDBC driver not found! Error: " + e.getMessage());
            e.printStackTrace();
        }
        catch (SQLException e2) {
            Logger.info("Can not connect to a MySQL server! Error: " + e2.getMessage());
            e2.printStackTrace();
        }
        return false;
    }
    
    public void executeUpdate(final String query) {
        if (this.conn == null) {
            this.connect();
        }
        try {
            final Statement st = this.conn.createStatement();
            st.executeUpdate(query);
        }
        catch (SQLException e) {
            this.connect();
            e.printStackTrace();
        }
    }
    
    public void execute(final String query) {
        if (this.conn == null) {
            this.connect();
        }
        try {
            final Statement st = this.conn.createStatement();
            st.execute(query);
        }
        catch (SQLException e) {
            this.connect();
            e.printStackTrace();
        }
    }
    
    public ResultSet executeQuery(final String query) {
        ResultSet rs = null;
        try {
            final Statement st = this.conn.createStatement();
            rs = st.executeQuery(query);
        }
        catch (SQLException e) {
            this.connect();
            e.printStackTrace();
        }
        return rs;
    }
    
    public void disconnect() {
        if (this.conn != null) {
            try {
                this.conn.close();
            }
            catch (SQLException e) {
                Logger.info("Can not close the connection to the MySQL server! Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public void reconnect() {
        this.connect();
    }
    
    public boolean isConnected() {
        try {
            return !this.conn.isClosed() || this.conn == null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Connection getConnection() {
        return this.conn;
    }
}
