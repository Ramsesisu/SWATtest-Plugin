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

public class AdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1 && Admins.isAdmin((Player) sender)) {
                admins.add(args[0]);
                adminconfig.set("admins", admins);
                Bukkit.broadcastMessage(ChatColor.DARK_RED + args[0] + ChatColor.RED + " wurde zum Admin ernannt!");
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
        return true;
    }
}
