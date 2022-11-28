package me.rqmses.swattest.listeners;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.commands.EquipCommand;
import me.rqmses.swattest.global.Functions;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PlayerDeathListener implements Listener {
  public static String killer = "Unbekannt";
  
  public static void setKiller(String name) {
    killer = name;
  }
  
  public static final HashMap<String, String> deathloadmsg = new HashMap<>();
  
  public static final HashMap<String, BukkitTask> deathtask = new HashMap<>();
  
  public static final HashMap<String, Boolean> spawnprotection = new HashMap<>();
  
  String deathmessage;
  
  String playerdeathmessage;
  
  @EventHandler
  public void onDeath(PlayerDeathEvent event) {
    if (event.getEntity().hasMetadata("NPC")) {return;}
    System.out.println(event.getDeathMessage());
    final Player player = event.getEntity().getPlayer();
    Location deathloc = player.getLocation();
    player.spigot().respawn();
    player.setCustomName("dead");
    if (event.getDeathMessage().contains("fell")) {
      killer = "Fallschaden";
      deathmessage = ChatColor.translateAlternateColorCodes('&', "&7" + event.getEntity().getName() + " &f&list an &7&lFallschaden&f&l gestorben.");
      playerdeathmessage = ChatColor.translateAlternateColorCodes('&', "&7&f&lDu bist an &7&lFallschaden&f&l gestorben.");
    } else if (event.getDeathMessage().contains("M4") || event.getDeathMessage().contains("Sniper") || event.getDeathMessage().contains("MP5") || event.getDeathMessage().contains("Jagdflinte") || event.getDeathMessage().contains("Messer")) {
      String weapon = null;
      String verb = "erschossen";
      if (event.getDeathMessage().contains("M4"))
        weapon = "M4"; 
      if (event.getDeathMessage().contains("Sniper"))
        weapon = "Sniper"; 
      if (event.getDeathMessage().contains("MP5"))
        weapon = "MP5"; 
      if (event.getDeathMessage().contains("Jagdflinte"))
        weapon = "Jagdflinte"; 
      if (event.getDeathMessage().contains("Messer")) {
        weapon = "Messer";
        verb = "erstochen";
      } 
      deathmessage = ChatColor.translateAlternateColorCodes('&', "&7" + event.getEntity().getName() + " &f&lwurde von &7" + killer + " &f&lmit&7&l " + weapon + " &f&l" + verb + ".");
      playerdeathmessage = ChatColor.translateAlternateColorCodes('&', "&7&f&lDu wurdest von &7" + killer + " &f&lmit&7&l " + weapon + " &f&l" + verb + ".");
    } else {
      String killcause = String.valueOf(event.getEntity().getLastDamageCause().getCause());
      switch (killcause) {
        case "CUSTOM":
        case "BLOCK_EXPLOSION":
        case "PROJECTILE":
        case "WITHER":
          killer = "RPG";
          break;
        case "FIRE_TICK":
          killer = "Flammenwerfer";
          break;
      } 
      deathmessage = ChatColor.translateAlternateColorCodes('&', "&7" + event.getEntity().getName() + " &f&lwurde von &7" + killer + " &f&lgetötet");
      playerdeathmessage = ChatColor.translateAlternateColorCodes('&', "&7&f&lDu wurdest von &7" + killer + " &f&lgetötet");
    } 
    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&cDu wurdest getötet"), ChatColor.translateAlternateColorCodes('&', "&cvon &7" + killer), 10, 30, 20);
    List<Entity> nearPlayers = new ArrayList<>(getEntitiesAroundPoint(deathloc, 30.0D));
    List<Entity> nearPlayers2 = new ArrayList<>();
    nearPlayers.forEach(playerName -> {
          nearPlayers2.remove(playerName);
          nearPlayers2.add(playerName);
        });
    nearPlayers2.remove(player);
    nearPlayers2.forEach(playerName2 -> playerName2.sendMessage(deathmessage));
    player.sendMessage(playerdeathmessage);
    event.setDeathMessage("");
    event.getEntity().spigot().respawn();
    player.teleport(deathloc);
    player.setGameMode(GameMode.SPECTATOR);
    spawnprotection.put(player.getName(), Boolean.TRUE);
    Bukkit.getScheduler().runTaskLater(SWATtest.plugin, () -> {
      if (player.getBedSpawnLocation() == null) {
        player.setBedSpawnLocation(new Location(Bukkit.getWorld("world"), 103, 70, 157), true);
      }
          player.teleport(new Location(Bukkit.getWorld(player.getWorld().getName()), player.getBedSpawnLocation().getBlockX(), (player.getBedSpawnLocation().getBlockY() + 1), player.getBedSpawnLocation().getBlockZ()));
          player.sendTitle(ChatColor.GREEN + "Du lebst nun wieder!", "", 10, 30, 20);
          player.setCustomName(player.getDisplayName());
          EquipCommand.playerequip.putIfAbsent(player.getName(), "none");
          Functions.equipPlayer(player);
          deathtask.get(player.getName()).cancel();
          PlayerInteractListener.cooldowntimes.put(player.getUniqueId(), 0);
          PlayerInteractListener.cooldowns.put(player.getUniqueId(), 0L);
          player.setGameMode(GameMode.SURVIVAL);
        }, 300L);
    Bukkit.getScheduler().runTaskLater(SWATtest.plugin, () -> {
          if (spawnprotection.get(player.getName())) {
            spawnprotection.put(player.getName(), Boolean.FALSE);
            player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
          } 
        }, 400L);
    deathloadmsg.put(player.getName(), ChatColor.translateAlternateColorCodes('&', "&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"));
    final int[] i = { 0 };
    BukkitRunnable death = new BukkitRunnable() {
        public void run() {
          i[0] = i[0] + 1;
          if (i[0] == 15) {
            i[0] = 0;
            cancel();
          } 
          PlayerDeathListener.deathloadmsg.put(player.getName(), PlayerDeathListener.deathloadmsg.get(player.getName()).replaceFirst("8", "a"));
          player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(PlayerDeathListener.deathloadmsg.get(player.getName())));
        }
      };
    deathtask.put(player.getName(), death.runTaskTimer(SWATtest.plugin, 0L, 20L));
  }
  
  public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
    List<Entity> entities = new ArrayList<>();
    World world = location.getWorld();
    int smallX = (int)Math.floor((location.getX() - radius) / 16.0D);
    int bigX = (int)Math.floor((location.getX() + radius) / 16.0D);
    int smallZ = (int)Math.floor((location.getZ() - radius) / 16.0D);
    int bigZ = (int)Math.floor((location.getZ() + radius) / 16.0D);
    for (int x = smallX; x <= bigX; x++) {
      for (int z = smallZ; z <= bigZ; z++) {
        if (world.isChunkLoaded(x, z))
          entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities())); 
      } 
    } 
    entities.removeIf(entity -> (entity.getLocation().distanceSquared(location) > radius * radius));
    return entities;
  }
}
