package me.rqmses.swattest.global.npctraits;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.Items;
import me.rqmses.swattest.listeners.PlayerInteractListener;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

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
        NPC npcplayer = getNPC();
        ((Player) npcplayer.getEntity()).setGameMode(GameMode.SURVIVAL);
        ((Player) npcplayer.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0D);
        ((Player) npcplayer.getEntity()).setHealth(40);

        npcplayer.setProtected(false);

        result = null;

        taskID1.put(npcplayer.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(SWATtest.plugin, () -> {
            double lastDistance = Double.MAX_VALUE;
            result = null;
            for (Player p : npcplayer.getEntity().getWorld().getPlayers()) {
                if (!((Player) npcplayer.getEntity()).hasLineOfSight(p)) {
                    continue;
                }
                if (p.hasMetadata("NPC")) {
                    continue;
                }
                if (p.getGameMode() == GameMode.SURVIVAL) {
                    double distance = npcplayer.getEntity().getLocation().distance(p.getLocation());
                    if (distance > 25) {
                        continue;
                    }
                    if (distance < lastDistance) {
                        lastDistance = distance;
                        result = p;
                        if (!((Player) npcplayer.getEntity()).getInventory().contains(Material.DIAMOND_BARDING)) {
                            ((Player) npcplayer.getEntity()).getInventory().setItemInMainHand(Items.getM4());
                        }
                        npcplayer.getNavigator().setTarget(result, true);
                        npcplayer.getNavigator().getDefaultParameters().useNewPathfinder(true);
                        npcplayer.getNavigator().getDefaultParameters().avoidWater(true);
                        ((Player) npcplayer.getEntity()).setSprinting(true);
                    }
                }
            }
            if (result == null) {
                npcplayer.getNavigator().cancelNavigation();
            }
        }, 60L, 60L));

        taskID2.put(npcplayer.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(SWATtest.plugin, () -> {
            if (result != null) {
                boolean sight = true;
                if (((CraftPlayer) npcplayer.getEntity()).hasPotionEffect(PotionEffectType.BLINDNESS) || ((CraftPlayer) npcplayer.getEntity()).hasPotionEffect(PotionEffectType.CONFUSION)) {
                    npcplayer.getNavigator().setTarget(result.getLocation());
                } else {
                    npcplayer.faceLocation(result.getLocation());
                    sight = ((Player) npcplayer.getEntity()).hasLineOfSight(result);
                }
                if (result != null) {
                    if (sight) {
                        PlayerInteractListener.shootM4((Player) npcplayer.getEntity());
                    }
                    if (((Player) npcplayer.getEntity()).getHealth() < 30) {
                        ((Player) npcplayer.getEntity()).chat("/use Kokain");
                    }
                }
            }
        }, 60L, 10L));
    }

    @Override
    public void onDespawn() {
        Bukkit.getScheduler().cancelTask(taskID1.get(getNPC().getUniqueId()));
        Bukkit.getScheduler().cancelTask(taskID2.get(getNPC().getUniqueId()));
    }

    @EventHandler
    public void onDeath(NPCDeathEvent event) {
        event.getNPC().despawn();
    }
}

