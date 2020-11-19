package pl.dzokv.auth.commands.auth;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.*;
import pl.dzokv.auth.data.Auth;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.managers.AuthManager;
import pl.dzokv.auth.tasks.LoginTask;
import pl.dzokv.auth.utils.ChatUtil;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.*;

public class LoginCommand extends Command
{
    public LoginCommand() {
        super("login", (String)null, new String[] { "l" });
    }
    
    public void execute(final CommandSender sender, final String[] args) {
        final Auth auth = AuthManager.getAuth(sender.getName());
        if (auth == null) {
            ChatUtil.sendMessage(sender, Config.error_user);
            return;
        }
        if (auth.isPremium()) {
            ChatUtil.sendMessage(sender, Config.message_cmdonlynonpremium);
            return;
        }
        if (auth.isLogin() || auth.isRemember()) {
            ChatUtil.sendMessage(sender, Config.message_alreadylogin);
            return;
        }
        if (!auth.isRegisted()) {
            ChatUtil.sendMessage(sender, Config.message_dontregister);
            return;
        }
        if (args.length != 1) {
            ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/login [haslo]"));
            return;
        }
        if (!auth.getPassword().equals(AuthManager.md5(args[0]))) {
            ChatUtil.sendMessage(sender, Config.message_password);
            return;
        }
        auth.setLogin(true);
        final ProxiedPlayer p = (ProxiedPlayer)sender;
        if (LoginTask.players.contains(p)) {
            LoginTask.players.remove(p);
        }
        p.connect(BungeeCord.getInstance().getServerInfo(Config.main));
        ChatUtil.sendMessage(sender, Config.message_login);
    }
}
