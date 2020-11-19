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

public class RegisterCommand extends Command
{
    public RegisterCommand() {
        super("register", (String)null, new String[] { "reg" });
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
        if (auth.isRegisted()) {
            ChatUtil.sendMessage(sender, Config.message_alreadyregister);
            return;
        }
        if (args.length != 3) {
            ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/register [captcha] [haslo] [powtorz_haslo]"));
            return;
        }
        if (!auth.getCaptcha().equals(args[0])) {
            ChatUtil.sendMessage(sender, Config.message_captcha.replace("{CAPTCHA}", auth.getCaptcha()));
            return;
        }
        final String pass = args[1];
        final String pass2 = args[2];
        if (!pass.equals(pass2)) {
            ChatUtil.sendMessage(sender, Config.message_changepassword);
            return;
        }
        auth.setLogin(true);
        auth.setRegisted(true);
        auth.setPassword(AuthManager.md5(pass));
        final ProxiedPlayer p = (ProxiedPlayer)sender;
        if (LoginTask.players.contains(p)) {
            LoginTask.players.remove(p);
        }
        p.connect(BungeeCord.getInstance().getServerInfo(Config.main));
        ChatUtil.sendMessage(sender, Config.message_register);
    }
}
