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

public class AdminsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                if (args.length >= 1 && Admins.isAdmin((Player) sender)) {
                    if (admins.contains(args[0])) {
                        admins.remove(args[0]);
                        Bukkit.broadcastMessage(ChatColor.DARK_RED + args[0] + ChatColor.RED + " ist nun kein Admin mehr!");
                        if (Bukkit.getServer().getPlayer(args[0]) != null)
                            Bukkit.getServer().getPlayer(args[0]).setOp(false);
                    } else {
                        admins.add(args[0]);
                        Bukkit.broadcastMessage(ChatColor.DARK_RED + args[0] + ChatColor.RED + " wurde zum Admin ernannt!");
                        if (Bukkit.getServer().getPlayer(args[0]) != null)
                            Bukkit.getServer().getPlayer(args[0]).setOp(true);
                    }
                    adminconfig.set("admins", admins);
                    try {
                        adminconfig.save(adminsave);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "Admins:");
                    for (Object name : admins) {
                        sender.sendMessage(ChatColor.RED + (String) name);
                    }
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }
}
