package pl.dzokv.auth.tasks;

import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.*;
import pl.dzokv.auth.AuthPlugin;
import pl.dzokv.auth.data.Auth;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.listeners.ProxyPingListener;
import pl.dzokv.auth.managers.AuthManager;
import pl.dzokv.auth.utils.ChatUtil;
import java.util.concurrent.*;
import net.md_5.bungee.api.plugin.*;
import java.util.*;

public class LoginTask implements Runnable
{
    public static List<ProxiedPlayer> players;
    public int i = 0;
    
    static {
        LoginTask.players = new ArrayList<ProxiedPlayer>();
    }
    
    public void register() {
        BungeeCord.getInstance().getScheduler().schedule( AuthPlugin.getPlugin(), (Runnable)this, 5L, 5L, TimeUnit.SECONDS);
    }
    
    @Override
    public void run() {
        i += 1;
        if(i >= 12) {
            i = 0;
            for (String admin : Config.admins) {
                ProxiedPlayer proxiedPlayer = BungeeCord.getInstance().getPlayer(admin);
                if(proxiedPlayer != null && proxiedPlayer.isConnected()) {
                    proxiedPlayer.sendMessage(ChatUtil.fixColor("&6Ilosc odswiezen na minute: "+ ProxyPingListener.refersh));
                }
            }
        }
        System.out.println(i);
        for (final ProxiedPlayer p : LoginTask.players) {
            final Auth auth = AuthManager.getAuth(p.getName());
            if (auth == null) {
                p.disconnect(ChatUtil.fixColor(Config.error_user));
            }
            else if (auth.isRegisted()) {
                ChatUtil.sendMessage(p, Config.message_usage.replace("{USE}", "/login [haslo]"));
            }
            else {
                ChatUtil.sendMessage(p, Config.message_usage.replace("{USE}", "/register [captcha] [haslo] [powtorz_haslo]"));
                ChatUtil.sendMessage(p, Config.message_captcha.replace("{CAPTCHA}", auth.getCaptcha()));
            }
        }
    }
}
