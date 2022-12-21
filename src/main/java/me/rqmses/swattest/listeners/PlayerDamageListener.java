package me.rqmses.swattest.listeners;

import com.google.common.collect.Sets;
import me.rqmses.swattest.global.Functions;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.DataWatcherRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static me.rqmses.swattest.SWATtest.team0;

public class PlayerDamageListener implements Listener {
  public static Player shooter;

  @EventHandler
  public void onBulletHit(EntityDamageByEntityEvent event) {
    if (event.getEntity() instanceof Player) {
      Player player = (Player) event.getEntity();

      if (event.getDamager() instanceof Player) {
        if (Functions.getTeam(player) == Functions.getTeam((Player) event.getDamager()) && Functions.getTeam(player) != team0) {
          event.setCancelled(true);
        }
      }
      if (PlayerDeathListener.spawnprotection.get(player.getUniqueId()) != null) {
        if (PlayerDeathListener.spawnprotection.get(player.getUniqueId())) {
          event.setCancelled(true);
          return;
        }
      }
      if (event.getDamager().getType() == EntityType.ARROW) {
        String[] bulletinfo = event.getDamager().getCustomName().split(":");
        String weapontype = bulletinfo[0];
        shooter = Bukkit.getPlayer(bulletinfo[1]);
        if (shooter != null) {
          PlayerDeathListener.setKiller(shooter.getName());

          if (Functions.getTeam(player) == Functions.getTeam(shooter) && Functions.getTeam(player) != team0) {
            event.setCancelled(true);
          }
        } else {
          PlayerDeathListener.setKiller("Bot");
        }

        if (Objects.equals(weapontype, "m4"))
          if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().getType() != Material.LEATHER_CHESTPLATE) {
            event.setDamage(7.0D);
          } else if (player.isBlocking()) {
            if (((Player)event.getEntity()).getInventory().getChestplate().getDurability() < 79) {
              ((Player)event.getEntity()).getInventory().getChestplate().setDurability((short)(((Player)event.getEntity()).getInventory().getChestplate().getDurability() + 1));
            } else {
              player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
              ((Player)event.getEntity()).getInventory().setChestplate(null);
            } 
          } else if (((Player)event.getEntity()).getInventory().getChestplate().getDurability() < 76) {
            ((Player)event.getEntity()).getInventory().getChestplate().setDurability((short)(((Player)event.getEntity()).getInventory().getChestplate().getDurability() + 4));
              event.setDamage(1.0D);
          } else {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            ((Player)event.getEntity()).getInventory().setChestplate(null);
              event.setDamage(1.0D);
          }  
        if (Objects.equals(weapontype, "sniper"))
          if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().getType() != Material.LEATHER_CHESTPLATE) {
            event.setDamage(14.5D);
          } else if (player.isBlocking()) {
            if (((Player)event.getEntity()).getInventory().getChestplate().getDurability() < 79) {
              ((Player)event.getEntity()).getInventory().getChestplate().setDurability((short)(((Player)event.getEntity()).getInventory().getChestplate().getDurability() + 1));
            } else {
              player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
              ((Player)event.getEntity()).getInventory().setChestplate(null);
            } 
          } else if (((Player)event.getEntity()).getInventory().getChestplate().getDurability() < 76) {
            ((Player)event.getEntity()).getInventory().getChestplate().setDurability((short)(((Player)event.getEntity()).getInventory().getChestplate().getDurability() + 4));
              event.setDamage(1.0D);
          } else {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            ((Player)event.getEntity()).getInventory().setChestplate(null);
              event.setDamage(1.0D);
          }  
        if (Objects.equals(weapontype, "mp5"))
          if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().getType() != Material.LEATHER_CHESTPLATE) {
            event.setDamage(5.0D);
          } else if (player.isBlocking()) {
            if (((Player)event.getEntity()).getInventory().getChestplate().getDurability() < 79) {
              ((Player)event.getEntity()).getInventory().getChestplate().setDurability((short)(((Player)event.getEntity()).getInventory().getChestplate().getDurability() + 1));
            } else {
              player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
              ((Player)event.getEntity()).getInventory().setChestplate(null);
            } 
          } else if (((Player)event.getEntity()).getInventory().getChestplate().getDurability() < 76) {
            ((Player)event.getEntity()).getInventory().getChestplate().setDurability((short)(((Player)event.getEntity()).getInventory().getChestplate().getDurability() + 4));
              event.setDamage(1.0D);
          } else {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            ((Player)event.getEntity()).getInventory().setChestplate(null);
              event.setDamage(1.0D);
          }  
        if (Objects.equals(weapontype, "jagdflinte"))
          if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().getType() != Material.LEATHER_CHESTPLATE) {
            event.setDamage(11.5D);
          } else if (player.isBlocking()) {
            if (((Player)event.getEntity()).getInventory().getChestplate().getDurability() < 79) {
              ((Player)event.getEntity()).getInventory().getChestplate().setDurability((short)(((Player)event.getEntity()).getInventory().getChestplate().getDurability() + 1));
            } else {
              player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
              ((Player)event.getEntity()).getInventory().setChestplate(null);
            } 
          } else if (((Player)event.getEntity()).getInventory().getChestplate().getDurability() < 76) {
            ((Player)event.getEntity()).getInventory().getChestplate().setDurability((short)(((Player)event.getEntity()).getInventory().getChestplate().getDurability() + 4));
              event.setDamage(1.0D);
          } else {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            ((Player)event.getEntity()).getInventory().setChestplate(null);
              event.setDamage(1.0D);
          }  
        if (Objects.equals(weapontype, "rpg")) {
          event.setDamage(0.0D);
          ProjectileHitListener.exlode(player.getLocation(), event.getDamager());
        }

        ((CraftLivingEntity) player).getHandle().getDataWatcher().set(new DataWatcherObject<>(10, DataWatcherRegistry.b),-1);
      }
    }
  }
  
  @EventHandler
  public void onFallDamage(EntityDamageEvent event) {
    if (event.getEntity() instanceof Player) {
      Player player = (Player)event.getEntity();
      if (event.getCause() == EntityDamageEvent.DamageCause.FALL && 
        event.getDamage() >= 30.0D) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2147483647, 3), true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1), true);
        player.setCustomName("brokenleg");
        player.sendMessage(ChatColor.GRAY + "Du hast dir dein Bein gebrochen!");
      } 
    } 
  }
  
  @EventHandler
  public void onPlayerSprint(PlayerToggleSprintEvent event) {
    Player player = event.getPlayer();
    if (Objects.equals(player.getCustomName(), "brokenleg") && !player.isFlying()) {
      player.damage(1.0D);
      event.setCancelled(true);
    } 
  }
  
  private final Set<UUID> prevPlayersOnGround = Sets.newHashSet();
  
  @EventHandler
  public void onMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    if (player.getVelocity().getY() > 0.0D) {
      double jumpVelocity = 0.41999998688697815D;
      if (player.hasPotionEffect(PotionEffectType.JUMP))
        jumpVelocity += ((player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F); 
      if (event.getPlayer().getLocation().getBlock().getType() != Material.LADDER && this.prevPlayersOnGround.contains(player.getUniqueId()) && 
        !player.isOnGround() && Double.compare(player.getVelocity().getY(), jumpVelocity) == 0)
        if (Objects.equals(player.getCustomName(), "brokenleg") && !player.isFlying())
          player.damage(1.0D);  
    } 
    if (player.isOnGround()) {
      this.prevPlayersOnGround.add(player.getUniqueId());
    } else {
      this.prevPlayersOnGround.remove(player.getUniqueId());
    } 
  }
  
  @EventHandler
  public void onHunger(FoodLevelChangeEvent event) {
    event.setCancelled(true);
  }
}
