package pl.dzokv.auth.listeners;

import net.md_5.bungee.api.event.*;
import pl.dzokv.auth.AuthPlugin;
import pl.dzokv.auth.data.Auth;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.managers.AuthManager;
import pl.dzokv.auth.utils.AuthUtil;
import pl.dzokv.auth.utils.ChatUtil;
import net.md_5.bungee.*;
import net.md_5.bungee.api.connection.*;
import java.util.concurrent.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.event.*;

public class PostLoginListener implements Listener
{
    @EventHandler
    public void onPostLogin(final PostLoginEvent e) {
        final ProxiedPlayer p = e.getPlayer();
        final Auth auth = AuthManager.getAuth(p.getName());
        if (auth == null) {
            p.disconnect(ChatUtil.fixColor(Config.error_user));
            return;
        }
        if (auth.getFirstIP() == null || auth.getFirstIP().equals("null")) {
            auth.setFirstIP(p.getAddress().getAddress().getHostAddress());
        }
        auth.setLastIP(p.getAddress().getAddress().getHostAddress());
        ChatUtil.sendMessage(p, Config.message_join.replace("{PREMIUM}", auth.isPremium() ? "premium" : "non-premium"));
        if (auth.isPremium() || auth.isRemember()) {
            return;
        }
        auth.setLogin(false);
        auth.setCaptcha(AuthUtil.generateCaptcha());
        BungeeCord.getInstance().getScheduler().schedule( AuthPlugin.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                if (!p.isConnected()) {
                    return;
                }
                if (auth.isLogin() || auth.isPremium() || auth.isRemember()) {
                    return;
                }
                p.disconnect(ChatUtil.fixColor(Config.message_kick));
            }
        }, 60L, TimeUnit.SECONDS);
    }
}
