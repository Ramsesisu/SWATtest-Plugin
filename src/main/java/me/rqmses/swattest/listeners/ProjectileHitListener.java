package me.rqmses.swattest.listeners;

import org.bukkit.Location;
import org.bukkit.Particle;
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

    List<Entity> entitylist;

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        event.getEntity().remove();

        String bullettype = event.getEntity().getCustomName();

        if (Objects.equals(bullettype, "rpg")) {
            Location loc = event.getHitBlock().getLocation();
            loc.getWorld().createExplosion(loc, 10, true);
            loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);

            Player nearplayer;
            entitylist = event.getEntity().getNearbyEntities(2, 2, 2);
            for (Entity entity : entitylist) {
                nearplayer = (Player) entity;
                nearplayer.damage(100);
                nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*10,2));
                nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3,1));
            }
            entitylist.clear();
            entitylist = event.getEntity().getNearbyEntities(6, 6, 6);
            for (Entity entity : entitylist) {
                nearplayer = (Player) entity;
                nearplayer.damage(35);
                nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*10,2));
                nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3,1));
            }
            entitylist.clear();
            entitylist = event.getEntity().getNearbyEntities(10, 10, 10);
            for (Entity entity : entitylist) {
                nearplayer = (Player) entity;
                nearplayer.damage(20);
                nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*5,1));
                nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3,1));
            }
            entitylist.clear();
            entitylist = event.getEntity().getNearbyEntities(15, 15, 15);
            for (Entity entity : entitylist) {
                nearplayer = (Player) entity;
                nearplayer.damage(8);
                nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*2,1));
                nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*2,1));
            }
            entitylist.clear();
        }
    }
}
