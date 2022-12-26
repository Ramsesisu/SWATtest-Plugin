package me.rqmses.swattest.commands;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class DisableCommandCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                Player player = (Player) sender;
                if (Admins.isAdmin(player)) {
                    if (args.length > 0) {
                        if (commandtoggles.get(args[0])) {
                            commandtoggles.put(args[0], Boolean.FALSE);
                            Bukkit.broadcastMessage(ChatColor.RED + "Der Befehl " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " wurde deaktiviert!");
                        } else {
                            commandtoggles.put(args[0], Boolean.TRUE);
                            Bukkit.broadcastMessage(ChatColor.RED + "Der Befehl " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " wurde wieder aktiviert!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Du musst einen Command angeben!");
                    }
                } else {
                    player.sendMessage("Du bist kein Admin!");
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> targets = new ArrayList<>();
        for (Command tempcommand : SWATtest.commandlist) { targets.add(tempcommand.getName()); }
        if (args.length == 1)
            for (String target : targets) {
                if (target.toUpperCase().startsWith(args[0].toUpperCase()))
                    list.add(target);
            }
        return list;
    }
}
