package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class BombeCommand implements CommandExecutor {
  public static boolean bomb = false;
  
  public static Location bombloc;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
        Player player = (Player) sender;
        Location loc = player.getLocation();
        if (bomb) {
          if (Bukkit.getWorld(player.getWorld().getName()).getBlockAt(bombloc).getType() == Material.TNT) {
            Bukkit.getWorld(player.getWorld().getName()).getBlockAt(bombloc).setType(Material.AIR);
            bomb = false;
            player.sendMessage(ChatColor.DARK_RED + "Du hast die Bombe bei " + ChatColor.GRAY + bombloc.getBlockX() + ", " + bombloc.getBlockY() + ", " + bombloc.getBlockZ() + ChatColor.DARK_RED + " entfernt.");
          }
        } else if (Bukkit.getWorld(player.getWorld().getName()).getBlockAt(loc).getType() == Material.AIR) {
          Bukkit.getWorld(player.getWorld().getName()).getBlockAt(loc).setType(Material.TNT);
          bombloc = loc;
          bomb = true;
          player.sendMessage(ChatColor.DARK_RED + "Du hast eine Bombe bei " + ChatColor.GRAY + bombloc.getBlockX() + ", " + bombloc.getBlockY() + ", " + bombloc.getBlockZ() + ChatColor.DARK_RED + " platziert.");
        }
      }
    }
    if (!commandtoggles.get(command.getName())) {
      sender.sendMessage("Dieser Befehl ist deaktiviert!");
    }
    return true;
  }
}
