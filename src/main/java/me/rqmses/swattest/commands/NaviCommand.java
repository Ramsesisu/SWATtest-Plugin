package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.WarpPoints;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.rqmses.swattest.SWATtest.commandtoggles;
import static me.rqmses.swattest.SWATtest.plugin;

public class NaviCommand implements CommandExecutor, TabCompleter {

    public static final HashMap<String, BukkitTask> navitask = new HashMap<>();

    public static final HashMap<String, Boolean> navitype = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    if (navitask.get(player.getName()) == null) {
                        player.sendMessage(ChatColor.YELLOW + "Du musst ein Ziel angeben!");
                    } else {
                        navitask.get(player.getName()).cancel();
                        player.sendMessage(ChatColor.YELLOW + "Du hast dein Navi gelöscht!");
                        navitask.put(player.getName(), null);
                    }
                } else {
                    navitype.putIfAbsent(player.getName(), Boolean.FALSE);
                    Location loc = WarpPoints.getWarp(args[0], player);
                    if (loc == null) {
                        return true;
                    }
                    BukkitRunnable navi = new BukkitRunnable() {
                        @Override
                        public void run() {
                            Location origin = player.getLocation().add(0, 1, 0);
                            Vector direction = loc.clone().subtract(player.getLocation()).toVector();

                            direction.normalize();
                            for (int i = 0; i < 20 * 2 /* 20 mal Länge des Navis */; i++) {
                                Location temploc = origin.add(direction);
                                player.spawnParticle(Particle.REDSTONE, temploc.subtract(direction.clone().multiply(0.75)), 1, 0.05, 0.05, 0.05, 0);
                            }

                            if (navitype.get(player.getName())) {
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&6Noch &l" + ((int) Math.floor(Math.sqrt(Math.pow(player.getLocation().getX() - loc.getX(), 2) + Math.pow(player.getLocation().getZ() - loc.getZ(), 2)))) + "m&6 bis zum Ziel.")));
                            } else {
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&6Noch &l" + ((int) Math.floor(Math.sqrt(Math.pow(player.getLocation().getX() - loc.getX(), 2) + Math.pow(player.getLocation().getY() - loc.getY(), 2) + Math.pow(player.getLocation().getZ() - loc.getZ(), 2)))) + "m&6 bis zum Ziel.")));
                            }
                        }
                    };
                    player.sendMessage(ChatColor.YELLOW + "Dir wird nun ein Navi zu " + ChatColor.GOLD + args[0] + ChatColor.YELLOW + " angezeigt!");

                    if (navitask.get(player.getName()) != null) {
                        navitask.get(player.getName()).cancel();
                        navitask.put(player.getName(), null);
                    }

                    navitask.put(player.getName(), navi.runTaskTimer(plugin, 0, 5L));
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String[] targets = WarpPoints.getTargets();
        if (args.length == 1) {
            for (String target : targets) {
                if (target.toUpperCase().startsWith(args[0].toUpperCase())) {
                    list.add(target);
                }
            }
        }
        return list;
    }
}