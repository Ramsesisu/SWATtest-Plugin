package me.rqmses.swattest.listeners;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import me.rqmses.swattest.global.Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
  public static final HashMap<UUID, File> playersafe = new HashMap<>();
  
  public static final HashMap<UUID, FileConfiguration> playerconfig = new HashMap<>();
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    event.setJoinMessage(ChatColor.GOLD + event.getPlayer().getName() + ChatColor.YELLOW + " ist nun" + ChatColor.GREEN + " online" + ChatColor.YELLOW + ".");
    player.setGameMode(GameMode.SURVIVAL);
    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0D);
    player.setHealth(40.0D);
    player.setCustomName("");
    player.getActivePotionEffects().clear();
    player.getInventory().clear();
    PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.FALSE);
    Functions.equipPlayer(player);
    playersafe.put(player.getUniqueId(), new File("data" + File.separator + event.getPlayer().getUniqueId() + ".yml"));
    playerconfig.put(player.getUniqueId(), YamlConfiguration.loadConfiguration(playersafe.get(player.getUniqueId())));
    if (!playersafe.get(player.getUniqueId()).exists()) {
      Functions.createFile(player);
      player.setBedSpawnLocation(new Location(Bukkit.getWorld("world"), 103.0D, 70.0D, 157.0D), true);
      player.teleport(new Location(Bukkit.getWorld("world"), 103.0D, 70.0D, 157.0D));
    } 
    System.out.println(playersafe + " wurde erfolgreich geladen.");
  }
}
