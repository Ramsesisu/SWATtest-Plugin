package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class InvCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                if (sender.isOp()) {
                    if (args.length >= 1) {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            Player player = Bukkit.getPlayer(args[0]);
                            showInventory((Player) sender, player);
                            sender.sendMessage(ChatColor.BLUE + "Du hast das Inventar von " + ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " durchsucht.");
                            Admins.msgAdmin(ChatColor.DARK_RED + sender.getName() + ChatColor.RED + " benutzt " + ChatColor.DARK_RED + "/inv " + args[0] + ChatColor.RED + ".");
                        } else if (Functions.getKI(args[0]) != null) {
                            showInventory((Player) sender, (Player) Objects.requireNonNull(Functions.getKI(args[0])).getEntity());
                            sender.sendMessage(ChatColor.BLUE + "Du hast das Inventar von " + ChatColor.DARK_BLUE + Objects.requireNonNull(Functions.getKI(args[0])).getName() + ChatColor.BLUE + " durchsucht.");
                            Admins.msgAdmin(ChatColor.DARK_RED + sender.getName() + ChatColor.RED + " benutzt " + ChatColor.DARK_RED + "/inv " + args[0] + ChatColor.RED + ".");
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
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }

    public void showInventory(Player sender, Player player) {
        Inventory gui = Bukkit.createInventory(sender, 45, player.getName());
        for (int slot = 0; slot <= 40; slot++) {
            ItemStack item = player.getInventory().getItem(slot);
            if (item == null) { item = new ItemStack(Material.AIR); }
            gui.setItem(slot, item);
        }
        sender.openInventory(gui);
    }
}
