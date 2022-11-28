package me.rqmses.swattest.global.npctraits;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.commands.TrainingsbotCommand;
import me.rqmses.swattest.global.Items;
import me.rqmses.swattest.listeners.PlayerInteractListener;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

@TraitName("abaimtrait")
public class AbaimTrait extends Trait {
    public AbaimTrait() {
        super("abaimtrait");
    }

    public static final HashMap<UUID, Integer> taskID1 = new HashMap<>();
    public static final HashMap<UUID, Integer> taskID2 = new HashMap<>();

    Player result = null;
    boolean found = false;

    @Override
    public void onSpawn() {
        NPC npcplayer = getNPC();
        ((Player) npcplayer.getEntity()).setGameMode(GameMode.SURVIVAL);
        ((Player) npcplayer.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0D);
        ((Player) npcplayer.getEntity()).setHealth(40);

        npcplayer.setProtected(false);

        result = null;
        found = false;

        taskID1.put(npcplayer.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(SWATtest.plugin, () -> {
            double lastDistance = Double.MAX_VALUE;
            found = false;
            for (Player p : npcplayer.getEntity().getWorld().getPlayers()) {
                if (p.hasMetadata("NPC")) {
                    continue;
                }
                if (p.getGameMode() == GameMode.SURVIVAL) {
                    double distance = npcplayer.getEntity().getLocation().distance(p.getLocation());
                    if (distance > 50) {
                        continue;
                    }
                    if (distance < lastDistance) {
                        lastDistance = distance;
                        result = p;
                        if (!((Player) npcplayer.getEntity()).getInventory().contains(Material.DIAMOND_BARDING)) {
                            ((Player) npcplayer.getEntity()).getInventory().setItemInMainHand(Items.getM4());
                        }

                        found = true;
                    }
                }
            }
            if (!found) {
                result = null;
            }
        }, 60L, 10L));

        taskID2.put(npcplayer.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(SWATtest.plugin, () -> {
            if (result != null) {
                boolean sight = true;
                if (((CraftPlayer) npcplayer.getEntity()).hasPotionEffect(PotionEffectType.BLINDNESS) || ((CraftPlayer) npcplayer.getEntity()).hasPotionEffect(PotionEffectType.CONFUSION)) {
                    npcplayer.faceLocation(result.getLocation());
                } else {
                    npcplayer.faceLocation(result.getLocation());
                    sight = ((Player) npcplayer.getEntity()).hasLineOfSight(result);
                }
                if (sight) {
                    PlayerInteractListener.shootM4((Player) npcplayer.getEntity());
                    if (result.getCustomName().equals("dead")) {
                        result = null;
                    }
                }
                if (((Player) npcplayer.getEntity()).getHealth() < 30) {
                    ((Player) npcplayer.getEntity()).chat("/use Kokain");
                }
            }
        }, 60L, 10L));
    }

    @Override
    public void onDespawn() {
        Bukkit.getScheduler().cancelTask(taskID1.get(getNPC().getUniqueId()));
        Bukkit.getScheduler().cancelTask(taskID2.get(getNPC().getUniqueId()));

        npc.destroy();
    }

    @EventHandler
    public void onDeath(NPCDeathEvent event) {
        TrainingsbotCommand.NPCList.remove(npc);
        TrainingsbotCommand.NPCListPlayer.put(event.getNPC().data().get("origin"), TrainingsbotCommand.NPCListPlayer.get(event.getNPC().data().get("origin")) - 1);
        event.getNPC().despawn();
    }
}

