package me.rqmses.swattest.listeners;

import me.rqmses.swattest.SWATtest;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCSpawnListener implements Listener {

    @EventHandler
    public void onNPCSpawn(NPCSpawnEvent event) {
        NPC npc = event.getNPC();
        Location finalLoc=new Location(npc.getEntity().getWorld(), 103, 69, 157);
        npc.setProtected(false);
        npc.faceLocation(finalLoc);

        Bukkit.getScheduler().runTaskLater(SWATtest.plugin, new Runnable() {
            @Override
            public void run() {
                npc.getNavigator().setTarget(finalLoc);
            }
        }, 20L);
    }
}
