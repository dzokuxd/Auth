package pl.dzokv.auth.commands;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.connection.*;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.utils.ChatUtil;
import pl.dzokv.auth.utils.Logger;
import net.md_5.bungee.api.*;

public class WhiteListCommand extends Command
{
    public WhiteListCommand() {
        super("bwhitelist", (String)null, new String[] { "bwl" });
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
            ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/bwl <on|off|add|remove|list|reason>"));
            return;
        }
        Label_0816: {
            Label_0702: {
                Label_0498: {
                    Label_0376: {
                        Label_0352: {
                            final String s3;
                            switch (s3 = args[0]) {
                                case "reason": {
                                    break Label_0702;
                                }
                                case "remove": {
                                    break Label_0498;
                                }
                                case "on": {
                                    break;
                                }
                                case "add": {
                                    break Label_0376;
                                }
                                case "off": {
                                    break Label_0352;
                                }
                                case "list": {
                                    String list = "";
                                    for (int i = 0; i < Config.whitelist_users.size(); ++i) {
                                        list = String.valueOf(list) + "&8, &6" + Config.whitelist_users.get(i);
                                    }
                                    final String s = list.replaceFirst("&8, &6", "");
                                    ChatUtil.sendMessage(sender, "&7Lista graczy na whitelist: &6" + s);
                                    return;
                                }
                                default:
                                    break Label_0816;
                            }
                            if (Config.whitelist_enabled) {
                                ChatUtil.sendMessage(sender, "&4Blad: &cWhitelist jest juz on!");
                                return;
                            }
                            Config.whitelist_enabled = true;
                            ChatUtil.sendMessage(sender, "&7Whitelist zostala wlaczona!");
                            return;
                        }
                        if (!Config.whitelist_enabled) {
                            ChatUtil.sendMessage(sender, "&4Blad: &cWhitelist jest off!");
                            return;
                        }
                        Config.whitelist_enabled = false;
                        ChatUtil.sendMessage(sender, "&7Whitelist zostala wylaczona!");
                        return;
                    }
                    if (args.length < 2) {
                        ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/wl add <nick>"));
                        return;
                    }
                    final String nick = args[1];
                    if (Config.whitelist_users.contains(nick)) {
                        ChatUtil.sendMessage(sender, "&4Blad: &c" + nick + " jest juz na whitelist!");
                        return;
                    }
                    Logger.info("1");
                    Config.whitelist_users.add(nick);
                    Logger.info("2");
                    Config.updateConfig();
                    Logger.info("3");
                    ChatUtil.sendMessage(sender, "&7Gracz &6" + nick + " &7zostal dodany do whitelist!");
                    return;
                }
                if (args.length < 2) {
                    ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/wl remove <nick>"));
                    return;
                }
                final String nick = args[1];
                if (!Config.whitelist_users.contains(nick)) {
                    ChatUtil.sendMessage(sender, "&4Blad: &c" + nick + " nie jest na whitelist!");
                    return;
                }
                Config.whitelist_users.remove(nick);
                Config.updateConfig();
                ChatUtil.sendMessage(sender, "&7Gracz &6" + nick + " &7zostal usuniety z whitelist!");
                return;
            }
            if (args.length < 2) {
                ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/wl reason <powod>"));
                return;
            }
            String s2 = "";
            for (int i = 1; i < args.length; ++i) {
                s2 = String.valueOf(s2) + " " + args[i];
            }
            final String reason = s2.replaceFirst(" ", "");
            Config.whitelist_reason = ChatUtil.fixColor(reason);
            Config.updateConfig();
            ChatUtil.sendMessage(sender, "&7Ustawiles powod whitelist na: &r" + reason);
            return;
        }
        ChatUtil.sendMessage(sender, "Poprawne uzycie: /whitelist [on|off|add|remove|list]");
    }
}
