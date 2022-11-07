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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                if (sender.isOp()) {
                    player = Bukkit.getPlayer(args[0]);
                }
            } else {
                player = (Player) sender;
            }

            if (!(Objects.equals(player.getCustomName(), "dead"))) {
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    player.setGameMode(GameMode.SPECTATOR);
                } else if (player.getGameMode() == GameMode.SPECTATOR) {
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
        }
        return true;
    }
}
