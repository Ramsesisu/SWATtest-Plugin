package me.rqmses.swattest.listeners;

import me.rqmses.swattest.commands.EquipCommand;
import me.rqmses.swattest.variables.Items;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class JoinListener implements Listener {

    public static final HashMap<UUID, File> playersafe = new HashMap<>();
    public static final HashMap<UUID, FileConfiguration> playerconfig = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GOLD + event.getPlayer().getName() + ChatColor.YELLOW + " ist nun" +  ChatColor.GREEN + " online" + ChatColor.YELLOW + ".");
        player.setGameMode(GameMode.SURVIVAL);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
        player.setHealth(40);
        player.setCustomName("");
        player.getActivePotionEffects().clear();
        player.getInventory().clear();

        PlayerDeathListener.spawnprotection.put(player.getName(), false);

        EquipCommand.cooldowns.put(player.getName(), 0L);
        if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "elytra")) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&3Elytra&8&l] &r") + player.getName());
            player.chat("/equip Elytra");
        }
        if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "swat")) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&1SWAT&8&l] &r") + player.getName());
            player.chat("/equip SWAT");
        }
        if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "polizei")) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&9UCPD&8&l] &r") + player.getName());
            player.chat("/equip Polizei");
        }
        if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "ballas")) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&5Ballas&8&l] &r") + player.getName());
            player.chat("/equip Ballas");
        }
        if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "terror")) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&eTerror&8&l] &r") + player.getName());
            player.chat("/equip Terror");
        }
        if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "zivilist")) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&7Zivi&8&l] &r") + player.getName());
            player.chat("/equip Zivilist");
        }
        if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "flammenwerfer")) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&cFlammi&8&l] &r") + player.getName());
            player.chat("/equip Flammenwerfer");
        }
        EquipCommand.cooldowns.put(player.getName(), 0L);

        playersafe.put(player.getUniqueId(), new File("data" + File.separator + event.getPlayer().getUniqueId() + ".yml"));
        playerconfig.put(player.getUniqueId(), YamlConfiguration.loadConfiguration(playersafe.get(player.getUniqueId())));
        if (!playersafe.get(player.getUniqueId()).exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                playersafe.get(player.getUniqueId()).createNewFile();

                playerconfig.get(player.getUniqueId()).set("elytra.0", Items.getElytra().getType().toString());
                playerconfig.get(player.getUniqueId()).set("elytra.1", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("elytra.2", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("elytra.3", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("elytra.4", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("elytra.5", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("elytra.6", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("elytra.7", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("elytra.8", Items.getAir().getType().toString());

                playerconfig.get(player.getUniqueId()).set("swat.0", Items.getSchild().getType().toString());
                playerconfig.get(player.getUniqueId()).set("swat.1", Items.getKev((short) 30).getType().toString());
                playerconfig.get(player.getUniqueId()).set("swat.2", Items.getM4().getType().toString());
                playerconfig.get(player.getUniqueId()).set("swat.3", Items.getSniper().getType().toString());
                playerconfig.get(player.getUniqueId()).set("swat.4", Items.getFlashes().getType().toString());
                playerconfig.get(player.getUniqueId()).set("swat.5", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("swat.6", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("swat.7", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("swat.8", Items.getAir().getType().toString());

                playerconfig.get(player.getUniqueId()).set("polizei.0", Items.getKev((short) 50).getType().toString());
                playerconfig.get(player.getUniqueId()).set("polizei.1", Items.getM4().getType().toString());
                playerconfig.get(player.getUniqueId()).set("polizei.2", Items.getMp5().getType().toString());
                playerconfig.get(player.getUniqueId()).set("polizei.3", Items.getTazer().getType().toString());
                playerconfig.get(player.getUniqueId()).set("polizei.4", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("polizei.5", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("polizei.6", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("polizei.7", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("polizei.8", Items.getAir().getType().toString());

                playerconfig.get(player.getUniqueId()).set("ballas.0", Items.getKev((short) 50).getType().toString());
                playerconfig.get(player.getUniqueId()).set("ballas.1", Items.getM4().getType().toString());
                playerconfig.get(player.getUniqueId()).set("ballas.2", Items.getJagdflinte().getType().toString());
                playerconfig.get(player.getUniqueId()).set("ballas.3", Items.getMesser().getType().toString());
                playerconfig.get(player.getUniqueId()).set("ballas.4", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("ballas.5", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("ballas.6", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("ballas.7", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("ballas.8", Items.getAir().getType().toString());

                playerconfig.get(player.getUniqueId()).set("terror.0", Items.getKev((short) 50).getType().toString());
                playerconfig.get(player.getUniqueId()).set("terror.1", Items.getM4().getType().toString());
                playerconfig.get(player.getUniqueId()).set("terror.2", Items.getRPG().getType().toString());
                playerconfig.get(player.getUniqueId()).set("terror.3", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("terror.4", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("terror.5", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("terror.6", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("terror.7", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("terror.8", Items.getAir().getType().toString());

                playerconfig.get(player.getUniqueId()).set("zivilist.0", Items.getMp5().getType().toString());
                playerconfig.get(player.getUniqueId()).set("zivilist.1", Items.getMesser().getType().toString());
                playerconfig.get(player.getUniqueId()).set("zivilist.2", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("zivilist.3", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("zivilist.4", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("zivilist.5", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("zivilist.6", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("zivilist.7", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("zivilist.8", Items.getAir().getType().toString());

                playerconfig.get(player.getUniqueId()).set("flammenwerfer.0", Items.getKev((short) 50).getType().toString());
                playerconfig.get(player.getUniqueId()).set("flammenwerfer.1", Items.getFlammenwerfer().getType().toString());
                playerconfig.get(player.getUniqueId()).set("flammenwerfer.2", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("flammenwerfer.3", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("flammenwerfer.4", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("flammenwerfer.5", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("flammenwerfer.6", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("flammenwerfer.7", Items.getAir().getType().toString());
                playerconfig.get(player.getUniqueId()).set("flammenwerfer.8", Items.getAir().getType().toString());

                playerconfig.get(player.getUniqueId()).save(playersafe.get(player.getUniqueId()));

                player.setBedSpawnLocation(new Location(Bukkit.getWorld("world"), 103,70,157), true);
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
        System.out.println(playersafe + " wurde erfolgreich geladen.");
    }
}
