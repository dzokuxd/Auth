package pl.dzokv.auth.tasks;

import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.*;
import pl.dzokv.auth.AuthPlugin;
import pl.dzokv.auth.data.Auth;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.managers.AuthManager;
import pl.dzokv.auth.utils.ChatUtil;
import java.util.concurrent.*;
import net.md_5.bungee.api.plugin.*;
import java.util.*;

public class LoginTask implements Runnable
{
    public static List<ProxiedPlayer> players;
    
    static {
        LoginTask.players = new ArrayList<ProxiedPlayer>();
    }
    
    public void register() {
        BungeeCord.getInstance().getScheduler().schedule( AuthPlugin.getPlugin(), (Runnable)this, 5L, 5L, TimeUnit.SECONDS);
    }
    
    @Override
    public void run() {
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
