package me.rqmses.swattest.listeners;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.Functions;
import me.rqmses.swattest.global.TextUtils;
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
import static me.rqmses.swattest.listeners.PlayerDeathListener.spawnprotection;

public class PlayerJoinListener implements Listener {
  public static final HashMap<UUID, File> playersave = new HashMap<>();
  
  public static final HashMap<UUID, FileConfiguration> playerconfig = new HashMap<>();
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    event.setJoinMessage(ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " ist nun" + ChatColor.GREEN + " online" + ChatColor.YELLOW + ".");
    if (banned.containsKey(player.getName())) {
      event.setJoinMessage("");
      player.kickPlayer(banned.get(player.getName()));
      return;
    }
    player.setOp(false);
    player.setGameMode(GameMode.SURVIVAL);
    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0D);
    player.setHealth(40.0D);
    player.setCustomName(" ");
    player.getActivePotionEffects().clear();
    player.getInventory().clear();
    spawnprotection.put(player.getUniqueId(), Boolean.FALSE);
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

    if (!verified.contains(player.getName())) {
      player.sendMessage(ChatColor.DARK_AQUA + "Du bist noch nicht verifiziert, melde dich bei einem Admin!");
      player.spigot().sendMessage(TextUtils.getCustomClickable(ChatColor.DARK_RED, net.md_5.bungee.api.ChatColor.GRAY + "» " + net.md_5.bungee.api.ChatColor.DARK_RED + "Admins", "/admins"));
    }

    if (!spawnprotection.get(player.getUniqueId())) {
      spawnprotection.put(player.getUniqueId(), Boolean.TRUE);
    }
    Bukkit.getScheduler().runTaskLater(SWATtest.plugin, () -> {
      if (spawnprotection.get(player.getUniqueId())) {
        spawnprotection.put(player.getUniqueId(), Boolean.FALSE);
        player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
      }
    }, 200L);

    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.AMBIENT, 10, 1);
  }
}
