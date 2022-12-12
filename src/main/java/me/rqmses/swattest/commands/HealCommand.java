package me.rqmses.swattest.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class HealCommand implements CommandExecutor {
  Player player;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (args.length == 1) {
        if (sender.isOp())
          this.player = Bukkit.getPlayer(args[0]); 
      } else {
        this.player = (Player)sender;
      } 
      if (this.player.getGameMode() == GameMode.SURVIVAL) {
        this.player.setHealth(40.0D);
        for (PotionEffect effect : player.getActivePotionEffects())
          player.removePotionEffect(effect.getType());
        player.setCustomName(" ");
        this.player.sendMessage(ChatColor.GREEN + "Du wurdest geheilt!");
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(1.0D, 1.0D, 1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(1.0D, 1.0D, 0.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(1.0D, 1.0D, -1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(0.0D, 1.0D, 1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(0.0D, 1.0D, -1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(-1.0D, 1.0D, -1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(-1.0D, 1.0D, 1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(-1.0D, 1.0D, 0.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(1.0D, 2.0D, 1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(1.0D, 2.0D, 0.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(1.0D, 2.0D, -1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(0.0D, 2.0D, 1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(0.0D, 2.0D, -1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(-1.0D, 2.0D, -1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(-1.0D, 2.0D, 1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(-1.0D, 2.0D, 0.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(1.0D, 0.0D, 1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(1.0D, 0.0D, 0.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(1.0D, 0.0D, -1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(0.0D, 0.0D, 1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(0.0D, 0.0D, -1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(-1.0D, 0.0D, -1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(-1.0D, 0.0D, 1.0D), 1);
        Bukkit.getServer().getWorld(this.player.getWorld().getName()).spawnParticle(Particle.HEART, this.player.getLocation().add(-1.0D, 0.0D, 0.0D), 1);
      } 
    } 
    return true;
  }
}
