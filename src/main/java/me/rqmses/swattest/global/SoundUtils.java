package me.rqmses.swattest.global;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SoundUtils extends BukkitRunnable {
    private final int range;
    private final Location music;
    private final Player player;
    private final float pitch;

    public SoundUtils(JavaPlugin plugin, Player player, Location music, int range, float pitch) {
        this.player = player;
        this.music = music;
        this.range = range;
        this.pitch = pitch;

        runTask(plugin);
    }

    @Override
    public void run() {
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, convertForSound((float) player.getLocation().distance(music), range), pitch);
    }

    private static float convertForSound(float x, int range) {
        return Math.max(0, 1 - (x / range));
    }
}