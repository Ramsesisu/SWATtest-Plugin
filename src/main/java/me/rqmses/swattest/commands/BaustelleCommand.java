package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rqmses.swattest.SWATtest.commandtoggles;
import static me.rqmses.swattest.commands.BuildmodeCommand.buildmode;

public class BaustelleCommand implements CommandExecutor {
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
        Player player = ((Player) sender);

        Location loc = player.getLocation();

        if (player.getWorld().getName().equals("Baustelle")) {
          if (buildmode.contains(player)) {
            player.chat("/buildmode");
          }

          player.teleport(new Location(Bukkit.getWorld("Training"), loc.getX(), loc.getY(), loc.getZ()));
          player.sendMessage(ChatColor.GRAY + "Du hast die " + ChatColor.GOLD + "B" + ChatColor.DARK_GRAY + "a" + ChatColor.GOLD + "u" + ChatColor.DARK_GRAY + "s" + ChatColor.GOLD + "t" + ChatColor.DARK_GRAY + "e" + ChatColor.GOLD + "l" + ChatColor.DARK_GRAY + "l" + ChatColor.GOLD + "e" + ChatColor.GRAY + " verlassen.");
        } else {
          EquipCommand.cooldowns.put(player.getName(), 0L);
          player.chat("/equip None " + player.getName() + " f");
          EquipCommand.cooldowns.put(player.getName(), 0L);

          player.teleport(new Location(Bukkit.getWorld("Baustelle"), loc.getX(), loc.getY(), loc.getZ()));
          player.sendTitle(ChatColor.GOLD + "B" + ChatColor.DARK_GRAY + "a" + ChatColor.GOLD + "u" + ChatColor.DARK_GRAY + "s" + ChatColor.GOLD + "t" + ChatColor.DARK_GRAY + "e" + ChatColor.GOLD + "l" + ChatColor.DARK_GRAY + "l" + ChatColor.GOLD + "e", "", 10, 40, 10);
          player.sendMessage(ChatColor.GRAY + "Du hast die " + ChatColor.GOLD + "B" + ChatColor.DARK_GRAY + "a" + ChatColor.GOLD + "u" + ChatColor.DARK_GRAY + "s" + ChatColor.GOLD + "t" + ChatColor.DARK_GRAY + "e" + ChatColor.GOLD + "l" + ChatColor.DARK_GRAY + "l" + ChatColor.GOLD + "e" + ChatColor.GRAY + " betreten.");
        }
      }
    }
    if (!commandtoggles.get(command.getName())) {
      sender.sendMessage("Dieser Befehl ist deaktiviert!");
    }
    return true;
  }
}
