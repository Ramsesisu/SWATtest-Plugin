package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.WarpPoints;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class WarpCommand implements CommandExecutor, TabCompleter {

    Player player;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                if (args.length == 2) {
                    if (sender.isOp()) {
                        player = Bukkit.getPlayer(args[1]);
                    }
                } else {
                    player = (Player) sender;
                }
                if (args.length == 0) {
                    player.sendMessage(ChatColor.YELLOW + "Du musst ein Ziel angeben!");
                } else {
                    Location loc = WarpPoints.getWarp(args[0], player, "warp");
                    if (loc == null) {
                        return true;
                    }
                    player.teleport(loc);
                    player.sendMessage(ChatColor.YELLOW + "Du wurdest zu " + ChatColor.GOLD + args[0] + ChatColor.YELLOW + " teleportiert!");
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
        String[] targets = WarpPoints.getTargets((Player) sender);
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