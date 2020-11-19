package pl.dzokv.auth.listeners;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.dzokv.auth.commands.SlotCommand;
import pl.dzokv.auth.lang.Config;
import pl.dzokv.auth.utils.ChatUtil;

import java.util.UUID;

public class ProxyPingListener implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent e) {

        ServerPing ping = e.getResponse();
        ServerPing.Players players = ping.getPlayers();
        ServerPing.Protocol ver = ping.getVersion();

        ver.setProtocol(2);

        players.setMax(Config.slot);
        if (SlotCommand.fake) {
            ver.setName(ChatUtil.fixColor("&7500&8/&c" + players.getMax()));
        } else {
            ver.setName(ChatUtil.fixColor("&7" + BungeeCord.getInstance().getOnlineCount() + "&8/&c" + players.getMax()));
        }

        players.setSample(new ServerPing.PlayerInfo[] { new ServerPing.PlayerInfo("§6MediumHC §7- §cNajlepszy MediumHC w Polsce!", UUID.randomUUID())});
        ping.setDescription(ChatUtil.fixColor(Config.motd.replace("{N}", "\n")));

    }

}
