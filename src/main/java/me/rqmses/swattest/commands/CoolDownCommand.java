package me.rqmses.swattest.commands;

import me.rqmses.swattest.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoolDownCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.isOp()) {
                if (args.length != 0) {
                    player = Bukkit.getPlayer(args[0]);
                }
                PlayerInteractListener.cooldowntimes.put(player.getName(), 0);
                PlayerInteractListener.tazerstatus.put(player.getName(), 10);
                UseCommand.cooldowns.put(player.getName(), 0L);
                EquipCommand.cooldowns.put(player.getName(), 0L);
                player.sendMessage(ChatColor.GRAY + "Deine Cooldowns wurden zurückgesetzt!");
            }
        }
        return true;
    }
}
