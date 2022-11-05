package me.rqmses.swattest.commands;

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

public class UseCommand implements CommandExecutor, TabCompleter {

    public static HashMap<String, Long> cooldowns = new HashMap<String, Long>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.getGameMode() == GameMode.SURVIVAL) {
                int cooldownTime = 10;
                if (cooldowns.containsKey(player.getName())) {
                    long secondsLeft = ((cooldowns.get(player.getName()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
                    if (secondsLeft > 0) {
                        return true;
                    }
                }
                cooldowns.put(player.getName(), System.currentTimeMillis());

                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Du hast keine Droge angeben!");
                } else {
                    switch (args[0].toLowerCase()) {
                        case "schmerzmittel":
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100, 1);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*250, 3));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*20, 1));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*5, 0));
                            break;
                        case "kokain":
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100, 1);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*150, 3));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*16, 0));
                            if (!player.getActivePotionEffects().toString().contains("SLOW")) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 0));
                            }
                            break;
                        case "koks":
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100, 1);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*220, 3));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*16, 0));
                            if (!player.getActivePotionEffects().toString().contains("SLOW")) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 0));
                            }
                            break;
                        case "marihuana":
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100, 1);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*250, 4));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*20, 1));
                            break;
                        case "gras":
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100, 1);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*250, 4));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*20, 1));
                            break;
                        case "methamphetamin":
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100, 1);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*220, 3));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*16, 1));
                            if (!player.getActivePotionEffects().toString().contains("SLOW")) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 0));
                            }
                            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20*16, 0));
                            break;
                        case "meth":
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 100, 1);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*220, 3));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*16, 1));
                            if (!player.getActivePotionEffects().toString().contains("SLOW")) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 0));
                            }
                            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20*16, 0));
                            break;
                        default:
                            player.sendMessage(ChatColor.RED + "Du kannst kein " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " nehmen!");
                            return true;
                    }

                    player.sendMessage(ChatColor.RED + "Du hast " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " genommen.");

                    List<Entity> nearPlayers = new ArrayList<>();
                    getEntitiesAroundPoint(player.getLocation(), 30).forEach((Entity nearPlayer) -> {
                        nearPlayers.add(nearPlayer);
                    });
                    List<Entity> nearPlayers2 = new ArrayList<>();
                    nearPlayers.forEach((Entity playerName) -> {
                        nearPlayers2.remove(playerName);
                        nearPlayers2.add(playerName);
                    });
                    nearPlayers2.remove(player);
                    nearPlayers2.forEach((Entity playerName2) -> {
                        playerName2.sendMessage(ChatColor.DARK_RED + player.getName() +  ChatColor.RED + " hat " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " genommen.");
                    });
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String[] targets = new String[] {"Schmerzmittel", "Kokain", "Marihuana", "Methamphetamin"};
        if (args.length == 1) {
            for (String target : targets) {
                if (target.toUpperCase().startsWith(args[0].toUpperCase())) {
                    list.add(target);
                }
            }
        }
        return list;
    }

    public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
        List<Entity> entities = new ArrayList<Entity>();
        World world = location.getWorld();

        // To find chunks we use chunk coordinates (not block coordinates!)
        int smallX = (int) Math.floor((location.getX() - radius) / 16.0D);
        int bigX = (int) Math.floor((location.getX() + radius) / 16.0D);
        int smallZ = (int) Math.floor((location.getZ() - radius) / 16.0D);
        int bigZ = (int) Math.floor((location.getZ() + radius) / 16.0D);

        for (int x = smallX; x <= bigX; x++) {
            for (int z = smallZ; z <= bigZ; z++) {
                if (world.isChunkLoaded(x, z)) {
                    entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities())); // Add all entities from this chunk to the list
                }
            }
        }

        // Remove the entities that are within the box above but not actually in the sphere we defined with the radius and location
        // This code below could probably be replaced in Java 8 with a stream -> filter
        Iterator<Entity> entityIterator = entities.iterator(); // Create an iterator so we can loop through the list while removing entries
        while (entityIterator.hasNext()) {
            if (entityIterator.next().getLocation().distanceSquared(location) > radius * radius) { // If the entity is outside of the sphere...
                entityIterator.remove(); // Remove it
            }
        }
        return entities;
    }
}
