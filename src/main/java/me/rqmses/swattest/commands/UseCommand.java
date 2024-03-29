package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class UseCommand implements CommandExecutor, TabCompleter {
  public static final HashMap<UUID, Long> cooldowns = new HashMap<>();
  
  final List<Entity> nearPlayers = new ArrayList<>();
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
        Player player = (Player) sender;
        if (player.getGameMode() == GameMode.SURVIVAL) {
          if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().getDisplayName().contains("Sprenggürtel")) {
            player.sendMessage(ChatColor.GRAY + "In dieser Situation darfst du keine Drogen nehmen!");
          }
          int cooldownTime = 3;
          if (cooldowns.containsKey(player.getUniqueId())) {
            long secondsLeft = cooldowns.get(player.getUniqueId()) / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
            if (secondsLeft > 0L)
              return true;
          }
          cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
          if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Du hast keine Droge angeben!");
          } else {
            switch (args[0].toLowerCase()) {
              case "schmerzmittel":
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 1.0F);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 5000, 3));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 0));
                break;
              case "kokain":
              case "koks":
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 1.0F);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 3000, 4));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 320, 0));
                if (!player.getActivePotionEffects().toString().contains("SLOW"))
                  player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
                break;
              case "marihuana":
              case "gras":
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 1.0F);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 5000, 3));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400, 1));
                break;
              case "methamphetamin":
              case "meth":
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 1.0F);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 4400, 4));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 320, 1));
                if (!player.getActivePotionEffects().toString().contains("SLOW"))
                  player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 320, 0));
                break;
              default:
                player.sendMessage(ChatColor.RED + "Du kannst kein " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " nehmen!");
                return true;
            }
            player.sendMessage(ChatColor.RED + "Du hast " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " genommen.");
            this.nearPlayers.clear();
            List<Entity> nearPlayers = new ArrayList<>(getEntitiesAroundPoint(player.getLocation(), 10.0D));
            List<Entity> nearPlayers2 = new ArrayList<>(nearPlayers);
            nearPlayers2.clear();
            nearPlayers.forEach(playerName -> {
              nearPlayers2.remove(playerName);
              nearPlayers2.add(playerName);
            });
            nearPlayers2.remove(player);
            for (Entity playerName2 : nearPlayers2) {
              if (playerName2 instanceof Player) {
                Player tempplayer = (Player) playerName2;
                if (tempplayer.getInventory().getChestplate() != null && tempplayer.getInventory().getChestplate().getItemMeta().getDisplayName().contains("Sprenggürtel")) {
                  player.sendMessage(ChatColor.GRAY + "In dieser Situation darfst du keine Drogen nehmen!");
                }
                tempplayer.sendMessage(ChatColor.DARK_RED + player.getName() + ChatColor.RED + " hat " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " genommen.");
              }
            }
          }
        }
      }
    }
    if (!commandtoggles.get(command.getName())) {
      sender.sendMessage("Dieser Befehl ist deaktiviert!");
    }
    return true;
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    ArrayList<String> list = new ArrayList<>();
    String[] targets = { "Schmerzmittel", "Kokain", "Marihuana", "Methamphetamin" };
    if (args.length == 1)
      for (String target : targets) {
        if (target.toUpperCase().startsWith(args[0].toUpperCase()))
          list.add(target); 
      }  
    return list;
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
    entities.removeIf(entity -> (entity.getWorld() != Bukkit.getWorld("Training") || entity.getLocation().distanceSquared(location) > radius * radius));
    return entities;
  }
}
