package me.rqmses.swattest.listeners;

import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.Functions;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import static me.rqmses.swattest.SWATtest.*;

public class PlayerJoinListener implements Listener {
  public static final HashMap<UUID, File> playersave = new HashMap<>();
  
  public static final HashMap<UUID, FileConfiguration> playerconfig = new HashMap<>();
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    if (banned.contains(event.getPlayer().getName())) {
      event.getPlayer().kickPlayer("Du bist noch gebannt!");
      return;
    }
    Player player = event.getPlayer();
    player.setOp(false);
    event.setJoinMessage(ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " ist nun" + ChatColor.GREEN + " online" + ChatColor.YELLOW + ".");
    player.setGameMode(GameMode.SURVIVAL);
    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0D);
    player.setHealth(40.0D);
    player.setCustomName(" ");
    player.getActivePotionEffects().clear();
    player.getInventory().clear();
    PlayerDeathListener.spawnprotection.put(player.getUniqueId(), Boolean.FALSE);
    playersave.put(player.getUniqueId(), new File("data" + File.separator + player.getUniqueId() + ".yml"));
    playerconfig.put(player.getUniqueId(), YamlConfiguration.loadConfiguration(playersave.get(player.getUniqueId())));
    if (!playersave.get(player.getUniqueId()).exists()) {
      Functions.createFile(player);
      player.setBedSpawnLocation(new Location(Bukkit.getWorld("world"), 103, 70, 157), true);
      player.teleport(new Location(Bukkit.getWorld("world"), 103, 70, 157));
      player.chat("/news");
    }
    team0.addEntry(player.getName());
    Functions.equipPlayer(player);

    PermissionAttachment pperms = player.addAttachment(plugin);
    pperms.setPermission("booknews.news", true);

    if (PlayerQuitListener.deadplayers.contains(player.getUniqueId())) {
      PlayerQuitListener.deadplayers.remove(player.getUniqueId());
      PlayerDeathListener.deathloadmsg.remove(player.getName());
      PlayerDeathListener.deathtask.get(player.getName()).cancel();
      player.setHealth(0.0D);
    }

    if (Admins.isAdmin(player)) {
      player.setOp(true);
    }

    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.AMBIENT, 10, 1);
  }
}
