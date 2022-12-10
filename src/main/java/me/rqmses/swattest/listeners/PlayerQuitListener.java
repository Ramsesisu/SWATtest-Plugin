package me.rqmses.swattest.listeners;

import me.rqmses.swattest.global.Functions;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Functions.getTeam(event.getPlayer()).removeEntry(event.getPlayer().getName());
    event.setQuitMessage(ChatColor.GOLD + event.getPlayer().getName() + ChatColor.YELLOW + " ist nun" + ChatColor.RED + " offline" + ChatColor.YELLOW + ".");
  }
}