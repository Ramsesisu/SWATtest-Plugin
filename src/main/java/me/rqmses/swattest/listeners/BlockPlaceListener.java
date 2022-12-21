package me.rqmses.swattest.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import static me.rqmses.swattest.commands.BuildmodeCommand.buildmode;

public class BlockPlaceListener implements Listener {

  @EventHandler
  public void onBlockBuild(BlockPlaceEvent event) {
    if (!buildmode.contains(event.getPlayer())) {
      event.setCancelled(true);
    }
  }
}
