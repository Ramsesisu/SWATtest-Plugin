package me.rqmses.swattest.listeners;

import me.rqmses.swattest.global.Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {
  @EventHandler
  @Deprecated
  public void onChatMessage(PlayerChatEvent event) {
    event.setCancelled(true);
    String name = Functions.getTeam(event.getPlayer()).getColor() + event.getPlayer().getName();
    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&f" + name + "&7 > &f" + event.getMessage()));
  }
}
