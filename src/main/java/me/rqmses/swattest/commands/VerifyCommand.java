package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static me.rqmses.swattest.SWATtest.*;

public class VerifyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                if (args.length >= 1 && Admins.isAdmin((Player) sender)) {
                    if (verified.contains(args[0])) {
                        verified.remove(args[0]);
                        player.sendMessage(ChatColor.DARK_AQUA + args[0] + ChatColor.AQUA + " ist nun nicht mehr verifiziert.");
                    } else {
                        verified.add(args[0]);
                        player.sendMessage(ChatColor.DARK_AQUA + args[0] + ChatColor.AQUA + " ist nun verifiziert.");
                        if (Bukkit.getServer().getPlayer(args[0]) != null) {
                            Bukkit.getServer().getPlayer(args[0]).sendMessage(ChatColor.AQUA + "Du wurdest von " + ChatColor.DARK_AQUA + player.getName() + ChatColor.AQUA + " verifiziert!");
                        }
                    }
                    verifiedconfig.set("verified", verified);
                    try {
                        verifiedconfig.save(verifiedsave);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Admins.msgAdmin(ChatColor.DARK_RED + sender.getName() + ChatColor.RED + " benutzt " + ChatColor.DARK_RED + "/verify " + args[0] + ChatColor.RED + ".");
                } else {
                    if (verified.contains(player.getName())) {
                     player.sendMessage(ChatColor.DARK_AQUA + "Du bist verifiziert.");
                    } else {
                        player.sendMessage(ChatColor.DARK_AQUA + "Du bist noch nicht verifiziert, melde dich bei einem Admin!");
                        player.spigot().sendMessage(TextUtils.getCustomClickable(ChatColor.DARK_RED, net.md_5.bungee.api.ChatColor.GRAY + "» " + net.md_5.bungee.api.ChatColor.DARK_RED + "Admins", "/admins"));
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
