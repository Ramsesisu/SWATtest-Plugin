package me.rqmses.swattest.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.world.WorldSaveEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    @Deprecated
    public void onChatMessage(PlayerChatEvent event) {
        event.setCancelled(true);

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&f" + event.getPlayer().getName() + "&7 > &f" + event.getMessage()));
    }
}
