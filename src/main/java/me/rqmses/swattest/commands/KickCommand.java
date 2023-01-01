package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Optional;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class KickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                if (Admins.isAdmin(((Player) sender).getPlayer())) {
                    if (args.length == 0) {
                        sender.sendMessage(ChatColor.RED + "Du musst einen Spieler angeben");
                        return true;
                    }
                    if (Bukkit.getServer().getPlayer(args[0]) != null) {
                        StringBuilder reason;
                        if (args.length < 2) {
                            reason = new StringBuilder("Kein Grund angegeben.");
                            Bukkit.broadcastMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + Bukkit.getServer().getPlayer(args[0]).getName() + ChatColor.RED + " wurde von " + ChatColor.DARK_RED + sender.getName() + ChatColor.RED + ChatColor.BOLD + " gekickt" + ChatColor.RED + ".");
                        } else {
                            reason = Optional.ofNullable(args[1]).map(StringBuilder::new).orElse(null);
                            for (int i = 2; i < args.length; i++) {
                                reason = (reason == null ? new StringBuilder("null") : reason).append(" ").append(args[i]);
                            }
                            Bukkit.broadcastMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + Bukkit.getServer().getPlayer(args[0]).getName() + ChatColor.RED + " wurde von " + ChatColor.DARK_RED + sender.getName() + ChatColor.RED + ChatColor.BOLD + " gekickt" + ChatColor.RED + ".");
                            Bukkit.broadcastMessage(ChatColor.GRAY + " Grund: " + reason);
                        }
                        Bukkit.getServer().getPlayer(args[0]).kickPlayer(Objects.requireNonNull(reason).toString());
                    } else {
                        sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " wurde nicht gefunden!");
                    }
                } else {
                    sender.sendMessage("Du bist kein Admin!");
                }
            }
        } else {
            Bukkit.getServer().getPlayer(args[0]).kickPlayer(args[1]);
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }
}
