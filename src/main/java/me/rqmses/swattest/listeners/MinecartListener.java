package me.rqmses.swattest.listeners;

import me.rqmses.swattest.commands.CarCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Objects;

public class MinecartListener implements Listener {

    public static final ArrayList<String> minecartplayerslist = new ArrayList<>();

    @EventHandler
    public void onCarEnter(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player) {
            Player player = (Player) event.getEntered();
            Player owner = Bukkit.getServer().getPlayer(event.getVehicle().getCustomName());
            if (!Objects.equals(player.getName(), owner.getName())) {
                event.setCancelled(true);
            } else {
                minecartplayerslist.add(player.getName());
            }
        }
    }

    @EventHandler
    public void onCarExit(VehicleExitEvent event) {
        if (event.getExited() instanceof Player) {
            Player player = (Player) event.getExited();
            minecartplayerslist.remove(player.getName());
            if (CarCommand.cartasks.get(player.getName()) != null) {
                CarCommand.cartasks.get(player.getName()).cancel();
            }
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    @EventHandler
    public void onCarHit(VehicleDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onCarCollision(VehicleEntityCollisionEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getVehicle().getPassengers().contains(Bukkit.getServer().getPlayer(event.getVehicle().getCustomName()))) {
                if (event.getEntity() != Bukkit.getServer().getPlayer(event.getVehicle().getCustomName())) {
                    Player player = (Player) event.getEntity();
                    player.chat("/car start f");
                    player.setVelocity(Bukkit.getServer().getPlayer(event.getVehicle().getCustomName()).getLocation().getDirection().multiply(3));
                    player.damage(14);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 8, 2));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 8, 1));
                }
            }
        }
    }
}
