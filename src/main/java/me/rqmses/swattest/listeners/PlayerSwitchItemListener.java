package me.rqmses.swattest.listeners;

import java.io.IOException;
import me.rqmses.swattest.commands.EquipCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerSwitchItemListener implements Listener {
  ItemStack item;
  
  @EventHandler
  public void onItemSwitch(InventoryClickEvent event) {
    Player player = ((Player)event.getWhoClicked()).getPlayer();
    if (event.getSlot() <= 8 && event.getSlot() >= 0) {
      if (event.getCursor() == null) {
        this.item = new ItemStack(Material.AIR);
      } else {
        this.item = event.getCursor();
      } 
      PlayerJoinListener.playerconfig.get(player.getUniqueId()).set(EquipCommand.playerequip.get(player.getName()) + "." + event.getSlot(), this.item.getType().toString());
      try {
        PlayerJoinListener.playerconfig.get(player.getUniqueId()).save(PlayerJoinListener.playersafe.get(player.getUniqueId()));
      } catch (IOException e) {
        throw new RuntimeException(e);
      } 
    } else {
      if (!(event.getSlot() == 38)) event.setCancelled(true);
    }
  }
}
