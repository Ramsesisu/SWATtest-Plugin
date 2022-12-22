package me.rqmses.swattest.listeners;

import me.rqmses.swattest.commands.EquipCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Objects;

import static me.rqmses.swattest.commands.BuildmodeCommand.buildmode;

public class PlayerSwitchItemListener implements Listener {
  ItemStack item;
  
  @EventHandler
  public void onItemSwitch(InventoryClickEvent event) {
    Player player = ((Player) event.getWhoClicked()).getPlayer();
    boolean invsee = false;
    for (Player tempplayer : Bukkit.getOnlinePlayers()) {
      if (Objects.equals(tempplayer.getName(), event.getClickedInventory().getName())) { invsee = true; }
    }
    if (invsee) {
      Player victim = Bukkit.getServer().getPlayer(event.getInventory().getName());
      int slot = event.getSlot();
      switch (event.getAction()) {
        case PICKUP_ALL:
        case PICKUP_HALF:
        case PICKUP_ONE:
        case MOVE_TO_OTHER_INVENTORY:
        case COLLECT_TO_CURSOR:
        case SWAP_WITH_CURSOR:
          victim.getInventory().setItem(slot, null);
          break;
        case PLACE_ALL:
        case PLACE_SOME:
        case PLACE_ONE:
          victim.getInventory().setItem(slot, event.getCursor());
          break;
      }
    } else if (event.getSlot() <= 8 && event.getSlot() >= 0) {
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
    } else if (event.getSlot() == 40) {
        if (!buildmode.contains(player)) {
          event.setCancelled(true);
        }
    } else if (event.getSlot() == 38) {
      if (!buildmode.contains(player)) {
        if (event.getCursor().getType() != Material.ELYTRA && event.getCursor().getType() != Material.LEATHER_CHESTPLATE && event.getCurrentItem().getType() != Material.ELYTRA && event.getCurrentItem().getType() != Material.LEATHER_CHESTPLATE) {
          event.setCancelled(true);
        }
      }
    }
  }

  @EventHandler
  public void onSwapItemInHand(PlayerSwapHandItemsEvent event) {
    if (!buildmode.contains(event.getPlayer())) {
      event.setCancelled(true);
    }
  }
}
