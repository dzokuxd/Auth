package pl.dzokv.auth.commands;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.connection.*;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.utils.ChatUtil;

public class MotdCommand extends Command
{
    public MotdCommand() {
        super("motd", (String)null, new String[0]);
    }
    
    public void execute(final CommandSender sender, final String[] args) {
        if (sender instanceof ProxiedPlayer && ((ProxiedPlayer)sender).getServer().getInfo().getName().equalsIgnoreCase(Config.auth)) {
            return;
        }
        if (sender instanceof ProxiedPlayer && !Config.admins.contains(sender.getName())) {
            ChatUtil.sendMessage(sender, "&cNie masz dostepu do tej komendy!");
            return;
        }
        if (args.length < 1) {
            ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/motd <motd>"));
            return;
        }
        String motd = args[0];
        for (int i = 1; i < args.length; ++i) {
            motd = String.valueOf(motd) + " " + args[i];
        }
        Config.motd = motd;
        Config.updateConfig();
        ChatUtil.sendMessage(sender, "&7Ustawiles motd na &r" + motd.replace("{<}", "\u00c2«").replace("{>}", "\u00c2»").replace("{N}", "\n"));
    }
}
