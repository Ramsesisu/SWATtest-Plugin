package me.rqmses.swattest.commands;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
  private final ArrayList<Player> flyingplayers = new ArrayList<>();
  
  Player player;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (args.length == 1) {
        if (sender.isOp())
          this.player = Bukkit.getPlayer(args[0]); 
      } else {
        this.player = (Player)sender;
      } 
      if (this.flyingplayers.contains(this.player)) {
        this.flyingplayers.remove(this.player);
        this.player.setAllowFlight(false);
        this.player.setPlayerListName(this.player.getPlayerListName().substring(0, this.player.getPlayerListName().length() - 4));
        this.player.sendMessage(ChatColor.AQUA + "Flug-Modus deaktiviert!");
      } else {
        this.flyingplayers.add(this.player);
        this.player.setAllowFlight(true);
        this.player.setPlayerListName(this.player.getPlayerListName() + ChatColor.translateAlternateColorCodes('&', "&b&l F"));
        this.player.sendMessage(ChatColor.AQUA + "Flug-Modus aktiviert!");
      } 
    } 
    return true;
  }
}
