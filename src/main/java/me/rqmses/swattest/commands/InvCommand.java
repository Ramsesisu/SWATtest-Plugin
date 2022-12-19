package me.rqmses.swattest.commands;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

import static me.rqmses.swattest.commands.TrainingsbotCommand.NPCList;

public class InvCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (sender.isOp()) {
                if (args.length >= 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player player = Bukkit.getPlayer(args[0]);
                        ((Player) sender).openInventory(player.getInventory());
                        sender.sendMessage(ChatColor.BLUE + "Du hast das Inventar von " + ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " durchsucht.");
                    } else if (getKI(args[0]) != null) {
                        ((Player) sender).openInventory(((Player) getKI(args[0]).getEntity()).getInventory());
                        sender.sendMessage(ChatColor.BLUE + "Du hast das Inventar von " + ChatColor.DARK_BLUE + getKI(args[0]).getName() + ChatColor.BLUE + " durchsucht.");
                    } else {
                        sender.sendMessage(ChatColor.BLUE + "Der Spieler " + ChatColor.DARK_BLUE + args[0] + ChatColor.BLUE + " wurde nicht gefunden!");
                    }
                } else {
                    sender.sendMessage(ChatColor.BLUE + "Du musst einen Spielernamen angeben!");
                }
            } else {
                sender.sendMessage("Du bist kein OP!");
            }
        }
        return true;
    }

    public NPC getKI(String name) {
        for (NPC npc : NPCList) {
            if (Objects.equals(npc.getName(), name)) {
                return npc;
            }
        }
        return null;
    }
}
