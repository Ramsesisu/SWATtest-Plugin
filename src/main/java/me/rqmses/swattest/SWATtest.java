package me.rqmses.swattest;

import me.rqmses.swattest.commands.*;
import me.rqmses.swattest.global.npctraits.AbaimTrait;
import me.rqmses.swattest.global.npctraits.AttackTrait;
import me.rqmses.swattest.listeners.*;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.io.File;

import static me.rqmses.swattest.commands.CarCommand.minecarts;
import static me.rqmses.swattest.commands.TeamCommand.*;

public final class SWATtest extends JavaPlugin implements Listener {
    public static SWATtest plugin;

    public static NPCRegistry registry;

    public static Team team0;
    public static Team team1;
    public static Team team2;
    public static Team team3;
    public static Team team4;

    public void onEnable() {
        plugin = this;
        listenerRegistration();
        commandRegistration();

        team0 = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam("Team 0");
        team1 = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam("Team 1");
        team2 = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam("Team 2");
        team3 = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam("Team 3");
        team4 = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam("Team 4");
        team0.setAllowFriendlyFire(true);
        team1.setAllowFriendlyFire(false);
        team2.setAllowFriendlyFire(false);
        team3.setAllowFriendlyFire(false);
        team4.setAllowFriendlyFire(false);
        team0.setColor(ChatColor.WHITE);
        team1.setColor(ChatColor.RED);
        team1.setPrefix(ChatColor.RED.toString());
        team2.setColor(ChatColor.BLUE);
        team3.setColor(ChatColor.GREEN);
        team4.setColor(ChatColor.GOLD);

        BukkitRunnable checkTeamsizes = new BukkitRunnable() {
            @Override
            public void run() {
                if (team1.getSize() == 0) {
                    kills1 = 0;
                    teamname1 = "Team-1";
                }
                if (team2.getSize() == 0) {
                    kills2 = 0;
                    teamname2 = "Team-2";
                }
                if (team3.getSize() == 0) {
                    kills3 = 0;
                    teamname3 = "Team-3";
                }
                if (team4.getSize() == 0) {
                    kills4 = 0;
                    teamname4 = "Team-4";
                }
            }
        };

        checkTeamsizes.runTaskTimer(plugin, 20L, 0L);

        registry = CitizensAPI.getNPCRegistry();
        net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(AttackTrait.class));
        net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(AbaimTrait.class));

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&lDas SWATtest-Plugin wurde reloaded! &7&l- &c&lVersion 1.8"));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7➝ Bei Bugs muss der Spieler rejoinen."));

            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.FALSE);
                PlayerJoinListener.playersafe.put(player.getUniqueId(), new File("data" + File.separator + player.getUniqueId() + ".yml"));
                PlayerJoinListener.playerconfig.put(player.getUniqueId(), YamlConfiguration.loadConfiguration(PlayerJoinListener.playersafe.get(player.getUniqueId())));
                team0.addEntry(player.getName());
            }
        }, 20L);

        System.out.println("Plugin erfolgreich geladen.");
    }

    public void onDisable() {
        if (BombeCommand.bombloc != null &&
                Bukkit.getWorld("world").getBlockAt(BombeCommand.bombloc).getType() == Material.TNT)
            Bukkit.getWorld("world").getBlockAt(BombeCommand.bombloc).setType(Material.AIR);

        for (NPC npc : registry.sorted()) {
            npc.despawn(DespawnReason.RELOAD);
            npc.destroy();
        }

        for (Minecart minecart : minecarts.values()) {
            minecart.remove();
        }
        System.out.println("Plugin erfolgreich heruntergefahren.");
    }

    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new ProjectileHitListener(), this);
        pluginManager.registerEvents(new PlayerDamageListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new ItemDropListener(), this);
        pluginManager.registerEvents(new PlayerHitListener(), this);
        pluginManager.registerEvents(new PlayerChatListener(), this);
        pluginManager.registerEvents(new InventoryOpenListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new PlayerSwitchItemListener(), this);
        pluginManager.registerEvents(new TabCompleteListener(), this);
        pluginManager.registerEvents(new MinecartListener(), this);
    }

    private void commandRegistration() {
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("equip").setExecutor(new EquipCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("use").setExecutor(new UseCommand());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("teamname").setExecutor(new TeamnameCommand());
        getCommand("camera").setExecutor(new CameraCommand());
        getCommand("bombe").setExecutor(new BombeCommand());
        getCommand("navi").setExecutor(new NaviCommand());
        getCommand("cooldown").setExecutor(new CoolDownCommand());
        getCommand("resetdata").setExecutor(new ResetDataCommand());
        getCommand("trainingsbot").setExecutor(new TrainingsbotCommand());
        getCommand("abaimbot").setExecutor(new AbaimbotCommand());
        getCommand("luftlinie").setExecutor(new LuftlinieCommand());
        getCommand("leitfaden").setExecutor(new LeitfadenCommand());
        getCommand("car").setExecutor(new CarCommand());
    }
}