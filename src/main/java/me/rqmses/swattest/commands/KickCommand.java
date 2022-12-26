package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class KickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                if (Admins.isAdmin(((Player) sender).getPlayer())) {
                    if (Bukkit.getServer().getPlayer(args[0]) != null) {
                        String reason;
                        if (args.length < 2) {
                            reason = "Kein Grund angegeben.";
                            Bukkit.broadcastMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + Bukkit.getServer().getPlayer(args[0]).getName() + ChatColor.RED + " wurde von " + ChatColor.DARK_RED + sender.getName() + ChatColor.RED + ChatColor.BOLD + " gekickt" + ChatColor.RED + ".");
                        } else {
                            reason = args[1];
                            Bukkit.broadcastMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + Bukkit.getServer().getPlayer(args[0]).getName() + ChatColor.RED + " wurde von " + ChatColor.DARK_RED + sender.getName() + ChatColor.RED + ChatColor.BOLD + " gekickt" + ChatColor.RED + "." + ChatColor.GRAY + " Grund: " + reason);
                        }
                        Bukkit.getServer().getPlayer(args[0]).kickPlayer(reason);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " wurde nicht gefunden!");
                    }
                } else {
                    sender.sendMessage("Du bist kein Admin!");
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }
}
