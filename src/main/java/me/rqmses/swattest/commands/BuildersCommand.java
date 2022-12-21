package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static me.rqmses.swattest.SWATtest.*;

public class BuildersCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1 && Admins.isAdmin((Player) sender)) {
                if (builders.contains(args[0])) {
                    builders.remove(args[0]);
                    builderconfig.set("builders", builders);
                    Bukkit.broadcastMessage(ChatColor.DARK_GREEN + args[0] + ChatColor.GREEN + " ist nun kein Builder mehr!");
                    try {
                        builderconfig.save(buildersave);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    builders.add(args[0]);
                    builderconfig.set("builders", builders);
                    Bukkit.broadcastMessage(ChatColor.DARK_GREEN + args[0] + ChatColor.GREEN + " wurde zum Builder ernannt!");
                    try {
                        builderconfig.save(buildersave);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                sender.sendMessage(ChatColor.DARK_GREEN + "Builders:");
                for (Object name : builders) {
                    sender.sendMessage(ChatColor.GREEN + (String) name);
                }
            }
        }
        return true;
    }
}
