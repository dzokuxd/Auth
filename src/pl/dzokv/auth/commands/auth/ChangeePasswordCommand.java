package pl.dzokv.auth.commands.auth;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.*;
import pl.dzokv.auth.data.Auth;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.managers.AuthManager;
import pl.dzokv.auth.utils.ChatUtil;

public class ChangeePasswordCommand extends Command
{
    public ChangeePasswordCommand() {
        super("changeepassword", (String)null, new String[] { "zmienhaslo" });
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
        if (args.length != 3) {
            ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/zmienhaslo [haslo] [nowe_haslo] [powtorz_nowe_haslo]"));
            return;
        }
        if (!auth.getPassword().equals(AuthManager.md5(args[0]))) {
            ChatUtil.sendMessage(sender, Config.message_password);
            return;
        }
        final String pass = args[1];
        final String pass2 = args[2];
        if (!pass.equals(pass2)) {
            ChatUtil.sendMessage(sender, Config.message_paswordpasword);
            return;
        }
        auth.setPassword(AuthManager.md5(pass));
        ChatUtil.sendMessage(sender, Config.message_changepassword);
    }
}
