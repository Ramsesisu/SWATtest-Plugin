package me.rqmses.swattest.listeners;

import me.rqmses.swattest.commands.EquipCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class PlayerSwitchItemListener implements Listener {
  ItemStack item;
  
  @EventHandler
  public void onItemSwitch(InventoryClickEvent event) {
    Player player = ((Player)event.getWhoClicked()).getPlayer();
    if (event.getSlot() <= 8 && event.getSlot() >= 0) {
      if (event.getCursor() == null) {
        item = new ItemStack(Material.AIR);
      } else {
        item = event.getCursor();
      } 
      PlayerJoinListener.playerconfig.get(player.getUniqueId()).set(EquipCommand.playerequip.get(player.getName()) + "." + event.getSlot(), item.getType().toString());
      try {
        PlayerJoinListener.playerconfig.get(player.getUniqueId()).save(PlayerJoinListener.playersave.get(player.getUniqueId()));
      } catch (IOException e) {
        throw new RuntimeException(e);
      } 
    } else {
      if (!(event.getSlot() == 38) && !(event.getSlot() == 40)) {
        if (event.getCursor().getType() == Material.WOOD_SPADE || event.getCursor().getType() == Material.ELYTRA) {
          event.setCancelled(true);
        }
      }
    }
  }
}
