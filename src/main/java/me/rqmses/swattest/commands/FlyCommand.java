package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class FlyCommand implements CommandExecutor {

  public static final ArrayList<Player> flyingplayers = new ArrayList<>();

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
        if (player.getGameMode() == GameMode.SURVIVAL) {
          if (flyingplayers.contains(this.player)) {
            flyingplayers.remove(this.player);
            this.player.setAllowFlight(false);
            if (player.getPlayerListName().contains(ChatColor.translateAlternateColorCodes('&', "&b&l F"))) {
              player.setPlayerListName(player.getPlayerListName().substring(0, player.getPlayerListName().length() - 4));
            }
            this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bFlug-Modus ist nun &c&ldeaktiviert&b."));
          } else {
            flyingplayers.add(this.player);
            this.player.setAllowFlight(true);
            if (this.player.getPlayerListName().contains(ChatColor.translateAlternateColorCodes('&', "&b&l F"))) {
              this.player.setPlayerListName(this.player.getPlayerListName().substring(0, this.player.getPlayerListName().length() - 4));
            }
            this.player.setPlayerListName(this.player.getPlayerListName() + ChatColor.translateAlternateColorCodes('&', "&b&l F"));
            this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bFlug-Modus ist nun &a&laktiviert&b."));
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
