package me.rqmses.swattest.commands;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.listeners.PlayerDeathListener;
import me.rqmses.swattest.listeners.ProjectileHitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SprengguertelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        int countdown = 0;
        if (args.length == 1) {
            countdown = Integer.parseInt(args[0]);
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (PlayerDeathListener.spawnprotection.get(player.getUniqueId())) {
                PlayerDeathListener.spawnprotection.put(player.getUniqueId(), Boolean.FALSE);
                player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
            }
            if (player.getInventory().getChestplate().getItemMeta().getDisplayName().contains("Sprenggürtel")) {
                Bukkit.getScheduler().runTaskLater(SWATtest.plugin, () -> {
                        if (player.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE) {
                            ProjectileHitListener.exlode(player.getLocation(), player);
                            player.sendMessage(ChatColor.YELLOW + "Dein Sprenggürtel ist explodiert!");
                            player.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
                        }
                    }, countdown * 20L);
            }
        }
        return true;
    }
}
