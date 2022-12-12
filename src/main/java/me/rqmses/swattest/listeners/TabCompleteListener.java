package me.rqmses.swattest.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.List;

public class TabCompleteListener implements Listener {

    @EventHandler
    public static void onTab(TabCompleteEvent event) {
        List<String> completions = event.getCompletions();
        completions.remove("/news");
        completions.remove("/booknews:news");
        completions.remove("/booknews");
        completions.remove("/booknews:booknews");
        event.setCompletions(completions);
    }
}
