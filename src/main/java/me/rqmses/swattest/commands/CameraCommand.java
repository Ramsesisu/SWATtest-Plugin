package me.rqmses.swattest.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CameraCommand implements CommandExecutor {
  Player player;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (args.length == 1) {
        if (sender.isOp())
          this.player = Bukkit.getPlayer(args[0]); 
      } else {
        this.player = (Player)sender;
      } 
      if (!Objects.equals(this.player.getCustomName(), "dead"))
        if (this.player.getGameMode() == GameMode.SPECTATOR) {
          this.player.setGameMode(GameMode.SURVIVAL);
        } else {
          this.player.setGameMode(GameMode.SPECTATOR);
        }  
    } 
    return true;
  }
}
