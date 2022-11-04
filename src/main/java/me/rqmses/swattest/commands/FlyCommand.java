package me.rqmses.swattest.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FlyCommand implements CommandExecutor {

    private final ArrayList<Player> list_of_flying_players = new ArrayList<>();

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
            if (list_of_flying_players.contains(player)) {
                list_of_flying_players.remove(player);
                player.setAllowFlight(false);
                player.setPlayerListName(player.getPlayerListName().substring(0, player.getPlayerListName().length()-4));
                player.sendMessage(ChatColor.AQUA + "Flug-Modus deaktiviert!");
            } else if (!list_of_flying_players.contains(player)) {
                list_of_flying_players.add(player);
                player.setAllowFlight(true);
                player.setPlayerListName(player.getPlayerListName() + ChatColor.translateAlternateColorCodes('&', "&b&l F"));
                player.sendMessage(ChatColor.AQUA + "Flug-Modus aktiviert!");
            }
        }

        return true;
    }
}
