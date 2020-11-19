package pl.dzokv.auth.data;

import java.sql.*;
import net.md_5.bungee.*;
import pl.dzokv.auth.AuthPlugin;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.connection.*;

public class Auth
{
    private String name;
    private boolean premium;
    private boolean registerd;
    private String password;
    private String firstIP;
    private String lastIP;
    private String rememberIP;
    private boolean login;
    private String captcha;
    
    public Auth(final String name, final boolean premium, final String password, final String firstIP) {
        this.name = name;
        this.premium = premium;
        this.password = password;
        this.firstIP = firstIP;
        this.lastIP = firstIP;
        this.rememberIP = null;
        this.login = false;
        this.insert();
    }
    
    public Auth(final ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.premium = (rs.getInt("premium") == 1);
        this.registerd = (rs.getInt("register") == 1);
        this.password = rs.getString("password");
        this.firstIP = rs.getString("firstIP");
        this.lastIP = rs.getString("lastIP");
        this.rememberIP = rs.getString("rememberIP");
    }
    
    public String getCaptcha() {
        return this.captcha;
    }
    
    public void setCaptcha(final String captcha) {
        this.captcha = captcha;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public boolean isPremium() {
        return this.premium;
    }
    
    public void setPremium(final boolean premium) {
        this.premium = premium;
        BungeeCord.getInstance().getScheduler().runAsync( AuthPlugin.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                AuthPlugin.getMySQL().executeUpdate("UPDATE `auth` SET `premium` ='" + (premium ? 1 : 0) + "' WHERE `name` ='" + Auth.this.getName() + "'");
            }
        });
    }
    
    public void setRegisted(final boolean registerd) {
        this.registerd = registerd;
        BungeeCord.getInstance().getScheduler().runAsync(AuthPlugin.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                AuthPlugin.getMySQL().executeUpdate("UPDATE `auth` SET `register` ='" + (registerd ? 1 : 0) + "' WHERE `name` ='" + Auth.this.getName() + "'");
            }
        });
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
        BungeeCord.getInstance().getScheduler().runAsync(AuthPlugin.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                AuthPlugin.getMySQL().executeUpdate("UPDATE `auth` SET `password` ='" + password + "' WHERE `name` ='" + Auth.this.getName() + "'");
            }
        });
    }
    
    public void setFirstIP(final String ip) {
        this.firstIP = ip;
        BungeeCord.getInstance().getScheduler().runAsync(AuthPlugin.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                AuthPlugin.getMySQL().executeUpdate("UPDATE `auth` SET `fistIP` ='" + ip + "' WHERE `name` ='" + Auth.this.getName() + "'");
            }
        });
    }
    
    public void setLastIP(final String ip) {
        this.lastIP = ip;
        BungeeCord.getInstance().getScheduler().runAsync(AuthPlugin.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                AuthPlugin.getMySQL().executeUpdate("UPDATE `auth` SET `lastIP` ='" + ip + "' WHERE `name` ='" + Auth.this.getName() + "'");
            }
        });
    }
    
    public void setRememberIP(final String ip) {
        this.rememberIP = ip;
        BungeeCord.getInstance().getScheduler().runAsync(AuthPlugin.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                AuthPlugin.getMySQL().executeUpdate("UPDATE `auth` SET `rememberIP` ='" + ip + "' WHERE `name` ='" + Auth.this.getName() + "'");
            }
        });
    }
    
    public boolean isLogin() {
        return this.login;
    }
    
    public void setLogin(final boolean login) {
        this.login = login;
    }
    
    public boolean isRegisted() {
        return this.registerd;
    }
    
    public void insert() {
        AuthPlugin.getMySQL().executeUpdate("INSERT INTO `auth`(`id`, `name`, `premium`, `password`, `register`, `firstIP`, `lastIP`, `rememberIP`) VALUES (NULL,'" + this.getName() + "','" + (this.isPremium() ? 1 : 0) + "','" + this.getPassword() + "','" + (this.isRegisted() ? 1 : 0) + "','" + this.getFirstIP() + "','" + this.getLastIP() + "','" + this.getRememberIP() + "')");
    }
    
    public ProxiedPlayer getPlayer() {
        return BungeeCord.getInstance().getPlayer(this.getName());
    }
    
    public String getFirstIP() {
        return this.firstIP;
    }
    
    public String getLastIP() {
        return this.lastIP;
    }
    
    public String getRememberIP() {
        return this.rememberIP;
    }
    
    public boolean isRemember() {
        return this.getPlayer().getAddress().getAddress().getHostAddress().equals(this.getRememberIP());
    }
}
