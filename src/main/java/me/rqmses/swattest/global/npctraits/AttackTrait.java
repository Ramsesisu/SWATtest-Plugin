package me.rqmses.swattest.global.npctraits;

import com.mojang.authlib.properties.Property;
import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.Functions;
import me.rqmses.swattest.global.Items;
import me.rqmses.swattest.listeners.PlayerDeathListener;
import me.rqmses.swattest.listeners.PlayerInteractListener;
import net.citizensnpcs.api.ai.AttackStrategy;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.api.util.DataKey;
import net.minecraft.server.v1_12_R1.PathfinderGoalArrowAttack;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@TraitName("attacktrait")
public class AttackTrait extends Trait {
    public AttackTrait() {
        super("attacktrait");
    }

    public static final HashMap<String, BukkitTask> NPCtargetPlayer = new HashMap<String, org.bukkit.scheduler.BukkitTask>();
    public static final HashMap<String, BukkitTask> NPCfacePlayer = new HashMap<>();
    public static final HashMap<String, BukkitTask> NPCnpcShoot = new HashMap<>();

    Player result = null;

    int taskID1;
    int taskID2;

    @Override
    public void onSpawn() {
        Player npcplayer = (Player) npc.getEntity();
        npcplayer.setGameMode(GameMode.SURVIVAL);
        ((CraftPlayer) npcplayer).setMaxHealth(40);
        ((CraftPlayer) npcplayer).setHealth(40);

        npc.setProtected(false);

        taskID1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(SWATtest.plugin, new Runnable() {
            @Override
            public void run() {
                result = null;
                double lastDistance = Double.MAX_VALUE;
                for (Player p : npcplayer.getWorld().getPlayers()) {
                    if (p.getName().contains("-KI")) {
                        continue;
                    }
                    if (p.getGameMode() == GameMode.SURVIVAL) {
                        double distance = npc.getEntity().getLocation().distance(p.getLocation());
                        if (distance > 25) {
                            continue;
                        }
                        if (distance < lastDistance) {
                            lastDistance = distance;
                            result = p;
                            if (!((CraftPlayer) npcplayer).getInventory().contains(Material.DIAMOND_BARDING)) {
                                ((CraftPlayer) npcplayer).getInventory().setItemInMainHand(Items.getM4());
                            }
                            npc.getNavigator().setTarget(result, true);
                            npc.getNavigator().getDefaultParameters().useNewPathfinder(true);
                            npc.getNavigator().getDefaultParameters().avoidWater(true);
                            ((Player) npc.getEntity()).setSprinting(true);
                        }
                    }
                }
                if (result == null) {
                    npc.getNavigator().cancelNavigation();
                }
            }
        }, 60L, 60L);

        taskID2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(SWATtest.plugin, new Runnable() {
            @Override
            public void run() {
                npc.faceLocation(npc.getNavigator().getTargetAsLocation());
                if (result != null) {
                    if (((Player) npc.getEntity()).hasLineOfSight(result)) {
                        PlayerInteractListener.shootM4((Player) npc.getEntity());
                    }
                    if (((Player) npc.getEntity()).getHealth() < 30) {
                        ((Player) npc.getEntity()).chat("/use Kokain");
                    }
                }
            }
        }, 60L, 8L);
    }

    String deathmessage;

    @EventHandler
    public void onDeath(NPCDeathEvent event) {
        Bukkit.getScheduler().cancelTask(taskID1);
        Bukkit.getScheduler().cancelTask(taskID2);

        deathmessage = ChatColor.translateAlternateColorCodes('&', "&7" + event.getNPC().getName() + " &f&lwurde getötet.");

        List<Entity> nearPlayers = new ArrayList<>(getEntitiesAroundPoint(event.getNPC().getEntity().getLocation(), 30.0D));
        List<Entity> nearPlayers2 = new ArrayList<>();
        nearPlayers.forEach(playerName -> {
            nearPlayers2.remove(playerName);
            nearPlayers2.add(playerName);
        });
        nearPlayers2.forEach(playerName2 -> playerName2.sendMessage(deathmessage));

        npc.destroy();
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

