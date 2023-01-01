package me.rqmses.swattest.global;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SoundUtils {

    public static void playSound(World world, Location loc, Integer range, Float pitch) {
        ArrayList<Entity> entities = (ArrayList<Entity>) world.getNearbyEntities(loc, range, range, range);
        for (Entity entity : Functions.removeDuplicates(entities)) {
            if (entity instanceof Player) {
                Player player = ((Player) entity).getPlayer();
                player.playSound(loc, Sound.ENTITY_FIREWORK_BLAST, (float) (50 / player.getLocation().distance(loc)), pitch);
            }
        }
    }
}