package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.Functions;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.rqmses.swattest.SWATtest.commandtoggles;
import static me.rqmses.swattest.commands.TrainingsbotCommand.NPCList;

public class KillCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                if (args.length < 1) {
                    sender.sendMessage(ChatColor.RED + "Du musst einen Spieler oder Bot angeben!");
                } else {
                    if (Bukkit.getServer().getPlayer(args[0]) != null) {
                        if (sender.isOp()) {
                            Player player = Bukkit.getServer().getPlayer(args[0]);
                            sender.sendMessage(ChatColor.DARK_RED + player.getName() + ChatColor.RED + " wurde getötet.");
                            Admins.msgAdmin(ChatColor.DARK_RED + sender.getName() + ChatColor.RED + " benutzt " + ChatColor.DARK_RED + "/kill " + args[0] + ChatColor.RED + ".");
                            player.setHealth(0);
                        } else {
                            sender.sendMessage("Du bist kein OP!");
                        }
                    } else if (Functions.getKI(args[0]) != null) {
                        NPC npc = Functions.getKI(args[0]);
                        sender.sendMessage(ChatColor.DARK_RED + Objects.requireNonNull(npc).getName() + ChatColor.RED + " wurde getötet.");
                        Admins.msgAdmin(ChatColor.DARK_RED + sender.getName() + ChatColor.RED + " benutzt " + ChatColor.DARK_RED + "/kill " + args[0] + ChatColor.RED + ".");
                        ((Player) Objects.requireNonNull(npc).getEntity()).setHealth(0);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Der Name " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " wurde nicht gefunden!");
                    }
                }
            } else {
                if (args.length > 0) {
                    if (Bukkit.getServer().getPlayer(args[0]) != null) {
                        Bukkit.getServer().getPlayer(args[0]).setHealth(0);
                    }
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> targets = new ArrayList<>();
        if (sender.isOp()) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                targets.add(player.getName());
            }
        }
        for (NPC npc : NPCList) {
            targets.add(npc.getName());
        }
        if (args.length == 1)
            for (String target : targets) {
                if (target.toUpperCase().startsWith(args[0].toUpperCase()))
                    list.add(target);
            }
        return list;
    }
}
