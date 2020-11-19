package pl.dzokv.auth.commands;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.connection.*;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.utils.ChatUtil;

public class ConfigCommand extends Command
{
    public ConfigCommand() {
        super("bconfig");
    }
    
    public void execute(final CommandSender sender, final String[] args) {
        if (sender instanceof ProxiedPlayer && ((ProxiedPlayer)sender).getServer().getInfo().getName().equalsIgnoreCase(Config.auth)) {
            return;
        }
        if (sender instanceof ProxiedPlayer && !Config.admins.contains(sender.getName())) {
            ChatUtil.sendMessage(sender, "&cNie masz dostepu do tej komendy!");
            return;
        }
        Config.getConfig();
        ChatUtil.sendMessage(sender, "&cCfg zaladowany!");
    }
}
