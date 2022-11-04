package me.rqmses.swattest.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    Player player;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            if (args.length == 1) {
                if (sender.isOp()) {
                    player = Bukkit.getPlayer(args[0]);
                }
            } else {
                player = (Player) sender;
            }
            player.setBedSpawnLocation(player.getLocation().add(0,1,0), true);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Du hast deinen Spawnpunkt auf " + ChatColor.DARK_PURPLE + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ() + ChatColor.LIGHT_PURPLE + " gesetzt!");
        }
        return true;
    }
}
