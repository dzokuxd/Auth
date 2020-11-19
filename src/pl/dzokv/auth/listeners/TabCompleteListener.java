package pl.dzokv.auth.listeners;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.connection.*;
import pl.dzokv.auth.lang.Config;
import net.md_5.bungee.event.*;

public class TabCompleteListener implements Listener
{
    @EventHandler
    public void tab(final TabCompleteEvent e) {
        if (e.getSender() instanceof ProxiedPlayer) {
            if (e.getSuggestions().size() > 1 || Config.admins.contains(((ProxiedPlayer)e.getSender()).getName()) || Config.slot1.contains(((ProxiedPlayer)e.getSender()).getName())) {
                return;
            }
            e.setCancelled(true);
        }
        else {
            e.setCancelled(true);
        }
    }
}
