package pl.dzokv.auth.listeners;

import net.md_5.bungee.api.plugin.*;
import java.util.regex.*;
import java.util.*;
import net.md_5.bungee.api.event.*;
import pl.dzokv.auth.data.Auth;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.managers.AuthManager;
import pl.dzokv.auth.utils.ChatUtil;
import pl.dzokv.auth.utils.DataUtil;
import net.md_5.bungee.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.event.*;

public class PreLoginListener implements Listener
{
    private static Pattern wzorzec;
    private static HashMap<String, Long> times;
    
    static {
        PreLoginListener.wzorzec = Pattern.compile("^[0-9a-zA-Z-_]+$");
        PreLoginListener.times = new HashMap<String, Long>();
    }
    
    @EventHandler
    public void onPreLogin(final PreLoginEvent e) {
        if (e.isCancelled()) {
            return;
        }
        final String nick = e.getConnection().getName();
        if (Config.whitelist_enabled && !Config.whitelist_users.contains(nick)) {
            e.setCancelled(true);
            e.setCancelReason(ChatUtil.fixColor(Config.whitelist_reason));
            return;
        }
        if (BungeeCord.getInstance().getPlayers().size() >= Config.slot && !Config.admins.contains(nick) && !Config.slot1.contains(nick)) {
            e.setCancelReason(ChatUtil.fixColor("&6Serwer jest przeciazony! \nSprobuj za chwile!"));
            e.setCancelled(true);
            return;
        }
        final Long l = PreLoginListener.times.get(e.getConnection().getAddress().getAddress().getHostAddress());
        if (l != null && l > System.currentTimeMillis() && !Config.admins.contains(nick)) {
            e.setCancelled(true);
            e.setCancelReason(ChatUtil.fixColor(Config.message_cantlogin.replace("{TIME}", DataUtil.secondsToString(l))));
            return;
        }
        final ProxiedPlayer p = BungeeCord.getInstance().getPlayer(e.getConnection().getName());
        if (BungeeCord.getInstance().getPlayers().contains(p)) {
            e.setCancelled(true);
            e.setCancelReason(ChatUtil.fixColor(Config.message_playerisonline.replace("{PLAYER}", nick)));
            return;
        }
        if (!PreLoginListener.wzorzec.matcher(nick).find()) {
            e.setCancelled(true);
            e.setCancelReason(ChatUtil.fixColor(Config.message_invalidnick));
            return;
        }
        final Auth auth = AuthManager.getAuth(nick);
        if (auth == null) {
            final boolean premium = AuthManager.hasPaid(nick);
            AuthManager.addAuth(nick, premium, null, e.getConnection().getAddress().getAddress().getHostAddress());
            e.getConnection().setOnlineMode(premium);
        }
        else {
            if (!auth.getName().equals(nick)) {
                e.setCancelReason(ChatUtil.fixColor("&4Blad: &cTwoj poprawny nick to &4" + auth.getName()));
                e.setCancelled(true);
                return;
            }
            e.getConnection().setOnlineMode(auth.isPremium());
        }
        PreLoginListener.times.put(e.getConnection().getAddress().getAddress().getHostAddress(), System.currentTimeMillis() + 10000L);
    }
}
