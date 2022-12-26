package me.rqmses.swattest.listeners;

import me.rqmses.swattest.commands.CarCommand;
import me.rqmses.swattest.global.Functions;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.UUID;

import static me.rqmses.swattest.commands.TrainingsbotCommand.NPCListPlayer;

public class PlayerQuitListener implements Listener {

  public static final ArrayList<UUID> deadplayers = new ArrayList<>();

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    event.setQuitMessage(ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " ist nun" + ChatColor.RED + " offline" + ChatColor.YELLOW + ".");
    Functions.getTeam(player).removeEntry(player.getName());
    if (player.getCustomName() != null) {
      if (player.getCustomName().equals("dead")) {
        deadplayers.add(player.getUniqueId());
      }
    }
    if (CarCommand.minecarts.get(player.getName()) != null) {
      CarCommand.minecarts.get(player.getName()).remove();
      CarCommand.minecarts.put(player.getName(), null);
    }
    if (NPCListPlayer.get(player.getName()) != null) {
      for (NPC npc : NPCListPlayer.get(player.getName())) {
        NPCListPlayer.get(player.getName()).remove(npc);
        npc.despawn(DespawnReason.RELOAD);
        npc.destroy();
      }
    }
  }
}