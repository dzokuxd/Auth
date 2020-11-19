package pl.dzokv.auth;

import pl.dzokv.auth.commands.*;
import pl.dzokv.auth.commands.auth.*;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.listeners.*;
import pl.dzokv.auth.managers.*;
import pl.dzokv.auth.mysql.*;
import pl.dzokv.auth.tasks.*;
import pl.dzokv.auth.utils.*;
import net.md_5.bungee.*;
import java.security.*;
import net.md_5.bungee.api.plugin.*;

public class AuthPlugin extends Plugin
{
    private static AuthPlugin plugin;
    private static MySQL mySQL;
    private static PluginManager pm;
    
    static {
        AuthPlugin.pm = BungeeCord.getInstance().getPluginManager();
    }
    
    public void onEnable() {
        AuthPlugin.plugin = this;
        Config.createConfig();
        Config.getConfig();
        Logger.info(Config.host);
        this.initMysql();
        Security.setProperty("networkaddress.cache.ttl", "30");
        AuthManager.setup();
        this.registerListener();
        this.registerCommand();
        new LoginTask().register();
    }
    
    public void onDisable() {
        AuthPlugin.plugin = null;
        if (!AuthPlugin.mySQL.connect()) {
            AuthPlugin.mySQL.disconnect();
        }
    }
    
    public static AuthPlugin getPlugin() {
        return AuthPlugin.plugin;
    }
    
    public static MySQL getMySQL() {
        return AuthPlugin.mySQL;
    }
    
    private boolean initMysql() {
        AuthPlugin.mySQL = new MySQL(Config.host, Config.port, Config.pass, Config.data, Config.user);
        final boolean conn = AuthPlugin.mySQL.connect();
        if (conn) {
            AuthPlugin.mySQL.executeUpdate("CREATE TABLE IF NOT EXISTS `auth` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,`name` varchar(32) NOT NULL,`premium` int(1) NOT NULL,`password` text NOT NULL, `register` int(1) NOT NULL, `firstIP` varchar(64) NOT NULL, `lastIP` varchar(64) NOT NULL, `rememberIP` varchar(64) NOT NULL);");
            return true;
        }
        return false;
    }
    
    private void registerListener() {
        AuthPlugin.pm.registerListener(this, new PreLoginListener());
        AuthPlugin.pm.registerListener(this, new ServerConnectListener());
        AuthPlugin.pm.registerListener(this, new PostLoginListener());
        AuthPlugin.pm.registerListener(this, new ChatListener());
        AuthPlugin.pm.registerListener(this, new PlayerDisconectListener());
        AuthPlugin.pm.registerListener(this, new TabCompleteListener());
        AuthPlugin.pm.registerListener(this, new ProxyPingListener());
    }
    
    private void registerCommand() {
        AuthPlugin.pm.registerCommand(this, new ChangeePasswordCommand());
        AuthPlugin.pm.registerCommand(this, new LoginCommand());
        AuthPlugin.pm.registerCommand(this, new RegisterCommand());
        AuthPlugin.pm.registerCommand(this, new RememberCommand());
        AuthPlugin.pm.registerCommand(this, new AuthCommand());
        AuthPlugin.pm.registerCommand(this, new MotdCommand());
        AuthPlugin.pm.registerCommand(this, new WhiteListCommand());
        AuthPlugin.pm.registerCommand(this, new SlotCommand());
        AuthPlugin.pm.registerCommand(this, new ConfigCommand());
    }
}
