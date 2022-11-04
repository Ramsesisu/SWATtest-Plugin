package me.rqmses.swattest.listeners;

import me.rqmses.swattest.commands.EquipCommand;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

import static me.rqmses.swattest.SWATtest.plugin;

public class PlayerDeathListener implements Listener {

    public static String killer = "Unbekannt";

    public void setKiller(String name) {
        killer = name;
    }

    String deathmessage;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity().getPlayer();

        player.setCustomName("dead");

        String[] words = event.getDeathMessage().split("\\W+");

        if(event.getDeathMessage().contains("fell")) {
            killer = "Fallschaden";
            deathmessage = ChatColor.translateAlternateColorCodes('&', "&7" + event.getEntity().getName() + " &f&lstarb an &7&lFallschaden&f&l.");
        } else if (event.getDeathMessage().contains("M4") || event.getDeathMessage().contains("Sniper") || event.getDeathMessage().contains("MP5") || event.getDeathMessage().contains("Jagdflinte")) {
            String weapon = null;
            if (event.getDeathMessage().contains("M4")) {
                weapon = "M4";
            }
            if (event.getDeathMessage().contains("Sniper")) {
                weapon = "Sniper";
            }
            if (event.getDeathMessage().contains("MP5")) {
                weapon = "MP5";
            }
            if (event.getDeathMessage().contains("Jagdflinte")) {
                weapon = "Jagdflinte";
            }
            deathmessage = ChatColor.translateAlternateColorCodes('&', "&7" + event.getEntity().getName() + " &f&lwurde von &7" + killer + " &f&lmit&7&l " + weapon + " &f&lerschossen.");
        } else {
            deathmessage = ChatColor.translateAlternateColorCodes('&', "&7" + event.getEntity().getName() + " &f&lwurde von &7" + killer + " &f&lerschlagen.");
        }

        player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&cDu wurdest getötet!"), ChatColor.translateAlternateColorCodes('&', "&cvon &7" + killer), 10, 30, 20);

        List<Entity> nearPlayers = new ArrayList<>();
        getEntitiesAroundPoint(event.getEntity().getLocation(), 30).forEach((Entity nearPlayer) -> {
            nearPlayers.add(nearPlayer);
        });
        List<Entity> nearPlayers2 = new ArrayList<>();
        nearPlayers.forEach((Entity playerName) -> {
            nearPlayers2.remove(playerName);
            nearPlayers2.add(playerName);
        });
        nearPlayers2.forEach((Entity playerName2) -> {
            playerName2.sendMessage(deathmessage);
        });

        PlayerInventory tempinv = EquipCommand.playerinv.get(player.getName());

        player.getInventory().setContents(tempinv.getContents());

        event.setDeathMessage("");

        Location loc = player.getLocation();
        event.getEntity().spigot().respawn();
        player.teleport(loc);

        player.setGameMode(GameMode.SPECTATOR);
        KillDelay(event);

    }

    public void KillDelay(PlayerDeathEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Player player = event.getEntity().getPlayer();

            player.teleport(new Location(Bukkit.getWorld(player.getWorld().getName()), player.getBedSpawnLocation().getBlockX(), player.getBedSpawnLocation().getBlockY() + 1, player.getBedSpawnLocation().getBlockZ()));
            player.setGameMode(GameMode.SURVIVAL);

            player.sendTitle(ChatColor.GREEN + "Du lebst nun wieder!", "", 10, 30, 20);
            player.setCustomName(player.getDisplayName());
        }, 300L);
    }

    public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
        List<Entity> entities = new ArrayList<Entity>();
        World world = location.getWorld();

        int smallX = (int) Math.floor((location.getX() - radius) / 16.0D);
        int bigX = (int) Math.floor((location.getX() + radius) / 16.0D);
        int smallZ = (int) Math.floor((location.getZ() - radius) / 16.0D);
        int bigZ = (int) Math.floor((location.getZ() + radius) / 16.0D);

        for (int x = smallX; x <= bigX; x++) {
            for (int z = smallZ; z <= bigZ; z++) {
                if (world.isChunkLoaded(x, z)) {
                    entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities()));
                }
            }
        }

        Iterator<Entity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            if (entityIterator.next().getLocation().distanceSquared(location) > radius * radius) {
                entityIterator.remove();
            }
        }
        return entities;
    }
}
