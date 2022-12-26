package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class SpawnCommand implements CommandExecutor {
  Player player;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
        if (args.length == 1) {
          if (sender.isOp())
            this.player = Bukkit.getPlayer(args[0]);
        } else {
          this.player = (Player) sender;
        }
        this.player.setBedSpawnLocation(this.player.getLocation().add(0.0D, 1.0D, 0.0D), true);
        this.player.sendMessage(ChatColor.LIGHT_PURPLE + "Du hast deinen Spawnpunkt auf " + ChatColor.DARK_PURPLE + this.player.getLocation().getBlockX() + ", " + this.player.getLocation().getBlockY() + ", " + this.player.getLocation().getBlockZ() + ChatColor.LIGHT_PURPLE + " gesetzt!");
      }
    }
    if (!commandtoggles.get(command.getName())) {
      sender.sendMessage("Dieser Befehl ist deaktiviert!");
    }
    return true;
  }
}