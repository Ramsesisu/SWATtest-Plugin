package me.rqmses.swattest;

import me.rqmses.swattest.commands.*;
import me.rqmses.swattest.listeners.*;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SWATtest extends JavaPlugin implements Listener {
    public static SWATtest plugin;

    public static NPCRegistry registry;

    public void onEnable() {
        plugin = this;
        listenerRegistration();
        commandRegistration();

        registry =  CitizensAPI.getNPCRegistry();

        System.out.println("Plugin erfolgreich geladen.");
    }

    public void onDisable() {
        if (BombeCommand.bombloc != null &&
                Bukkit.getWorld("world").getBlockAt(BombeCommand.bombloc).getType() == Material.TNT)
            Bukkit.getWorld("world").getBlockAt(BombeCommand.bombloc).setType(Material.AIR);
        for (NPC npc : registry) {
            npc.destroy();
        }
        System.out.println("Plugin erfolgreich heruntergefahren.");
    }

    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
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
        pluginManager.registerEvents(new PlayerSwitchItemListener(), this);
        pluginManager.registerEvents(new NPCDeathListener(), this);
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
        getCommand("resetdata").setExecutor(new ResetDataCommand());
        getCommand("trainingsbot").setExecutor(new NPCCommand());
    }
}