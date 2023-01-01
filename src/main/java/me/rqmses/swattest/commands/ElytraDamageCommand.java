package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class ElytraDamageCommand implements CommandExecutor {

    public static final HashMap<String, Boolean> elytradamage = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                Player player = ((Player) sender).getPlayer();
                elytradamage.putIfAbsent(player.getName(), Boolean.FALSE);

                if (elytradamage.get(player.getName())) {
                    elytradamage.put(player.getName(), Boolean.FALSE);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Elytra-Schaden ist nun &c&ldeaktiviert&3."));
                } else {
                    elytradamage.put(player.getName(), Boolean.TRUE);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Elytra-Schaden ist nun &a&laktiviert&3."));
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }
}
