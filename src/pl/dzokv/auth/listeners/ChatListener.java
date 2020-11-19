package pl.dzokv.auth.listeners;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.connection.*;
import pl.dzokv.auth.tasks.LoginTask;
import net.md_5.bungee.event.*;

public class ChatListener implements Listener
{
    @EventHandler
    public void onChat(final ChatEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if ((e.getMessage().contains("//solve") && e.getMessage().contains("//calc")) || e.getMessage().contains("//calculate") || e.getMessage().contains("//eval") || e.getMessage().contains("//evaluate")) {
            e.setCancelled(true);
            return;
        }
        final ProxiedPlayer p = (ProxiedPlayer)e.getSender();
        if (LoginTask.players.contains(p)) {
            final String msg = e.getMessage().toLowerCase();
            if (msg.startsWith("/l") || msg.startsWith("/login") || msg.startsWith("/reg") || msg.startsWith("/register")) {
                return;
            }
            e.setCancelled(true);
        }
    }
}
