package me.rqmses.swattest.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rqmses.swattest.commands.NaviCommand.navitype;

public class LuftlinieCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        Player player = (Player) sender;
        navitype.putIfAbsent(player.getName(), Boolean.FALSE);
        navitype.put(player.getName(), !navitype.get(player.getName()));
        if (navitype.get(player.getName())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eModus Luftlinie ist nun &a&laktiviert&e."));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eModus Luftlinie ist nun &c&ldeaktiviert&e."));
        }
        return true;
    }
}
