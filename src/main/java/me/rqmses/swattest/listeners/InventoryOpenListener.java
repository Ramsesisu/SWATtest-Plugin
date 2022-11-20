package me.rqmses.swattest.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryOpenListener implements Listener {
  @EventHandler
  public void onOpenInventory(InventoryOpenEvent event) {
    if (event.getInventory().getType() != InventoryType.PLAYER && event.getInventory().getType() != InventoryType.WORKBENCH)
      event.setCancelled(true); 
  }
}
