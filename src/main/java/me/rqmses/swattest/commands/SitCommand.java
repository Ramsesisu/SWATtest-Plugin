package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.Functions;
import me.rqmses.swattest.global.Items;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class SitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                Player player = ((Player) sender).getPlayer();
                Location loc = player.getLocation();
                if (Items.getSlabs().contains(loc.getBlock().getType()) || Items.getSlabs().contains(loc.getBlock().getRelative(BlockFace.DOWN).getType()) || loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
                    player.sendMessage("Du kannst dich hier nicht hinsetzen.");
                } else {
                    if (loc.getBlock().getType() == Material.AIR) {
                        Functions.sitPlayer(player, loc.add(0, -1.75, 0), loc.getDirection());
                    }
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }
}
