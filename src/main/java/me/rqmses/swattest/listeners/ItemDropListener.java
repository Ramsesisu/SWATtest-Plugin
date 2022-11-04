package me.rqmses.swattest.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static me.rqmses.swattest.SWATtest.plugin;

public class ItemDropListener implements Listener {

    public static HashMap<String, Long> cooldowns = new HashMap<>();
    public static HashMap<String, Integer> cooldowntimes = new HashMap<>();

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
    }

    @EventHandler

    public boolean onFlashDrop(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (player.getInventory().getItemInMainHand().getType() == Material.SLIME_BALL) {
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

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        if (event.getEntity().getItemStack().getType() == Material.SLIME_BALL) {
            FlashDelay(event);
        }
    }

    public void FlashDelay(ItemSpawnEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            List<Entity> entitylist1 = event.getEntity().getNearbyEntities(5, 5, 5);
            for (Entity entity : entitylist1) {
                if (entity.getType() == EntityType.PLAYER) {

                    Player player = (Player) entity;

                    Random rand = new Random();

                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rand.nextInt(140) + 60, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rand.nextInt(200) + 200, 0));

                }
            }

            List<Entity> entitylist2 = event.getEntity().getNearbyEntities(10, 10, 10);
            for (Entity entity : entitylist2) {
                if (entity.getType() == EntityType.PLAYER) {

                    Player player = (Player) entity;

                    Random rand = new Random();

                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rand.nextInt(100), 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rand.nextInt(200), 0));

                }
            }

            event.getEntity().remove();
        }, 60L);
    }

    @EventHandler
    public void onItemPickUp(EntityPickupItemEvent event) {
        if (event.getItem().getItemStack().getType() == Material.SLIME_BALL) {
            event.setCancelled(true);
        }
    }
}
