package pl.dzokv.auth.commands.auth;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.*;
import pl.dzokv.auth.data.Auth;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.managers.AuthManager;
import pl.dzokv.auth.utils.ChatUtil;
import net.md_5.bungee.api.connection.*;

public class RememberCommand extends Command
{
    public RememberCommand() {
        super("remember", (String)null, new String[] { "zapamietaj" });
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
        if (!auth.isLogin()) {
            ChatUtil.sendMessage(sender, Config.message_alreadylogin);
            return;
        }
        if (!auth.isRegisted()) {
            ChatUtil.sendMessage(sender, Config.message_dontregister);
            return;
        }
        if (auth.isRemember()) {
            ChatUtil.sendMessage(sender, Config.message_alreadyremember);
            return;
        }
        if (args.length != 1) {
            ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/zapamietaj [haslo]"));
            return;
        }
        if (!auth.getPassword().equals(AuthManager.md5(args[0]))) {
            ChatUtil.sendMessage(sender, Config.message_password);
            return;
        }
        final ProxiedPlayer p = (ProxiedPlayer)sender;
        auth.setRememberIP(p.getAddress().getAddress().getHostAddress());
        ChatUtil.sendMessage(sender, Config.message_remember);
    }
}
