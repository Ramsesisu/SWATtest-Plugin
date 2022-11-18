package me.rqmses.swattest.listeners;

import java.util.List;
import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ProjectileHitListener implements Listener {
  List<Entity> entitylist;
  
  @EventHandler
  public void onProjectileHit(ProjectileHitEvent event) {
    event.getEntity().remove();
    String bullettype = event.getEntity().getCustomName();
    if (Objects.equals(bullettype, "rpg")) {
      Location loc = event.getHitBlock().getLocation();
      loc.getWorld().createExplosion(loc, 10.0F, true);
      loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
      this.entitylist = event.getEntity().getNearbyEntities(2.0D, 2.0D, 2.0D);
      for (Entity entity : this.entitylist) {
        Player nearplayer = (Player)entity;
        nearplayer.damage(100.0D);
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 2));
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
      } 
      this.entitylist.clear();
      this.entitylist = event.getEntity().getNearbyEntities(6.0D, 6.0D, 6.0D);
      for (Entity entity : this.entitylist) {
        Player nearplayer = (Player)entity;
        nearplayer.damage(35.0D);
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 2));
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
      } 
      this.entitylist.clear();
      this.entitylist = event.getEntity().getNearbyEntities(10.0D, 10.0D, 10.0D);
      for (Entity entity : this.entitylist) {
        Player nearplayer = (Player)entity;
        nearplayer.damage(20.0D);
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
      } 
      this.entitylist.clear();
      this.entitylist = event.getEntity().getNearbyEntities(15.0D, 15.0D, 15.0D);
      for (Entity entity : this.entitylist) {
        Player nearplayer = (Player)entity;
        nearplayer.damage(8.0D);
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 1));
        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
      } 
      this.entitylist.clear();
    } 
  }
}