package me.rqmses.swattest.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

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
            if (player.getGameMode() == GameMode.SURVIVAL) {
                player.setHealth(40);
                player.sendMessage(ChatColor.GREEN + "Du wurdest geheilt!");

                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(1, 1, 1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(1, 1, 0), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(1, 1, -1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(0, 1, 1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(0, 1, -1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(-1, 1, -1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(-1, 1, 1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(-1, 1, 0), 1);

                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(1, 2, 1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(1, 2, 0), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(1, 2, -1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(0, 2, 1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(0, 2, -1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(-1, 2, -1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(-1, 2, 1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(-1, 2, 0), 1);

                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(1, 0, 1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(1, 0, 0), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(1, 0, -1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(0, 0, 1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(0, 0, -1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(-1, 0, -1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(-1, 0, 1), 1);
                Bukkit.getServer().getWorld(player.getWorld().getName()).spawnParticle(Particle.HEART, player.getLocation().add(-1, 0, 0), 1);
            }
        }
        return true;
    }
}