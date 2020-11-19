package pl.dzokv.auth.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.utils.ChatUtil;
import pl.dzokv.auth.utils.Util;

public class SlotCommand extends Command {

    public static boolean fake = false;

    public SlotCommand() {
        super("bslot", (String) null, new String[0]);
    }

    public void execute(final CommandSender sender, final String[] args) {
        if (sender instanceof ProxiedPlayer && ((ProxiedPlayer) sender).getServer().getInfo().getName().equalsIgnoreCase(Config.auth)) {
            return;
        }
        if (sender instanceof ProxiedPlayer && !Config.admins.contains(sender.getName())) {
            ChatUtil.sendMessage(sender, "&cNie masz dostepu do tej komendy!");
            return;
        }
        if (args.length < 1) {
            ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/bslot <slot>"));
            return;
        }

        if (args[0].equalsIgnoreCase("fakeon")) {
            fake = true;
            ChatUtil.sendMessage(sender, "&7Fake Sloty &aWlaczone");
            return;
        }
        if (args[0].equalsIgnoreCase("fakeoff")) {
            fake = false;
            ChatUtil.sendMessage(sender, "&7Fake Sloty &cWylaczone");
            return;
        }

        if (!Util.isInteger(args[0])) {
            ChatUtil.sendMessage(sender, "&4Blad: &cTo nie liczba!");
            return;
        }
        int i = Integer.parseInt(args[0]);

        Config.slot = i;
        Config.updateConfig();
        ChatUtil.sendMessage(sender, "&7Ustawiles sloty na &c" + i);
    }
}
