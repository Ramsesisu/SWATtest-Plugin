package me.rqmses.swattest.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BombeCommand implements CommandExecutor {
  public static boolean bomb = false;
  
  public static Location bombloc;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player = (Player)sender;
    if (!player.isOp())
      return true; 
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
    return true;
  }
}
