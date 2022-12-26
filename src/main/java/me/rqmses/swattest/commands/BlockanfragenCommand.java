package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class BlockanfragenCommand implements CommandExecutor {

    public static HashMap<String, ArrayList<String>> blockedplayers = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                blockedplayers.putIfAbsent(sender.getName(), new ArrayList<>());
                if (args.length > 0) {
                    if (blockedplayers.get(sender.getName()).contains(args[0].toLowerCase())) {
                        blockedplayers.get(sender.getName()).remove(args[0].toLowerCase());
                        sender.sendMessage(ChatColor.GRAY + "Die Anfragen von " + ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " wurden wieder freigegeben.");
                    } else {
                        blockedplayers.get(sender.getName()).add(args[0].toLowerCase());
                        sender.sendMessage(ChatColor.GRAY + "Die Anfragen von " + ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " wurden blockiert.");
                    }
                } else {
                    sender.sendMessage(ChatColor.GRAY + "Du musst einen Spieler angeben!");
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }
}
