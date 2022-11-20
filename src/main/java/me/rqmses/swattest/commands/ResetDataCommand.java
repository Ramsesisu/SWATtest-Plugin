package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Functions;
import me.rqmses.swattest.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetDataCommand implements CommandExecutor {
  Player player;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (args.length == 1) {
        if (sender.isOp())
          this.player = Bukkit.getPlayer(args[0]); 
      } else {
        this.player = (Player)sender;
      }
      //noinspection ResultOfMethodCallIgnored
      PlayerJoinListener.playersafe.get(this.player.getUniqueId()).delete();
      Functions.createFile(this.player);
      this.player.sendMessage(ChatColor.GRAY + "Dein Speicherstand wurde zurückgesetzt!");
    } 
    return true;
  }
}
