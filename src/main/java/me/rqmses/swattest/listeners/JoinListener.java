package me.rqmses.swattest.listeners;

import me.rqmses.swattest.commands.EquipCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.GOLD + event.getPlayer().getName() + ChatColor.YELLOW + " ist nun" +  ChatColor.GREEN + " online" + ChatColor.YELLOW + ".");
        event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
        event.getPlayer().setHealth(40);
        event.getPlayer().getInventory().clear();
        event.getPlayer().setGameMode(GameMode.SURVIVAL);
        event.getPlayer().setBedSpawnLocation(new Location(Bukkit.getWorld(event.getPlayer().getWorld().getName()), 103, 70, 157), true);
        event.getPlayer().teleport(event.getPlayer().getBedSpawnLocation());
        event.getPlayer().setPlayerListName(event.getPlayer().getName());
        event.getPlayer().setCustomName("");
        event.getPlayer().getActivePotionEffects().clear();
        event.getPlayer().setGlowing(false);
        changeName(event.getPlayer().getName(), event.getPlayer());
    }

    public static void changeName(String name, Player player) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle",
                    (Class<?>[]) null);
            try {
                Class.forName("com.mojang.authlib.GameProfile");
            } catch (ClassNotFoundException e) {
                Bukkit.broadcastMessage("CHANGE NAME METHOD DOES NOT WORK IN 1.7 OR LOWER!");
                return;
            }
            Object profile = getHandle.invoke(player).getClass()
                    .getMethod("getProfile")
                    .invoke(getHandle.invoke(player));
            Field ff = profile.getClass().getDeclaredField("name");
            ff.setAccessible(true);
            ff.set(profile, name);
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.hidePlayer(player);
                players.showPlayer(player);
            }
        } catch (NoSuchMethodException | SecurityException
                 | IllegalAccessException | IllegalArgumentException
                 | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
