package me.rqmses.swattest.listeners;

import net.citizensnpcs.api.event.NPCDeathEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NPCDeathListener implements Listener {

    String deathmessage;

    List<Entity> nearPlayers = new ArrayList<>();

    @EventHandler
    public void onNPCDeath(NPCDeathEvent event) {
        deathmessage = ChatColor.GRAY + event.getNPC().getName() + ChatColor.WHITE + " wurde getötet!";

        List<Entity> nearPlayers = new ArrayList<>(getEntitiesAroundPoint(event.getNPC().getEntity().getLocation(), 30.0D));
        List<Entity> nearPlayers2 = new ArrayList<>();
        nearPlayers.forEach(playerName -> {
            nearPlayers2.remove(playerName);
            nearPlayers2.add(playerName);
        });
        nearPlayers2.forEach(playerName2 -> playerName2.sendMessage(this.deathmessage));

        event.getNPC().destroy();
    }

    public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
        List<Entity> entities = new ArrayList<>();
        World world = location.getWorld();
        int smallX = (int)Math.floor((location.getX() - radius) / 16.0D);
        int bigX = (int)Math.floor((location.getX() + radius) / 16.0D);
        int smallZ = (int)Math.floor((location.getZ() - radius) / 16.0D);
        int bigZ = (int)Math.floor((location.getZ() + radius) / 16.0D);
        for (int x = smallX; x <= bigX; x++) {
            for (int z = smallZ; z <= bigZ; z++) {
                if (world.isChunkLoaded(x, z))
                    entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities()));
            }
        }
        entities.removeIf(entity -> (entity.getLocation().distanceSquared(location) > radius * radius));
        return entities;
    }
}
