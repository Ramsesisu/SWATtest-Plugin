package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static me.rqmses.swattest.SWATtest.*;

public class BanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                if (Admins.isAdmin(((Player) sender).getPlayer())) {
                    if (args.length == 0) {
                        sender.sendMessage(ChatColor.DARK_GRAY + "Gebannte Spieler:");
                        for (Object name : bannedconfig.getList("banned")) {
                            String[] strings = ((String) name).split("~");
                            sender.sendMessage(ChatColor.DARK_RED + strings[0] + ChatColor.GRAY + " ➝ Grund: " + ChatColor.RED + strings[1] + ChatColor.DARK_GRAY + " (" + strings[2] + ")");
                        }
                        return true;
                    }
                    if (!banned.containsKey(args[0])) {
                        if (Bukkit.getServer().getPlayer(args[0]) != null) {
                            StringBuilder reason;
                            if (args.length < 2) {
                                reason = new StringBuilder("Kein Grund angegeben.");
                                Bukkit.broadcastMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + Bukkit.getServer().getPlayer(args[0]).getName() + ChatColor.RED + " wurde von " + ChatColor.DARK_RED + sender.getName() + ChatColor.RED + ChatColor.BOLD + " gebannt" + ChatColor.RED + ".");
                            } else {
                                reason = Optional.ofNullable(args[1]).map(StringBuilder::new).orElse(null);
                                for (int i = 2; i < args.length; i++) {
                                    reason = (reason == null ? new StringBuilder("null") : reason).append(" ").append(args[i]);
                                }
                                Bukkit.broadcastMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + Bukkit.getServer().getPlayer(args[0]).getName() + ChatColor.RED + " wurde von " + ChatColor.DARK_RED + sender.getName() + ChatColor.RED + ChatColor.BOLD + " gebannt" + ChatColor.RED + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + " Grund: " + reason);
                            }
                            banned.put(args[0], String.valueOf(reason));
                            bannedlist.add(args[0] + "~" + reason + "~" + sender.getName());
                            bannedconfig.set("banned", bannedlist);
                            try {
                                bannedconfig.save(bannedsave);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Bukkit.getServer().getPlayer(args[0]).kickPlayer(Objects.requireNonNull(reason).toString());
                        } else {
                            sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " wurde nicht gefunden!");
                        }
                    } else {
                        Admins.msgAdmin(ChatColor.RED + "Der Spieler " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " wurde von " + ChatColor.DARK_RED + sender.getName() + ChatColor.RED + " entbannt.");
                        banned.remove(args[0]);
                        bannedlist.removeIf(string -> ((String) string).startsWith(args[0]));
                        bannedconfig.set("banned", bannedlist);
                        try {
                            bannedconfig.save(bannedsave);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
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
