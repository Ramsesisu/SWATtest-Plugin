package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

import static me.rqmses.swattest.SWATtest.commandtoggles;
import static me.rqmses.swattest.commands.BuildmodeCommand.buildmode;
import static me.rqmses.swattest.commands.FlyCommand.flyingplayers;

public class CameraCommand implements CommandExecutor {
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
        if (!Objects.equals(this.player.getCustomName(), "dead"))
          if (this.player.getGameMode() == GameMode.SPECTATOR) {
            if (buildmode.contains(this.player)) {
              this.player.setGameMode(GameMode.CREATIVE);
            } else {
              this.player.setGameMode(GameMode.SURVIVAL);
              if (flyingplayers.contains(this.player)) {
                this.player.setAllowFlight(true);
              }
            }
          } else {
            this.player.setGameMode(GameMode.SPECTATOR);
          }
      }
    }
    if (!commandtoggles.get(command.getName())) {
      sender.sendMessage("Dieser Befehl ist deaktiviert!");
    }
    return true;
  }
}
