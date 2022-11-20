package me.rqmses.swattest.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
  @EventHandler
  public void onBlockBuild(BlockPlaceEvent event) {
    if (!event.getPlayer().getInventory().getItemInOffHand().getType().equals(Material.WOOD_SPADE))
      event.setCancelled(true); 
  }
}
