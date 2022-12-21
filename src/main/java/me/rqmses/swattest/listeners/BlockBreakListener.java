package me.rqmses.swattest.listeners;

import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

import static me.rqmses.swattest.commands.BuildmodeCommand.buildmode;

public class BlockBreakListener implements Listener {

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!buildmode.contains(event.getPlayer())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onBlockExplode(BlockExplodeEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onTNTExplode(EntityExplodeEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBreakPainting(HangingBreakByEntityEvent event) {
    if (event.getEntity() instanceof Painting) {
      if (event.getRemover() instanceof Player) {
        if (!buildmode.contains(((Player) event.getRemover()).getPlayer())) {
          event.setCancelled(true);
        }
      }
    }
  }
}
