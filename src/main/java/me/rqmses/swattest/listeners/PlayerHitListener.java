package me.rqmses.swattest.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class PlayerHitListener implements Listener {

    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

    public static Player hitter;

    public static int cooldownTime;

    @EventHandler
    public boolean onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (!(event.getDamager().getType() == EntityType.ARROW)) {
                hitter = (Player) event.getDamager();
                Player player = (Player) event.getEntity();

                PlayerDeathListener killer = new PlayerDeathListener();
                killer.setKiller(hitter.getName());

                if (player.getInventory().getChestplate() != null) {
                    if (((Player) event.getEntity()).getInventory().getChestplate().getDurability() < 79) {
                        ((Player) event.getEntity()).getInventory().getChestplate().setDurability((short) (((Player) event.getEntity()).getInventory().getChestplate().getDurability() + 1));
                    } else {
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                        ((Player) event.getEntity()).getInventory().setChestplate(null);
                    }
                }

                if (hitter.getInventory().getItemInMainHand().getType() == Material.FEATHER) {
                    if (cooldowns.containsKey(hitter.getName())) {
                        long secondsLeft = ((cooldowns.get(hitter.getName())) + cooldownTime) - (System.currentTimeMillis());
                        if (secondsLeft > 0) {
                            event.setCancelled(true);
                            hitter.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
                            return true;
                        }
                    }
                    cooldowns.put(hitter.getName(), System.currentTimeMillis());

                    event.setDamage(7);

                    cooldownTime = 12000;
                }
            }
        }
        return false;
    }

    @EventHandler
    public boolean onShieldHit(PlayerItemDamageEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item.getType() == Material.SHIELD) {
            if (hitter.getInventory().getItemInMainHand().getType() == Material.FEATHER) {
                item.setDurability((short) (item.getDurability() - 9));
                player.damage(7);
            }
        } else {
            event.setCancelled(true);
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public static boolean onPlayerUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (player.getInventory().getItemInMainHand().getType() == Material.FEATHER) {
                hitter = player;
            }
        }
        return false;
    }
}
