package me.rqmses.swattest.global.npctraits;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.Items;
import me.rqmses.swattest.listeners.PlayerInteractListener;
import net.citizensnpcs.api.ai.TargetType;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PotionSplashEvent;
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
        Player npcplayer = (Player) npc.getEntity();
        npcplayer.setGameMode(GameMode.SURVIVAL);
        ((CraftPlayer) npcplayer).setMaxHealth(40);
        npcplayer.setHealth(40);

        npc.setProtected(false);

        result = null;

        taskID1.put(npc.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(SWATtest.plugin, () -> {
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
                        if (!npcplayer.getInventory().contains(Material.DIAMOND_BARDING)) {
                            npcplayer.getInventory().setItemInMainHand(Items.getM4());
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
        }, 60L, 60L));

        taskID2.put(npc.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(SWATtest.plugin, () -> {
            Bukkit.broadcastMessage(npc.getNavigator().getTargetType().toString());
            if (((CraftPlayer) npc.getEntity()).hasPotionEffect(PotionEffectType.BLINDNESS) || ((CraftPlayer) npc.getEntity()).hasPotionEffect(PotionEffectType.CONFUSION)) {
                npc.faceLocation(npc.getNavigator().getTargetAsLocation().add(5,0,5));
                if (npc.getNavigator().getTargetType().equals(TargetType.ENTITY)) {
                    npc.getNavigator().setTarget(npc.getNavigator().getTargetAsLocation().add(5, 0, 5));
                }
            } else {
                npc.faceLocation(npc.getNavigator().getTargetAsLocation());
            }
            if (result != null) {
                if (((Player) npc.getEntity()).hasLineOfSight(result)) {
                    PlayerInteractListener.shootM4((Player) npc.getEntity());
                }
                if (((Player) npc.getEntity()).getHealth() < 30) {
                    ((Player) npc.getEntity()).chat("/use Kokain");
                }
            }
        }, 60L, 10L));
    }

    @EventHandler
    public void onDeath(NPCDeathEvent event) {
        Bukkit.getScheduler().cancelTask(taskID1.get(event.getNPC().getUniqueId()));
        Bukkit.getScheduler().cancelTask(taskID2.get(event.getNPC().getUniqueId()));
    }
}

