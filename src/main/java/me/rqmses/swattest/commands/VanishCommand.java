package me.rqmses.swattest.commands;

import de.myzelyam.api.vanish.VanishAPI;
import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VanishCommand implements CommandExecutor {

    public static ArrayList<Player> hidden = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (hidden.contains(player)) {
                hidden.remove(player);
                VanishAPI.showPlayer(player);
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " ist nun" + ChatColor.GREEN + " online" + ChatColor.YELLOW + ".");
            } else {
                hidden.add(player);
                VanishAPI.hidePlayer(player);
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " ist nun" + ChatColor.RED + " offline" + ChatColor.YELLOW + ".");
            }
            Admins.msgAdmin(ChatColor.DARK_RED + player.getName() + ChatColor.RED + " benutzt " + ChatColor.DARK_RED + "/vanish" + ChatColor.RED + ".");
        }
        return true;
    }
}
