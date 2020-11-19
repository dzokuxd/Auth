package pl.dzokv.auth.listeners;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.event.*;
import pl.dzokv.auth.data.Auth;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.managers.AuthManager;
import pl.dzokv.auth.tasks.LoginTask;
import pl.dzokv.auth.utils.ChatUtil;
import net.md_5.bungee.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.event.*;

public class ServerConnectListener implements Listener
{
    @EventHandler
    public void onConncect(final ServerConnectEvent e) {
        final ProxiedPlayer p = e.getPlayer();
        final Auth auth = AuthManager.getAuth(p.getName());
        if (auth == null) {
            p.disconnect(ChatUtil.fixColor(Config.error_user));
            return;
        }
        if (e.getTarget().getName().equalsIgnoreCase(Config.auth)) {
            if (auth.isPremium() || auth.isRemember()) {
                e.setTarget(BungeeCord.getInstance().getServerInfo(Config.main));
                return;
            }
            if (!LoginTask.players.contains(p)) {
                LoginTask.players.add(p);
            }
        }
    }
}
