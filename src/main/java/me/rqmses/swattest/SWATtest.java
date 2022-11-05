package me.rqmses.swattest;

import me.rqmses.swattest.commands.*;
import me.rqmses.swattest.listeners.*;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SWATtest extends JavaPlugin implements Listener {

    public static SWATtest plugin;

    @Override
    public void onEnable() {
        System.out.println("Plugin erfolgreich geladen.");

        plugin = this;

        listenerRegistration();
        commandRegistration();

        Bukkit.getWorld("world").getLivingEntities().forEach((Entity singleEntity) -> {
            if (singleEntity.getType() == EntityType.CHICKEN) {
                singleEntity.remove();
            }
        });

        World myWorld = Bukkit.getWorld("world");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {

                int X = -1000 + (int)(Math.random() * ((2000 - -1000) + 1));
                int Z = -600 + (int)(Math.random() * ((1000 - -600) + 1));

                Location spawnLocation = new Location(myWorld, X, 70, Z);

                EntityType TYPE_TO_SEARCH = EntityType.CHICKEN;
                long amount = myWorld.getLivingEntities().stream().map(LivingEntity::getType).filter(TYPE_TO_SEARCH::equals).count();

                if (amount < 100) {
                    Entity spawnedChicken = myWorld.spawnEntity(spawnLocation, EntityType.CHICKEN);
                }
            }
        }, 20L, 20L);
    }

    @Override
    public void onDisable() {
        System.out.println("Plugin erfolgreich heruntergefahren.");

        if (BombeCommand.bombloc != null) {
            if (Bukkit.getWorld("world").getBlockAt(BombeCommand.bombloc).getType() == Material.TNT) {
                Bukkit.getWorld("world").getBlockAt(BombeCommand.bombloc).setType(Material.AIR);
            }
        }
    }

    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
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
    }

    private void commandRegistration() {
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("equip").setExecutor(new EquipCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("use").setExecutor(new UseCommand());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("camera").setExecutor(new CameraCommand());
        getCommand("bombe").setExecutor(new BombeCommand());
        getCommand("navi").setExecutor(new NaviCommand());
        getCommand("cooldown").setExecutor(new CoolDownCommand());
    }

    public static SWATtest getInstance() {
        return plugin;
    }
}
