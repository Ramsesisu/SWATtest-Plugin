package me.rqmses.swattest.listeners;

import me.rqmses.swattest.commands.CarCommand;
import me.rqmses.swattest.global.Functions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

  public static final ArrayList<UUID> deadplayers = new ArrayList<>();

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    Functions.getTeam(player).removeEntry(player.getName());
    if (player.getCustomName().equals("dead")) {
      deadplayers.add(player.getUniqueId());
    }
    if (CarCommand.minecarts.get(player.getName()) != null) {
      CarCommand.minecarts.get(player.getName()).remove();
      CarCommand.minecarts.put(player.getName(), null);
    }
    event.setQuitMessage(ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " ist nun" + ChatColor.RED + " offline" + ChatColor.YELLOW + ".");
  }
}