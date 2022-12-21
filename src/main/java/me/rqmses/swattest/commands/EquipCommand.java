package me.rqmses.swattest.commands;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.Functions;
import me.rqmses.swattest.global.Items;
import me.rqmses.swattest.listeners.PlayerInteractListener;
import me.rqmses.swattest.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static me.rqmses.swattest.commands.BuildmodeCommand.buildmode;

public class EquipCommand implements CommandExecutor, TabCompleter {
  public static final HashMap<String, Long> cooldowns = new HashMap<>();
  
  public static final HashMap<String, String> playerequip = new HashMap<>();
  
  public static final HashMap<String, BukkitTask> rpgtask = new HashMap<>();
  
  Player player;
  
  @SuppressWarnings({"ResultOfMethodCallIgnored"})
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (!buildmode.contains(((Player) sender).getPlayer())) {
        if (args.length == 2) {
          if (sender.isOp())
            this.player = Bukkit.getPlayer(args[1]);
        } else {
          this.player = (Player) sender;
          int cooldownTime = 30;
          if (cooldowns.containsKey(this.player.getName())) {
            long secondsLeft = cooldowns.get(this.player.getName()) / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
            if (secondsLeft > 0L) {
              this.player.sendMessage(ChatColor.GRAY + "Du kannst dein Equip erst in " + ChatColor.DARK_GRAY + secondsLeft + " Sekunden" + ChatColor.GRAY + " wieder ändern.");
              return true;
            }
          }
          cooldowns.put(this.player.getName(), System.currentTimeMillis());
        }
        if (args.length == 0) {
          this.player.sendMessage(ChatColor.GRAY + "Du musst ein Equip angeben!");
        } else {
          String playername = Functions.getTeam(player).getColor() + player.getName();
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
              this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&3Elytra&8&l] &r") + playername + fly);
              break;
            case "swat":
              playerequip.put(this.player.getName(), "swat");
              this.player.getInventory().clear();
              invchanged = true;
              this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&1SWAT&8&l] &r") + playername + fly);
              break;
            case "polizei":
              playerequip.put(this.player.getName(), "polizei");
              this.player.getInventory().clear();
              invchanged = true;
              this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&9UCPD&8&l] &r") + playername + fly);
              break;
            case "ballas":
              playerequip.put(this.player.getName(), "ballas");
              this.player.getInventory().clear();
              invchanged = true;
              this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&5Ballas&8&l] &r") + playername + fly);
              break;
            case "terror":
              playerequip.put(this.player.getName(), "terror");
              this.player.getInventory().clear();
              invchanged = true;
              PlayerInteractListener.rpgcooldown.put(this.player.getName(), Boolean.FALSE);
              this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&eTerror&8&l] &r") + playername + fly);
              rpg = new BukkitRunnable() {
                public void run() {
                  PlayerInteractListener.rpgcooldown.put(EquipCommand.this.player.getName(), Boolean.TRUE);
                  EquipCommand.this.player.sendMessage(ChatColor.GRAY + "Du kannst deine RPG nun benutzen!");
                }
              };
              rpgtask.put(this.player.getName(), rpg.runTaskLater(SWATtest.plugin, 600L));
              break;
            case "zivilist":
              playerequip.put(this.player.getName(), "zivilist");
              this.player.getInventory().clear();
              invchanged = true;
              this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&7Zivi&8&l] &r") + playername + fly);
              break;
            case "flammenwerfer":
              playerequip.put(this.player.getName(), "flammenwerfer");
              this.player.getInventory().clear();
              invchanged = true;
              this.player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&cFlammi&8&l] &r") + playername + fly);
              break;
            case "none":
              this.player.getInventory().clear();
              this.player.setPlayerListName(playername + fly);
              break;
            default:
              this.player.sendMessage(ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " ist kein vorgegebenes Equip!");
              return true;
          }
          if (invchanged) {
            int itemamount = 0;

            for (int i = 0; i <= 8; i++) {
              String itemname = (String) PlayerJoinListener.playerconfig.get(this.player.getUniqueId()).get(playerequip.get(this.player.getName()) + "." + i);
              switch (itemname) {
                case "SHIELD":
                  itemamount++;
                  this.player.getInventory().setItem(i, Items.getSchild());
                  break;
                case "LEATHER_CHESTPLATE":
                  itemamount++;
                  if (Objects.equals(playerequip.get(this.player.getName()), "swat")) {
                    this.player.getInventory().setItem(i, Items.getKev((short) 30));
                    break;
                  } else if (Objects.equals(playerequip.get(this.player.getName()), "terror")) {
                    this.player.getInventory().setItem(i, Items.getSprengi());
                    break;
                  }
                  this.player.getInventory().setItem(i, Items.getKev((short) 50));
                  break;
                case "DIAMOND_BARDING":
                  itemamount++;
                  this.player.getInventory().setItem(i, Items.getM4());
                  break;
                case "STONE_HOE":
                  itemamount++;
                  this.player.getInventory().setItem(i, Items.getSniper());
                  break;
                case "SLIME_BALL":
                  itemamount++;
                  if (!player.getInventory().contains(Material.SLIME_BALL)) {
                    this.player.getInventory().setItem(i, Items.getFlashes());
                  }
                  break;
                case "GOLD_HOE":
                  itemamount++;
                  this.player.getInventory().setItem(i, Items.getJagdflinte());
                  break;
                case "FEATHER":
                  itemamount++;
                  this.player.getInventory().setItem(i, Items.getMesser());
                  break;
                case "WOOD_HOE":
                  itemamount++;
                  this.player.getInventory().setItem(i, Items.getTazer());
                  break;
                case "GOLD_AXE":
                  itemamount++;
                  this.player.getInventory().setItem(i, Items.getRPG());
                  break;
                case "BLAZE_POWDER":
                  itemamount++;
                  this.player.getInventory().setItem(i, Items.getFlammenwerfer());
                  break;
                case "GOLD_BARDING":
                  itemamount++;
                  this.player.getInventory().setItem(i, Items.getMp5());
                  break;
                case "ELYTRA":
                  itemamount++;
                  this.player.getInventory().setItem(i, Items.getElytra());
                  break;
                default:
                  this.player.getInventory().setItem(i, Items.getAir());
                  break;
              }
            }

            if (Objects.equals(playerequip.get(player.getName()), "swat")) {
              if (itemamount != 5) {
                PlayerJoinListener.playersave.get(player.getUniqueId()).delete();
                Functions.createFile(player);
                Functions.equipPlayer(player);
                cooldowns.put(player.getName(), 600L);
              }
            }
            if (Objects.equals(playerequip.get(player.getName()), "elytra")) {
              if (itemamount != 1) {
                PlayerJoinListener.playersave.get(player.getUniqueId()).delete();
                Functions.createFile(player);
                Functions.equipPlayer(player);
                cooldowns.put(player.getName(), 600L);
              }
            }
            if (Objects.equals(playerequip.get(player.getName()), "ballas")) {
              if (itemamount != 4) {
                PlayerJoinListener.playersave.get(player.getUniqueId()).delete();
                Functions.createFile(player);
                Functions.equipPlayer(player);
                cooldowns.put(player.getName(), 600L);
              }
            }
            if (Objects.equals(playerequip.get(player.getName()), "polizei")) {
              if (itemamount != 4) {
                PlayerJoinListener.playersave.get(player.getUniqueId()).delete();
                Functions.createFile(player);
                Functions.equipPlayer(player);
                cooldowns.put(player.getName(), 600L);
              }
            }
            if (Objects.equals(playerequip.get(player.getName()), "zivilist")) {
              if (itemamount != 2) {
                PlayerJoinListener.playersave.get(player.getUniqueId()).delete();
                Functions.createFile(player);
                Functions.equipPlayer(player);
                cooldowns.put(player.getName(), 600L);
              }
            }
            if (Objects.equals(playerequip.get(player.getName()), "flammenwerfer")) {
              if (itemamount != 2) {
                PlayerJoinListener.playersave.get(player.getUniqueId()).delete();
                Functions.createFile(player);
                Functions.equipPlayer(player);
                cooldowns.put(player.getName(), 600L);
              }
            }
            if (Objects.equals(playerequip.get(player.getName()), "terror")) {
              if (itemamount != 3) {
                PlayerJoinListener.playersave.get(player.getUniqueId()).delete();
                Functions.createFile(player);
                Functions.equipPlayer(player);
                cooldowns.put(player.getName(), 600L);
              }
            }
          }
          if (this.player.getGameMode() == GameMode.SURVIVAL) {
            if (args.length != 3) {
              this.player.sendMessage(ChatColor.GRAY + "Du hast dein Equip zu " + ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " geändert!");
            }
          }
        }
        return true;
      } else {
        sender.sendMessage(ChatColor.DARK_GREEN + "Du kannst diesen Befehl im Build-Mode nicht benutzen!");
      }
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
