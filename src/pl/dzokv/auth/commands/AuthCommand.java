package pl.dzokv.auth.commands;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.connection.*;
import pl.dzokv.auth.data.Auth;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.managers.AuthManager;
import pl.dzokv.auth.utils.ChatUtil;
import net.md_5.bungee.*;
import net.md_5.bungee.api.*;

public class AuthCommand extends Command
{
    public AuthCommand() {
        super("auth", (String)null, new String[0]);
    }
    
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer && ((ProxiedPlayer)sender).getServer().getInfo().getName().equalsIgnoreCase(Config.auth)) {
            return;
        }
        if (sender instanceof ProxiedPlayer && !Config.admins.contains(sender.getName())) {
            ChatUtil.sendMessage(sender, "&cNie masz dostepu do tej komendy!");
            return;
        }
        if (args.length == 0) {
            this.help(sender);
            return;
        }
        Label_0821: {
            Label_0622: {
                Label_0337: {
                String s;
                    switch (s = args[0]) {
                        case "register": {
                            break;
                        }
                        case "un": {
                            break Label_0337;
                        }
                        case "reg": {
                            break;
                        }
                        case "set": {
                            break Label_0622;
                        }
                        case "info": {
                            if (args.length != 2) {
                                ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/auth info [gracz]"));
                                return;
                            }
                            Auth auth = AuthManager.getAuth(args[1]);
                            if (auth == null) {
                                ChatUtil.sendMessage(sender, "&4Blad: &cGracz jest juz zarejestrowany!");
                                return;
                            }
                            ChatUtil.sendMessage(sender, "&6Nick: &2" + auth.getName());
                            ChatUtil.sendMessage(sender, "&6premium: &2" + (auth.isPremium() ? "tak" : "nie"));
                            ChatUtil.sendMessage(sender, "&6FirstIP: &2" + auth.getFirstIP());
                            ChatUtil.sendMessage(sender, "&6LastIP: &2" + auth.getLastIP());
                            ChatUtil.sendMessage(sender, "&6Konta z podobnym adresem ip &2" + AuthManager.getAuthIP(auth.getLastIP()));
                            return;
                        }
                        case "unregister": {
                            break Label_0337;
                        }
                        case "setpremium": {
                            break Label_0622;
                        }
                        default:
                            break Label_0821;
                    }
                    if (args.length != 3) {
                        ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/auth register [gracz] [premiun/nonpremium]"));
                        return;
                    }
                    boolean premium = this.premium(args);
                    Auth auth2 = AuthManager.getAuth(args[0]);
                    if (auth2 != null) {
                        ChatUtil.sendMessage(sender, "&4Blad: &cGracz jest juz zarejestrowany!");
                        return;
                    }
                    AuthManager.addAuth(args[1], premium, null, null);
                    ChatUtil.sendMessage(sender, "&7Zarejestrowales gracza &9" + args[1] + " &7z kontem &9" + (premium ? "premium" : "non-premium"));
                    return;
                }
                if (args.length != 2) {
                    ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/auth unregister [gracz]"));
                    return;
                }
                Auth auth = AuthManager.getAuth(args[1]);
                if (auth == null) {
                    ChatUtil.sendMessage(sender, "&4Blad: &cGracz nie istnieje!");
                    return;
                }
                ProxiedPlayer p = BungeeCord.getInstance().getPlayer(args[1]);
                AuthManager.removeAuth(args[0]);
                if (p != null) {
                    p.disconnect(ChatUtil.fixColor(Config.message_kick_unregister.replace("{ADMIN}", sender.getName())));
                }
                ChatUtil.sendMessage(sender, "&7Odrejestrowales gracza &9" + args[1]);
                return;
            }
            if (args.length != 3) {
                ChatUtil.sendMessage(sender, Config.message_usage.replace("{USE}", "/auth setpremium [gracz] [premiun/nonpremium]"));
                return;
            }
            Auth auth = AuthManager.getAuth(args[1]);
            if (auth == null) {
                ChatUtil.sendMessage(sender, "&4&lBlad: Podany gracz nie istnieje!");
                return;
            }
            boolean premium2 = this.premium(args);
            if (auth.isPremium() == premium2) {
                ChatUtil.sendMessage(sender, "&4Blad: &cGracz posiada juz konto: " + (premium2 ? "premium" : "non-premium"));
                return;
            }
            auth.setPremium(premium2);
            ProxiedPlayer p2 = BungeeCord.getInstance().getPlayer(args[1]);
            if (p2 != null) {
                p2.disconnect(ChatUtil.fixColor(Config.message_kick_setpremium.replace("{ADMIN}", sender.getName()).replace("{PREMIUM}", premium2 ? "premium" : "non-premium")));
            }
            ChatUtil.sendMessage(sender, "&7Ustawiles konto gracza &9" + auth.getName() + " &7na &9" + (premium2 ? "premium" : "non-premiumn"));
            return;
        }
        this.help(sender);
    }
    
    private void help(CommandSender sender) {
        ChatUtil.sendMessage(sender, "&7/auth &9&lregister [gracz] [premium]");
        ChatUtil.sendMessage(sender, "&7/auth &9&lunregister [gracz]");
        ChatUtil.sendMessage(sender, "&7/auth &9&lsetpremium [gracz] [premium]");
        ChatUtil.sendMessage(sender, "&7/auth &9&linfo [gracz]");
    }
    
    private boolean premium(String[] args) {
        return args[2].equalsIgnoreCase("tak") || args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("ja") || args[2].equalsIgnoreCase("1") || args[2].equalsIgnoreCase("premium");
    }
}
