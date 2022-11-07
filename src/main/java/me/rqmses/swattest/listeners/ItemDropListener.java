package me.rqmses.swattest.listeners;

import me.rqmses.swattest.SWATtest;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

public class ItemDropListener implements Listener {

    public static final HashMap<String, Long> cooldowns = new HashMap<>();
    public static final HashMap<String, Integer> cooldowntimes = new HashMap<>();

    List<Entity> nearPlayers = new ArrayList<>();;

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {

        ItemStack flash = new ItemStack(Material.SLIME_BALL);
        ItemMeta flashmeta = flash.getItemMeta();
        flashmeta.setDisplayName(ChatColor.GRAY + "Blendgranate");
        flash.setItemMeta(flashmeta);

        // Messer

        if (event.getItemDrop().getItemStack().getType() == Material.FEATHER) {
            event.setCancelled(true);
        }

        // Kev

        if (event.getItemDrop().getItemStack().getType() == Material.LEATHER_CHESTPLATE) {
            event.setCancelled(true);
        }

        // Elytra

        if (event.getItemDrop().getItemStack().getType() == Material.ELYTRA) {
            event.setCancelled(true);
        }

        // Schild

        if (event.getItemDrop().getItemStack().getType() == Material.SHIELD) {
            event.setCancelled(true);
        }

        // Flashes

        if (event.getItemDrop().getItemStack().getType() == Material.SLIME_BALL) {
            event.setCancelled(true);
        }

        // Tazer

        if (event.getItemDrop().getItemStack().getType() == Material.WOOD_HOE) {
            event.setCancelled(true);
        }

        // Flammi

        if (event.getItemDrop().getItemStack().getType() == Material.BLAZE_POWDER) {
            event.setCancelled(true);
        }

        // RPG

        if (event.getItemDrop().getItemStack().getType() == Material.GOLD_AXE) {
            event.setCancelled(true);
        }
    }

    @SuppressWarnings("SameReturnValue")
    @EventHandler

    public boolean onFlashDrop(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (player.getInventory().getItemInMainHand().getType() == Material.SLIME_BALL) {
                PlayerDeathListener.spawnprotection.put(player.getName(), false);

                if (cooldowns.containsKey(event.getPlayer().getName())) {
                    long secondsLeft = ((cooldowns.get(player.getName()))) + cooldowntimes.get(player.getName()) - (System.currentTimeMillis());
                    if (secondsLeft > 0) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
                        return true;
                    }
                }
                cooldowns.put(player.getName(), System.currentTimeMillis());
                event.setCancelled(true);

                ItemStack flash = new ItemStack(Material.SLIME_BALL);
                ItemMeta flashmeta = flash.getItemMeta();
                flashmeta.setDisplayName(ChatColor.GRAY + "Blendgranate");
                flash.setItemMeta(flashmeta);

                Item thrownFlash = player.getWorld().dropItem(player.getLocation(), flash);
                Vector flashvel = player.getLocation().getDirection();
                flashvel.multiply(1.225);
                thrownFlash.setVelocity(flashvel);

                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);

                cooldowntimes.put(player.getName(), 3000);
            }
        } return true;
    }

    Player nextplayer;

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        if (event.getEntity().getItemStack().getType() == Material.SLIME_BALL) {
            Bukkit.getScheduler().runTaskLater(SWATtest.plugin, () -> {
                event.getEntity().remove();

                nearPlayers.clear();
                nearPlayers = new ArrayList<>(getEntitiesAroundPoint(event.getEntity().getLocation(), 5));
                for (Entity nearPlayer: nearPlayers) {
                    if (nearPlayer instanceof Player) {
                        nextplayer = (Player) nearPlayer;
                        Random rand = new Random();

                        if (PlayerDeathListener.spawnprotection.get(nextplayer.getName()) != null) {
                            if (!PlayerDeathListener.spawnprotection.get(nextplayer.getName())) {
                                nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rand.nextInt(140) + 60, 0));
                                nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rand.nextInt(200) + 200, 0));
                            }
                        }
                    }
                }
                nearPlayers.clear();
                nearPlayers = new ArrayList<>(getEntitiesAroundPoint(event.getEntity().getLocation(), 10));
                for (Entity nearPlayer: nearPlayers) {
                    if (nearPlayer instanceof Player) {
                        nextplayer = (Player) nearPlayer;
                        Random rand = new Random();

                        if (PlayerDeathListener.spawnprotection.get(nextplayer.getName()) != null) {
                            if (!PlayerDeathListener.spawnprotection.get(nextplayer.getName())) {
                                nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rand.nextInt(100), 0));
                                nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rand.nextInt(200), 0));
                            }
                        }
                    }
                }
            }, 60L);
        }
    }

    @EventHandler
    public void onItemPickUp(EntityPickupItemEvent event) {
        if (event.getItem().getItemStack().getType() == Material.SLIME_BALL) {
            event.setCancelled(true);
        }
    }

    public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
        List<Entity> entities = new ArrayList<>();
        entities.clear();
        World world = location.getWorld();

        int smallX = (int) Math.floor((location.getX() - radius) / 16.0D);
        int bigX = (int) Math.floor((location.getX() + radius) / 16.0D);
        int smallZ = (int) Math.floor((location.getZ() - radius) / 16.0D);
        int bigZ = (int) Math.floor((location.getZ() + radius) / 16.0D);

        for (int x = smallX; x <= bigX; x++) {
            for (int z = smallZ; z <= bigZ; z++) {
                if (world.isChunkLoaded(x, z)) {
                    entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities()));
                }
            }
        }

        entities.removeIf(entity -> entity.getLocation().distanceSquared(location) > radius * radius);
        return entities;
    }
}
