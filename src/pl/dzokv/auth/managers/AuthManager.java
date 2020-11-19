package pl.dzokv.auth.managers;

import java.util.concurrent.*;

import pl.dzokv.auth.AuthPlugin;
import pl.dzokv.auth.data.Auth;
import pl.dzokv.auth.utils.Logger;
import java.util.*;
import java.sql.*;
import java.net.*;
import javax.net.ssl.*;
import java.security.*;

public class AuthManager
{
    private static ConcurrentHashMap<String, Auth> auths;
    
    static {
        AuthManager.auths = new ConcurrentHashMap<String, Auth>();
    }
    
    public static Auth getAuth(final String nick) {
        for (final Auth auth : AuthManager.auths.values()) {
            if (auth.getName().equalsIgnoreCase(nick)) {
                return auth;
            }
        }
        return null;
    }
    
    public static void addAuth(final String name, final boolean premium, final String password, final String ip) {
        final Auth auth = new Auth(name, premium, password, ip);
        AuthManager.auths.put(name, auth);
    }
    
    public static void removeAuth(final String name) {
        AuthManager.auths.remove(name);
        AuthPlugin.getMySQL().executeUpdate("DELETE FROM `auth` WHERE `name` = '" + name + "'");
    }
    
    public static List<String> getAuthIP(final String ip) {
        final List<String> name = new ArrayList<String>();
        for (final Auth auth : AuthManager.auths.values()) {
            if (!auth.getFirstIP().equalsIgnoreCase("null") || (auth.getFirstIP() != null && auth.getFirstIP().equals(ip)) || !auth.getLastIP().equalsIgnoreCase("null") || (auth.getLastIP() != null && auth.getLastIP().equals(ip))) {
                name.add(auth.getName());
            }
        }
        return name;
    }
    
    public static void setup() {
        final ResultSet rs = AuthPlugin.getMySQL().executeQuery("SELECT * FROM `auth`");
        try {
            while (rs.next()) {
                final Auth u = new Auth(rs);
                AuthManager.auths.put(u.getName(), u);
            }
            rs.close();
            Logger.info("Loaded " + AuthManager.auths.size() + " auths!");
        }
        catch (SQLException e) {
            Logger.info("An error occurred while loading users! Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static boolean hasPaid(final String nick) {
        try {
            final URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + nick);
            final HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
            conn.connect();
            return conn.getResponseCode() == 200;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static String md5(final String input) {
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            final byte[] result = mDigest.digest(input.getBytes());
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; ++i) {
                sb.append(Integer.toString((result[i] & 0xFF) + 256, 16).substring(1));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }
}
