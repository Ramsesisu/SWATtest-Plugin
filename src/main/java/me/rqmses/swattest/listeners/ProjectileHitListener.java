package me.rqmses.swattest.listeners;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

public class ProjectileHitListener implements Listener {
  static List<Entity> entitylist;
  
  @EventHandler
  public void onProjectileHit(ProjectileHitEvent event) {
    if (event.getEntity() instanceof Arrow) {
      String[] bulletinfo = event.getEntity().getCustomName().split(":");
      String weapontype = bulletinfo[0];
      if (Objects.equals(weapontype, "rpg")) {
        if (event.getHitBlock() != null) {
          exlode(event.getHitBlock().getLocation(), event.getEntity());
        }
      }
      event.getEntity().remove();
    }
  }

  public static void exlode(Location loc, Entity projectile) {
    loc.getWorld().createExplosion(loc, 10.0F, true);
    loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
    entitylist = projectile.getNearbyEntities(2.0D, 2.0D, 2.0D);
    for (Entity entity : entitylist) {
      if (entity instanceof Player) {
        Player nearplayer = (Player) entity;
        nearplayer.damage(100.0D);
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 2));
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
      }
    }
    entitylist.clear();
    entitylist = projectile.getNearbyEntities(6.0D, 6.0D, 6.0D);
    for (Entity entity : entitylist) {
      if (entity instanceof Player) {
        Player nearplayer = (Player) entity;
        nearplayer.damage(35.0D);
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 2));
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
      }
    }
    entitylist.clear();
    entitylist = projectile.getNearbyEntities(10.0D, 10.0D, 10.0D);
    for (Entity entity : entitylist) {
      if (entity instanceof Player) {
        Player nearplayer = (Player) entity;
        nearplayer.damage(20.0D);
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
      }
    }
    entitylist.clear();
    entitylist = projectile.getNearbyEntities(15.0D, 15.0D, 15.0D);
    for (Entity entity : entitylist) {
      if (entity instanceof Player) {
        Player nearplayer = (Player) entity;
        nearplayer.damage(8.0D);
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 1));
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
      }
    }
    entitylist.clear();
  }
}
