package pl.dzokv.auth.listeners;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.event.*;
import pl.dzokv.auth.tasks.LoginTask;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.event.*;

public class PlayerDisconectListener implements Listener
{
    @EventHandler
    public void onDisc(final PlayerDisconnectEvent e) {
        final ProxiedPlayer p = e.getPlayer();
        if (LoginTask.players.contains(p)) {
            LoginTask.players.remove(p);
        }
    }
}
