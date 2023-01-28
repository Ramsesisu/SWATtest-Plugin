package me.rqmses.swattest;

import me.rqmses.swattest.commands.*;
import me.rqmses.swattest.global.Admins;
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
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    public static final List<Object> admins = new ArrayList<>();
    public static final List<Object> builders = new ArrayList<>();
    public static final List<Object> verified = new ArrayList<>();
    public static final List<Object> bannedlist = new ArrayList<>();

    public static final HashMap<Object, String> banned = new HashMap<>();

    public static File adminsave;
    public static YamlConfiguration adminconfig;
    public static File buildersave;
    public static YamlConfiguration builderconfig;
    public static File bannedsave;
    public static YamlConfiguration bannedconfig;
    public static File verifiedsave;
    public static YamlConfiguration verifiedconfig;

    public static List<Command> commandlist;

    public static final HashMap<String, Boolean> commandtoggles = new HashMap<>();
    public static final HashMap<Material, Boolean> itemtoggles = new HashMap<>();

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
        team2.setColor(ChatColor.BLUE);
        team3.setColor(ChatColor.GREEN);
        team4.setColor(ChatColor.GOLD);

        BukkitRunnable reloadData = new BukkitRunnable() {
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

                for (Entity entity : Bukkit.getServer().getWorld("world").getEntities()) {
                    if (entity instanceof ArmorStand) {
                        if (Objects.equals(String.valueOf(entity.getPassengers()), "[]")) {
                            entity.remove();
                        }
                    }
                }
            }
        };

        reloadData.runTaskTimer(plugin, 20L, 20L);

        registry = CitizensAPI.getNPCRegistry();
        net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(AttackTrait.class));
        net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(AbaimTrait.class));

        String version = this.getDescription().getVersion();

        adminsave = new File("plugins" + File.separator + "SWATtest" + File.separator + "admins.yml");
        adminconfig = YamlConfiguration.loadConfiguration(adminsave);
        if (!adminsave.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                adminsave.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            adminconfig.set("admins", admins);
            try {
                adminconfig.save(adminsave);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        buildersave = new File("plugins" + File.separator + "SWATtest" + File.separator + "builders.yml");
        builderconfig = YamlConfiguration.loadConfiguration(buildersave);
        if (!buildersave.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                buildersave.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            builderconfig.set("builders", builders);
            try {
                builderconfig.save(buildersave);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        bannedsave = new File("plugins" + File.separator + "SWATtest" + File.separator + "banned.yml");
        bannedconfig = YamlConfiguration.loadConfiguration(bannedsave);
        if (!bannedsave.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                bannedsave.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            bannedconfig.set("banned", banned);
            try {
                bannedconfig.save(bannedsave);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        verifiedsave = new File("plugins" + File.separator + "SWATtest" + File.separator + "verified.yml");
        verifiedconfig = YamlConfiguration.loadConfiguration(verifiedsave);
        if (!verifiedsave.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                verifiedsave.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            verifiedconfig.set("verified", verified);
            try {
                verifiedconfig.save(verifiedsave);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (Object name : adminconfig.getList("admins")) {
            admins.add(String.valueOf(name));
        }

        for (Object name : builderconfig.getList("builders")) {
            builders.add(String.valueOf(name));
        }

        for (Object name : bannedconfig.getList("banned")) {
            String[] strings = ((String) name).split("~");
            banned.put(strings[0], strings[1]);
            bannedlist.add(strings[0]+"~"+strings[1]+"~"+strings[2]);
        }

        for (Object name : verifiedconfig.getList("verified")) {
            verified.add(String.valueOf(name));
        }

        commandlist = PluginCommandYamlParser.parse(plugin);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&lDas SWATtest-Plugin wurde reloaded! &7&l- &c&lVersion " + version));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7➝ Bei Bugs muss der Spieler rejoinen."));

            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerDeathListener.spawnprotection.put(player.getUniqueId(), Boolean.FALSE);
                PlayerJoinListener.playersave.put(player.getUniqueId(), new File("data" + File.separator + player.getUniqueId() + ".yml"));
                PlayerJoinListener.playerconfig.put(player.getUniqueId(), YamlConfiguration.loadConfiguration(PlayerJoinListener.playersave.get(player.getUniqueId())));
                team0.addEntry(player.getName());
                player.setOp(Admins.isAdmin(player));
            }
        }, 20L);

        for (Command tempcommand : commandlist) { commandtoggles.put(tempcommand.getName(), Boolean.TRUE); }

        new WorldCreator("Baustelle").createWorld();
        new WorldCreator("Training").createWorld();

        System.out.println("Plugin erfolgreich geladen.");
    }

    public void onDisable() {
        if (BombeCommand.bombloc != null &&
                Bukkit.getWorld("Training").getBlockAt(BombeCommand.bombloc).getType() == Material.TNT)
            Bukkit.getWorld("Training").getBlockAt(BombeCommand.bombloc).setType(Material.AIR);

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
        getCommand("inv").setExecutor(new InvCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("admins").setExecutor(new AdminsCommand());
        getCommand("builders").setExecutor(new BuildersCommand());
        getCommand("buildmode").setExecutor(new BuildmodeCommand());
        getCommand("sprenggürtel").setExecutor(new SprengguertelCommand());
        getCommand("disablecommand").setExecutor(new DisableCommandCommand());
        getCommand("disableitem").setExecutor(new DisableItemCommand());
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("annehmen").setExecutor(new AnnehmenCommand());
        getCommand("ablehnen").setExecutor(new AblehnenCommand());
        getCommand("blockanfragen").setExecutor(new BlockanfragenCommand());
        getCommand("elytradamage").setExecutor(new ElytraDamageCommand());
        getCommand("sit").setExecutor(new SitCommand());
        getCommand("kill").setExecutor(new KillCommand());
        getCommand("verify").setExecutor(new VerifyCommand());
        getCommand("world").setExecutor(new WorldCommand());
        getCommand("baustelle").setExecutor(new BaustelleCommand());
    }
}