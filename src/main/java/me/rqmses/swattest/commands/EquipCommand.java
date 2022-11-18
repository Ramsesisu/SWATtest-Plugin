package me.rqmses.swattest.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.Items;
import me.rqmses.swattest.listeners.PlayerJoinListener;
import me.rqmses.swattest.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EquipCommand implements CommandExecutor, TabCompleter {
  public static final HashMap<String, Long> cooldowns = new HashMap<>();
  
  public static final HashMap<String, String> playerequip = new HashMap<>();
  
  public static final HashMap<String, BukkitTask> rpgtask = new HashMap<>();
  
  Player player;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (args.length == 2) {
        if (sender.isOp())
          this.player = Bukkit.getPlayer(args[1]); 
      } else {
        this.player = (Player)sender;
        int cooldownTime = 30;
        if (cooldowns.containsKey(this.player.getName())) {
          long secondsLeft = ((Long)cooldowns.get(this.player.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
          if (secondsLeft > 0L) {
            this.player.sendMessage(ChatColor.GRAY + "Du kannst dein Equip erst in " + ChatColor.DARK_GRAY + secondsLeft + " Sekunden" + ChatColor.GRAY + " wieder ändern.");
            return true;
          } 
        } 
        cooldowns.put(this.player.getName(), Long.valueOf(System.currentTimeMillis()));
      } 
      if (args.length == 0) {
        this.player.sendMessage(ChatColor.GRAY + "Du musst ein Equip angeben!");
      } else {
        BukkitRunnable rpg;
        String fly = "";
        if (this.player.getPlayerListName().endsWith(" F"))
          fly = ChatColor.translateAlternateColorCodes('&', "&b&l F"); 
        boolean invchanged = false;
        switch (args[0].toLowerCase()) {
          case "elytra":
            playerequip.put(this.player.getName(), "elytra");
            this.player.getInventory().clear();
            invchanged = true;
            this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&3Elytra&8&l] &r") + this.player.getName() + fly);
            break;
          case "swat":
            playerequip.put(this.player.getName(), "swat");
            this.player.getInventory().clear();
            invchanged = true;
            this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&1SWAT&8&l] &r") + this.player.getName() + fly);
            break;
          case "polizei":
            playerequip.put(this.player.getName(), "polizei");
            this.player.getInventory().clear();
            invchanged = true;
            this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&9UCPD&8&l] &r") + this.player.getName() + fly);
            break;
          case "ballas":
            playerequip.put(this.player.getName(), "ballas");
            this.player.getInventory().clear();
            invchanged = true;
            this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&5Ballas&8&l] &r") + this.player.getName() + fly);
            break;
          case "terror":
            playerequip.put(this.player.getName(), "terror");
            this.player.getInventory().clear();
            invchanged = true;
            PlayerInteractListener.rpgcooldown.put(this.player.getName(), Boolean.valueOf(false));
            this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&eTerror&8&l] &r") + this.player.getName() + fly);
            rpg = new BukkitRunnable() {
                public void run() {
                  PlayerInteractListener.rpgcooldown.put(EquipCommand.this.player.getName(), Boolean.valueOf(true));
                  EquipCommand.this.player.sendMessage(ChatColor.GRAY + "Du kannst deine RPG nun benutzen!");
                }
              };
            rpgtask.put(this.player.getName(), rpg.runTaskLater((Plugin)SWATtest.plugin, 600L));
            break;
          case "zivilist":
            playerequip.put(this.player.getName(), "zivilist");
            this.player.getInventory().clear();
            invchanged = true;
            this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&7Zivi&8&l] &r") + this.player.getName() + fly);
            break;
          case "flammenwerfer":
            playerequip.put(this.player.getName(), "flammenwerfer");
            this.player.getInventory().clear();
            invchanged = true;
            this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&cFlammi&8&l] &r") + this.player.getName() + fly);
            break;
          case "none":
            this.player.getInventory().clear();
            this.player.setPlayerListName(this.player.getName() + fly);
            break;
          default:
            this.player.sendMessage(ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " ist kein vorgegebenes Equip!");
            return true;
        } 
        if (invchanged)
          for (int i = 0; i <= 8; i++) {
            String itemname = (String)((FileConfiguration) PlayerJoinListener.playerconfig.get(this.player.getUniqueId())).get((String)playerequip.get(this.player.getName()) + "." + i);
            switch (itemname) {
              case "SHIELD":
                this.player.getInventory().setItem(i, Items.getSchild());
                break;
              case "LEATHER_CHESTPLATE":
                if (Objects.equals(playerequip.get(this.player.getName()), "swat")) {
                  this.player.getInventory().setItem(i, Items.getKev((short)30));
                  break;
                } 
                this.player.getInventory().setItem(i, Items.getKev((short)50));
                break;
              case "DIAMOND_BARDING":
                this.player.getInventory().setItem(i, Items.getM4());
                break;
              case "STONE_HOE":
                this.player.getInventory().setItem(i, Items.getSniper());
                break;
              case "SLIME_BALL":
                this.player.getInventory().setItem(i, Items.getFlashes());
                break;
              case "GOLD_HOE":
                this.player.getInventory().setItem(i, Items.getJagdflinte());
                break;
              case "FEATHER":
                this.player.getInventory().setItem(i, Items.getMesser());
                break;
              case "WOOD_HOE":
                this.player.getInventory().setItem(i, Items.getTazer());
                break;
              case "GOLD_AXE":
                this.player.getInventory().setItem(i, Items.getRPG());
                break;
              case "BLAZE_POWDER":
                this.player.getInventory().setItem(i, Items.getFlammenwerfer());
                break;
              case "GOLD_BARDING":
                this.player.getInventory().setItem(i, Items.getMp5());
                break;
              case "ELYTRA":
                this.player.getInventory().setItem(i, Items.getElytra());
                break;
              default:
                this.player.getInventory().setItem(i, Items.getAir());
                break;
            } 
          }  
        if (this.player.getGameMode() == GameMode.SURVIVAL)
          this.player.sendMessage(ChatColor.GRAY + "Du hast dein Equip zu " + ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " geändert!"); 
      } 
      return true;
    } 
    return true;
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    ArrayList<String> list = new ArrayList<>();
    String[] equip = { "Elytra", "SWAT", "Polizei", "Ballas", "Terror", "Zivilist", "Flammenwerfer", "None" };
    if (args.length == 1)
      for (String items : equip) {
        if (items.toUpperCase().startsWith(args[0].toUpperCase()))
          list.add(items); 
      }  
    return list;
  }
}
