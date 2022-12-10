package me.rqmses.swattest.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockBreakListener implements Listener {
  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!event.getPlayer().getInventory().getItemInOffHand().getType().equals(Material.WOOD_SPADE))
      event.setCancelled(true); 
  }
  
  @EventHandler
  public void onBlockExplode(BlockExplodeEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onTNTExplode(EntityExplodeEvent event) {
    event.setCancelled(true);
  }
}
