package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class LeitfadenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                Player player = (Player) sender;
                player.chat("/news");
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }
}
