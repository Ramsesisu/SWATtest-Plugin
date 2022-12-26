package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.Functions;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rqmses.swattest.SWATtest.commandtoggles;
import static me.rqmses.swattest.SWATtest.plugin;

public class AblehnenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                Functions.accepttask.putIfAbsent(sender.getName(), null);
                if (Functions.accepttask.get(sender.getName()) == null) {
                    sender.sendMessage(ChatColor.GRAY + "Es steht gerade keine Anfrage offen!");
                    return true;
                }
                Functions.choice.put(sender.getName(), Boolean.FALSE);
                Functions.accepttask.get(sender.getName()).runTask(plugin);
                Functions.accepttask.put(sender.getName(), null);
                sender.sendMessage(ChatColor.GRAY + "Du hast die Anfrage " + ChatColor.RED + ChatColor.BOLD + "abgelehnt" + ChatColor.GRAY + ".");
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }

}
