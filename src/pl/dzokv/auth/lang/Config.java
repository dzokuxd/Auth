package pl.dzokv.auth.lang;

import net.md_5.bungee.*;
import java.util.*;
import net.md_5.bungee.config.*;
import pl.dzokv.auth.AuthPlugin;
import com.google.common.io.*;
import java.io.*;

public class Config {
    private static File f;
    public static String host;
    public static int port;
    public static String pass;
    public static String user;
    public static String data;
    public static int maxAccount;
    public static String auth;
    public static String main;
    public static int slot;
    public static String message_join;
    public static String message_kick;
    public static String message_cantlogin;
    public static String message_playerisonline;
    public static String message_invalidnick;
    public static String message_multi;
    public static String message_usage;
    public static String message_captcha;
    public static String message_cmdonlynonpremium;
    public static String message_alreadylogin;
    public static String message_dontregister;
    public static String message_password;
    public static String message_paswordpasword;
    public static String message_passwordlenght;
    public static String message_passwordalfa;
    public static String message_login;
    public static String message_register;
    public static String message_remember;
    public static String message_changepassword;
    public static String message_alreadyregister;
    public static String message_alreadyremember;
    public static String message_kick_unregister;
    public static String message_kick_setpremium;
    public static String error_user;
    public static String whitelist_reason;
    public static boolean whitelist_enabled;
    public static List<String> whitelist_users;
    public static String motd;
    public static List<String> admins;
    public static List<String> slot1;
    
    static {
        Config.f = new File(BungeeCord.getInstance().getPluginsFolder().getName() + "/KAuth");
        Config.whitelist_reason = "&cNie jestes na WhiteList!";
        Config.whitelist_enabled = true;
        Config.whitelist_users = Collections.singletonList("dzokv");
        Config.motd = "MOTD! {N}MOTD2";
        Config.host = "host";
        Config.port = 3306;
        Config.pass = "pass";
        Config.user = "user";
        Config.data = "data";
        Config.host = "host";
        Config.slot = 1001;
        Config.maxAccount = 1;
        Config.auth = "login";
        Config.main = "hc";
        Config.message_join = "&cZalogowano z konta {PREMIUM}";
        Config.message_kick = "&c&lCzas logowania dobiegl konca";
        Config.message_cantlogin = "&c&lKolejny raz mozesz polaczyc sie za &4&l{TIME}";
        Config.message_playerisonline = "&c&lGracz o nicku &4&l{PLAYER} &c&ljest juz na serwerze!";
        Config.message_invalidnick = "&c&lTwoj nick zawiera niedozwolone znaki!";
        Config.message_multi = "&c&lWykryto multi-konto, gracze z twoim ip: &4&l{PLAYERS}\n&c&lChcesz pograc wbij na ts &4&lTS";
        Config.message_usage = "&7Poprawne uzycie: &c{USE}";
        Config.message_captcha = "&7Twoj kod captcha: &c{CAPTCHA}";
        Config.message_cmdonlynonpremium = "&cTa komenda nie jest dla graczy premium!";
        Config.message_alreadylogin = "&cJestes juz zalogowany!";
        Config.message_dontregister = "&cNie jestes zarejestrowny!";
        Config.message_password = "&cHaslo jest nie poprawne!";
        Config.message_paswordpasword = "&cHasla nie pasuja do siebie!";
        Config.message_passwordlenght = "&cHaslo musi zawierac 8 znakow!";
        Config.message_passwordalfa = "&cHaslo musi zawierac znak specjalny!";
        Config.message_login = "&aPomyslnie zalogowano!";
        Config.message_register = "&aPomyslnie zalogowano!";
        Config.message_remember = "&aPomyslnie zapamietano haslo!";
        Config.message_changepassword = "&aPomyslnie zmieniono haslo!";
        Config.message_alreadyregister = "&cJestes juz zarejestrowany!";
        Config.message_alreadyremember = "&cTwoje konto jest juz zapamietane!";
        Config.message_kick_unregister = "&c&lTwoje konto zostalo odrejestrowne przez &4&l{ADMIN}";
        Config.message_kick_setpremium = "&c&lTwoje konto zostalo zmienione na &4&l{PREMIUM} &c&lprzez &4&l{ADMIN}";
        Config.error_user = "&4Blad: &cWystapil nie znany blad, Wejdz jeszcze raz!";
        admins = Arrays.asList("dzokv","mwqx");
        slot1 = Arrays.asList("dzokv","mwqx");
    }
    
