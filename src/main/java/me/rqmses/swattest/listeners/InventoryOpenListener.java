package me.rqmses.swattest.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.Objects;

import static me.rqmses.swattest.commands.BuildmodeCommand.buildmode;

public class InventoryOpenListener implements Listener {
  @EventHandler
  public void onOpenInventory(InventoryOpenEvent event) {
    if (event.getPlayer() instanceof Player) {
      Player player = (Player) event.getPlayer();
      if (!buildmode.contains(player)) {
        boolean invsee = false;
        for (Player tempplayer : Bukkit.getOnlinePlayers()) {
          if (Objects.equals(tempplayer.getName(), event.getInventory().getName())) { invsee = true; }
        }
        if (!invsee) {
          event.setCancelled(true);
        }
      }
    }
  }
}
