package me.rqmses.swattest.commands;

import de.myzelyam.api.vanish.VanishAPI;
import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class VanishCommand implements CommandExecutor {

    public static final ArrayList<Player> hidden = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                Player player = (Player) sender;
                if (Admins.isVerified(player)) {
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
                } else {
                    player.sendMessage(ChatColor.DARK_AQUA + "Du musst erst verifiziert werden, bevor du dich verstecken kannst!");
                    player.spigot().sendMessage(TextUtils.getCustomClickable(ChatColor.DARK_RED, net.md_5.bungee.api.ChatColor.GRAY + "» " + net.md_5.bungee.api.ChatColor.DARK_RED + "Admins", "/admins"));
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }
}
