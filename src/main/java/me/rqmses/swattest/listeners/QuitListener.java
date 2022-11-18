package me.rqmses.swattest.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    event.setQuitMessage(ChatColor.GOLD + event.getPlayer().getName() + ChatColor.YELLOW + " ist nun" + ChatColor.RED + " offline" + ChatColor.YELLOW + ".");
  }
}