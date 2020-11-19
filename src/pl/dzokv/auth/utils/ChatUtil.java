package pl.dzokv.auth.utils;

import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.*;
import java.util.*;

public class ChatUtil
{

    public static String fixColor(final String string) {
        if (string.equalsIgnoreCase("")) {
            return string;
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }
    
    public static void sendMessage(final ProxiedPlayer p, final String string) {
        p.sendMessage(fixColor(string));
    }
    
    public static void sendMessage(final CommandSender sender, final String string) {
        sender.sendMessage(fixColor(string));
    }
    
    public static void sendMessage(final Collection<? extends CommandSender> collection, final String msg) {
        for (final CommandSender cs : collection) {
            cs.sendMessage(fixColor(msg));
        }
    }
}
