package me.rqmses.swattest.listeners;

import me.rqmses.swattest.commands.EquipCommand;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static me.rqmses.swattest.SWATtest.plugin;

public class PlayerDeathListener implements Listener {

    public static String killer = "Unbekannt";
    public void setKiller(String name) {
        killer = name;
    }

    public static HashMap<String, String> deathloadmsg = new HashMap<String, String>();
    public static HashMap<String, BukkitTask> deathtask = new HashMap<String, BukkitTask>();

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
            killer = String.valueOf(event.getEntity().getLastDamageCause().getCause());
            deathmessage = ChatColor.translateAlternateColorCodes('&', "&7" + event.getEntity().getName() + " &f&lwurde von &7" + killer + " &f&lgetötet.");
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

        player.getInventory().setContents(EquipCommand.playerinv.get(player.getName()));

        event.setDeathMessage("");

        Location loc = player.getLocation();
        event.getEntity().spigot().respawn();
        player.teleport(loc);

        player.setGameMode(GameMode.SPECTATOR);
        KillDelay(event);

        deathloadmsg.put(player.getName(), ChatColor.translateAlternateColorCodes('&', "&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"));

        final int[] i = {0};

        BukkitRunnable death = new BukkitRunnable() {
            @Override
            public void run() {
                i[0]++;
                if (i[0] == 15) {
                    i[0] = 0;
                    cancel();
                }
                deathloadmsg.put(player.getName(), deathloadmsg.get(player.getName()).replaceFirst("8", "a"));
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(deathloadmsg.get(player.getName())));
            }
        };

        deathtask.put(player.getName(), death.runTaskTimer(plugin, 0, 20L));
    }

    public void KillDelay(PlayerDeathEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Player player = event.getEntity().getPlayer();

            player.teleport(new Location(Bukkit.getWorld(player.getWorld().getName()), player.getBedSpawnLocation().getBlockX(), player.getBedSpawnLocation().getBlockY() + 1, player.getBedSpawnLocation().getBlockZ()));
            player.setGameMode(GameMode.SURVIVAL);

            player.sendTitle(ChatColor.GREEN + "Du lebst nun wieder!", "", 10, 30, 20);
            player.setCustomName(player.getDisplayName());

            deathtask.get(player.getName()).cancel();
            PlayerInteractListener.cooldowntimes.put(player.getName(), 0);
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
