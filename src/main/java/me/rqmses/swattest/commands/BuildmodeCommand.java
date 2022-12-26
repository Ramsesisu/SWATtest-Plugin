package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;

import static me.rqmses.swattest.SWATtest.commandtoggles;
import static me.rqmses.swattest.SWATtest.plugin;
import static me.rqmses.swattest.commands.FlyCommand.flyingplayers;

public class BuildmodeCommand implements CommandExecutor {

    public static final ArrayList<Player> buildmode = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                Player player = (Player) sender;
                if (Admins.isBuilder(player)) {
                    if (buildmode.contains(player)) {
                        buildmode.remove(player);
                        PermissionAttachment pperms = player.addAttachment(plugin);
                        pperms.unsetPermission("worldedit.*");
                        pperms.unsetPermission("headdb.open");
                        pperms.unsetPermission("headdb.phead");
                        if (player.getPlayerListName().contains(ChatColor.translateAlternateColorCodes('&', "&a&l B"))) {
                            player.setPlayerListName(player.getPlayerListName().substring(0, player.getPlayerListName().length() - 4));
                        }
                        player.getInventory().clear();
                        if (player.isFlying()) {
                            player.setGameMode(GameMode.SURVIVAL);
                            player.chat("/fly");
                        } else {
                            player.setGameMode(GameMode.SURVIVAL);
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Build-Modus ist nun &c&ldeaktiviert&2."));
                    } else {
                        EquipCommand.cooldowns.put(player.getName(), 0L);
                        player.chat("/equip None " + player.getName() + " f");
                        buildmode.add(player);
                        PermissionAttachment pperms = player.addAttachment(plugin);
                        pperms.setPermission("worldedit.*", true);
                        pperms.setPermission("headdb.open", true);
                        pperms.setPermission("headdb.phead", true);
                        flyingplayers.remove(player);
                        if (player.getPlayerListName().contains(ChatColor.translateAlternateColorCodes('&', "&b&l F"))) {
                            player.setPlayerListName(player.getPlayerListName().substring(0, player.getPlayerListName().length() - 4));
                        }
                        player.setPlayerListName(player.getPlayerListName() + ChatColor.translateAlternateColorCodes('&', "&a&l B"));
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Build-Modus ist nun &a&laktiviert&2."));
                    }
                } else {
                    sender.sendMessage("Du bist kein Builder!");
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }
}
