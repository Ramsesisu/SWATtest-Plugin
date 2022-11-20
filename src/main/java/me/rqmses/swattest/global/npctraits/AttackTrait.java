package me.rqmses.swattest.global.npctraits;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.Items;
import me.rqmses.swattest.listeners.PlayerInteractListener;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.*;

@TraitName("attacktrait")
public class AttackTrait extends Trait {
    public AttackTrait() {
        super("attacktrait");
    }

    public static final HashMap<UUID, Integer> taskID1 = new HashMap<>();
    public static final HashMap<UUID, Integer> taskID2 = new HashMap<>();

    Player result = null;

    @Override
    public void onSpawn() {
        Player npcplayer = (Player) npc.getEntity();
        npcplayer.setGameMode(GameMode.SURVIVAL);
        ((CraftPlayer) npcplayer).setMaxHealth(40);
        ((CraftPlayer) npcplayer).setHealth(40);

        npc.setProtected(false);

        taskID1.put(npc.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(SWATtest.plugin, new Runnable() {
            @Override
            public void run() {
                result = null;
                double lastDistance = Double.MAX_VALUE;
                for (Player p : npcplayer.getWorld().getPlayers()) {
                    if (!((Player) npc.getEntity()).hasLineOfSight(p)) {
                        continue;
                    }
                    if (p.hasMetadata("NPC")) {
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
        }, 60L, 60L));

        taskID2.put(npc.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(SWATtest.plugin, new Runnable() {
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
        }, 60L, 8L));
    }

    @EventHandler
    public void onDeath(NPCDeathEvent event) {
        Bukkit.getScheduler().cancelTask(taskID1.get(event.getNPC().getUniqueId()));
        Bukkit.getScheduler().cancelTask(taskID2.get(event.getNPC().getUniqueId()));
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

