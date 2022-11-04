package me.rqmses.swattest.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.material.MaterialData;

public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        event.getEntity().remove();
    }
}