    public static void getConfig() {
        try {
            final Configuration c = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Config.f, "config.yml"));
            Config.whitelist_reason = c.getString("config.wl.reason");
            Config.whitelist_users = c.getStringList("config.wl.users");
            Config.motd = c.getString("config.motd");
            Config.host = c.getString("config.mysql.host");
            Config.port = c.getInt("config.mysql.port");
            Config.pass = c.getString("config.mysql.pass");
            Config.user = c.getString("config.mysql.user");
            Config.data = c.getString("config.mysql.data");
            Config.maxAccount = c.getInt("config.auth.settings.maxaccount");
            Config.auth = c.getString("config.auth.settings.auth");
            Config.main = c.getString("config.auth.settings.main");
            Config.error_user = c.getString("config.auth.message.error");
            Config.message_join = c.getString("config.auth.message.join");
            Config.message_kick = c.getString("config.auth.message.kick");
            Config.message_cantlogin = c.getString("config.auth.message.cantlogin");
            Config.message_playerisonline = c.getString("config.auth.message.playerisonline");
            Config.message_invalidnick = c.getString("config.auth.message.invalidnick");
            Config.message_multi = c.getString("config.auth.message.multi");
            Config.message_captcha = c.getString("config.auth.message.captcha");
            Config.message_cmdonlynonpremium = c.getString("config.auth.message.cmdonlynonpremium");
            Config.message_alreadylogin = c.getString("config.auth.message.alreadylogin");
            Config.message_dontregister = c.getString("config.auth.message.dontregister");
            Config.message_password = c.getString("config.auth.message.password");
            Config.message_paswordpasword = c.getString("config.auth.message.passwordpassword");
            Config.message_passwordlenght = c.getString("config.auth.message.passwordlenght");
            Config.message_passwordalfa = c.getString("config.auth.message.passwordalfa");
            Config.message_login = c.getString("config.auth.message.login");
            Config.message_register = c.getString("config.auth.message.register");
            Config.message_remember = c.getString("config.auth.message.remember");
            Config.message_changepassword = c.getString("config.auth.message.changepassword");
            Config.message_alreadyregister = c.getString("config.auth.message.alreadyregister");
            Config.message_alreadyremember = c.getString("config.auth.message.alreadyremember");
            Config.message_kick_unregister = c.getString("config.auth.message.kickunregister");
            Config.message_kick_setpremium = c.getString("config.auth.message.kicksetpremium");
            Config.slot = c.getInt("config.auth.slot");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateConfig() {
        try {
            final Configuration c = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Config.f, "config.yml"));
            c.set("config.wl.reason", Config.whitelist_reason);
            c.set("config.wl.users", Config.whitelist_users);
            c.set("config.motd", Config.motd);
            c.set("config.mysql.host", Config.host);
            c.set("config.mysql.port", Config.port);
            c.set("config.mysql.pass", Config.pass);
            c.set("config.mysql.user", Config.user);
            c.set("config.mysql.data", Config.data);
            c.set("config.auth.settings.maxaccount", Config.maxAccount);
            c.set("config.auth.settings.auth", Config.auth);
            c.set("config.auth.settings.main", Config.main);
            c.set("config.auth.message.error", Config.error_user);
            c.set("config.auth.message.join", Config.message_join);
            c.set("config.auth.message.kick", Config.message_kick);
            c.set("config.auth.message.cantlogin", Config.message_cantlogin);
            c.set("config.auth.message.playerisonline", Config.message_playerisonline);
            c.set("config.auth.message.invalidnick", Config.message_invalidnick);
            c.set("config.auth.message.multi", Config.message_multi);
            c.set("config.auth.message.captcha", Config.message_captcha);
            c.set("config.auth.message.cmdonlynonpremium", Config.message_cmdonlynonpremium);
            c.set("config.auth.message.alreadylogin", Config.message_alreadylogin);
            c.set("config.auth.message.dontregister", Config.message_dontregister);
            c.set("config.auth.message.password", Config.message_password);
            c.set("config.auth.message.passwordpassword", Config.message_paswordpasword);
            c.set("config.auth.message.passwordlenght", Config.message_passwordlenght);
            c.set("config.auth.message.passwordalfa", Config.message_passwordalfa);
            c.set("config.auth.message.login", Config.message_login);
            c.set("config.auth.message.register", Config.message_register);
            c.set("config.auth.message.remember", Config.message_remember);
            c.set("config.auth.message.changepassword", Config.message_changepassword);
            c.set("config.auth.message.alreadyregister", Config.message_alreadyregister);
            c.set("config.auth.message.alreadyremember", Config.message_alreadyremember);
            c.set("config.auth.message.kickunregister", Config.message_kick_unregister);
            c.set("config.auth.message.kicksetpremium", Config.message_kick_setpremium);
            c.set("config.auth.slot", Config.slot);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(c, new File(Config.f, "config.yml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void createConfig() {
        if (!Config.f.isDirectory()) {
            Config.f.mkdir();
            final File configFile = new File(Config.f, "config.yml");
            if (!configFile.exists()) {
                try {
                    configFile.createNewFile();
                    Throwable t = null;
                    try {
                        final InputStream is = AuthPlugin.getPlugin().getResourceAsStream("config.yml");
                        try {
                            final OutputStream os = new FileOutputStream(configFile);
                            try {
                                ByteStreams.copy(is, os);
                            }
                            finally {
                                if (os != null) {
                                    os.close();
                                }
                            }
                            if (is != null) {
                                is.close();
                            }
                        }
                        finally {
                            if (t == null) {
                                final Throwable t2 = null;
                                t = t2;
                            }
                            else {
                                final Throwable t2 = null;
                                if (t != t2) {
                                    t.addSuppressed(t2);
                                }
                            }
                            if (is != null) {
                                is.close();
                            }
                        }
                    }
                    finally {
                        if (t == null) {
                            final Throwable t3 = null;
                            t = t3;
                        }
                        else {
                            final Throwable t3 = null;
                            if (t != t3) {
                                t.addSuppressed(t3);
                            }
                        }
                    }
                }
                catch (IOException e) {
                    throw new RuntimeException("Unable to create configuration file", e);
                }
            }
        }
    }
}
