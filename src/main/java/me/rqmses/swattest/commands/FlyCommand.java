package me.rqmses.swattest.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FlyCommand implements CommandExecutor {

    private final ArrayList<Player> flyingplayers = new ArrayList<>();

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
            if (flyingplayers.contains(player)) {
                flyingplayers.remove(player);
                player.setAllowFlight(false);
                player.setPlayerListName(player.getPlayerListName().substring(0, player.getPlayerListName().length()-4));
                player.sendMessage(ChatColor.AQUA + "Flug-Modus deaktiviert!");
            } else if (!flyingplayers.contains(player)) {
                flyingplayers.add(player);
                player.setAllowFlight(true);
                player.setPlayerListName(player.getPlayerListName() + ChatColor.translateAlternateColorCodes('&', "&b&l F"));
                player.sendMessage(ChatColor.AQUA + "Flug-Modus aktiviert!");
            }
        }

        return true;
    }
}
