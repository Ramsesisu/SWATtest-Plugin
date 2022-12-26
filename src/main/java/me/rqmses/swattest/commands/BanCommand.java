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

public class BanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (Admins.isAdmin(((Player) sender).getPlayer())) {
                if (Bukkit.getServer().getPlayer(args[0]) != null) {
                    if (!banned.contains(args[0])) {
                        String reason;
                        if (args.length < 2) {
                            reason = "Kein Grund angegeben.";
                            Bukkit.broadcastMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + Bukkit.getServer().getPlayer(args[0]).getName() + ChatColor.RED + " wurde von " + ChatColor.DARK_RED + sender.getName() + ChatColor.RED + ChatColor.BOLD + " gebannt" + ChatColor.RED + ".");
                        } else {
                            reason = args[1];
                            Bukkit.broadcastMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + Bukkit.getServer().getPlayer(args[0]).getName() + ChatColor.RED + " wurde von " + ChatColor.DARK_RED + sender.getName() + ChatColor.RED + ChatColor.BOLD + " gebannt" + ChatColor.RED + "." + ChatColor.GRAY + " Grund: " + reason);
                        }
                        banned.add(args[0]);
                        bannedconfig.set("banned", banned);
                        try {
                            bannedconfig.save(bannedsave);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Bukkit.getServer().getPlayer(args[0]).kickPlayer(reason);
                    } else {
                        Admins.msgAdmin(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + Bukkit.getServer().getPlayer(args[0]).getName() + ChatColor.RED + " wurde von " + ChatColor.DARK_RED + sender.getName() + ChatColor.RED + " entbannt.");
                        banned.remove(args[0]);
                        bannedconfig.set("banned", banned);
                        try {
                            bannedconfig.save(bannedsave);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " wurde nicht gefunden!");
                }
            } else {
                sender.sendMessage("Du bist kein Admin!");
            }
        }
        return true;
    }
}
