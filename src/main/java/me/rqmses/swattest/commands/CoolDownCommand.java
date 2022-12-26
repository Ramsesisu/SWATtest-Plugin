package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.listeners.ItemDropListener;
import me.rqmses.swattest.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class CoolDownCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
        Player player = (Player) sender;
        if (player.isOp()) {
          if (args.length != 0)
            player = Bukkit.getPlayer(args[0]);
          PlayerInteractListener.cooldowntimes.put(player.getUniqueId(), 0);
          PlayerInteractListener.cooldowns.put(player.getUniqueId(), 0L);
          PlayerInteractListener.tazerstatus.put(player.getName(), 10);
          PlayerInteractListener.rpgcooldown.put(player.getName(), Boolean.TRUE);
          UseCommand.cooldowns.put(player.getUniqueId(), 0L);
          EquipCommand.cooldowns.put(player.getName(), 0L);
          TeamCommand.cooldowns.put(player.getName(), 0L);
          ItemDropListener.cooldowns.put(player.getName(), 0L);
          ItemDropListener.cooldowntimes.put(player.getName(), 0);
          player.sendMessage(ChatColor.GRAY + "Deine Cooldowns wurden zurückgesetzt!");
        } else {
          player.sendMessage("Du bist kein OP!");
        }
      }
    }
    if (!commandtoggles.get(command.getName())) {
      sender.sendMessage("Dieser Befehl ist deaktiviert!");
    }
    return true;
  }
}
