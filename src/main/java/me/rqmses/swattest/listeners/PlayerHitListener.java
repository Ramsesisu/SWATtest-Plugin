package me.rqmses.swattest.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

import static me.rqmses.swattest.commands.BuildmodeCommand.buildmode;
import static me.rqmses.swattest.commands.VanishCommand.hidden;

public class PlayerHitListener implements Listener {
  public final HashMap<String, Long> cooldowns = new HashMap<>();
  
  public static Player hitter;
  
  public static int cooldownTime;
  
  @EventHandler
  public boolean onPlayerHit(EntityDamageByEntityEvent event) {
    if (event.getEntity() instanceof Player && 
      event.getDamager().getType() != EntityType.ARROW && 
      event.getDamager() instanceof Player) {
      if (event.getEntity().getName().contains("-KI")) {return true;}
      hitter = (Player) event.getDamager();
      if (hidden.contains(hitter) || buildmode.contains(hitter)) {
        event.setCancelled(true);
        return true;
      }
      PlayerDeathListener.setKiller(hitter.getName());
      if (!hitter.getName().contains("-KI")) {
        if (PlayerDeathListener.spawnprotection.get(hitter.getUniqueId())) {
          PlayerDeathListener.spawnprotection.put(hitter.getUniqueId(), Boolean.FALSE);
          hitter.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
        }
      }
      if (hitter.getInventory().getItemInMainHand().getType() == Material.FEATHER) {
        if (this.cooldowns.containsKey(hitter.getName())) {
          long secondsLeft = this.cooldowns.get(hitter.getName()) + cooldownTime - System.currentTimeMillis();
          if (secondsLeft > 0L) {
            event.setCancelled(true);
            return true;
          } 
        }
        Player player = (Player)event.getEntity();
        ItemStack messer = ((Player)event.getDamager()).getPlayer().getInventory().getItemInMainHand();
        ItemMeta meta = messer.getItemMeta();
        String strlore = meta.getLore().toString();
        String[] ammos = strlore.split("/");
        ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
        int ammo = Integer.parseInt(ammos[0]);
        if (ammo == 0) {
          event.setCancelled(true);
          return true;
        }
        if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE)
          if (((Player)event.getEntity()).getInventory().getChestplate().getDurability() < 79) {
            ((Player)event.getEntity()).getInventory().getChestplate().setDurability((short)(((Player)event.getEntity()).getInventory().getChestplate().getDurability() + 1));
          } else {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            ((Player)event.getEntity()).getInventory().setChestplate(null);
          }
        this.cooldowns.put(hitter.getName(), System.currentTimeMillis());
        event.setDamage(7.0D);
        ArrayList<String> lore = new ArrayList<>();
        String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + "100");
        lore.add(templore);
        meta.setLore(lore);
        messer.setItemMeta(meta);
        cooldownTime = 12000;
      } else {
        event.setDamage(1.0D);
      } 
    } 
    return false;
  }
  
  @EventHandler
  public void onShieldHit(PlayerItemDamageEvent event) {
    Player player = event.getPlayer();
    ItemStack item = event.getItem();
    if (item.getType() == Material.SHIELD) {
      if (hitter != null) {
        if (hitter.getInventory().getItemInMainHand().getType() == Material.FEATHER) {
          item.setDurability((short) (item.getDurability() - 9));
          player.damage(7.0D);
        }
      }
    } else {
      event.setCancelled(true);
    } 
  }
  
  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerUse(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if ((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) && 
      player.getInventory().getItemInMainHand().getType() == Material.FEATHER) {
      hitter = player;
      PlayerDeathListener.spawnprotection.put(hitter.getUniqueId(), Boolean.FALSE);
    } 
  }
}
