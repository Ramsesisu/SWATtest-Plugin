package me.rqmses.swattest.commands;

import me.rqmses.swattest.listeners.JoinListener;
import me.rqmses.swattest.listeners.PlayerInteractListener;
import me.rqmses.swattest.variables.Items;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static me.rqmses.swattest.SWATtest.plugin;

public class EquipCommand implements CommandExecutor, TabCompleter {

    public static final HashMap<String, Long> cooldowns = new HashMap<>();

    public static final HashMap<String, String> playerequip = new HashMap<>();

    public static final HashMap<String, BukkitTask> rpgtask = new HashMap<>();

    Player player;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 2) {
                if (sender.isOp()) {
                    player = Bukkit.getPlayer(args[1]);
                }
            } else {
                player = (Player) sender;

                int cooldownTime = 30;
                if (cooldowns.containsKey(player.getName())) {
                    long secondsLeft = ((cooldowns.get(player.getName()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
                    if (secondsLeft > 0) {
                        player.sendMessage(ChatColor.GRAY + "Du kannst dein Equip erst in " + ChatColor.DARK_GRAY + secondsLeft + " Sekunden" + ChatColor.GRAY + " wieder ändern.");
                        return true;
                    }
                }
                cooldowns.put(player.getName(), System.currentTimeMillis());
            }

            if(args.length == 0 ) {
                player.sendMessage(ChatColor.GRAY + "Du musst ein Equip angeben!");
            } else {

                String fly = "";

                if (player.getPlayerListName().endsWith(" F")) {
                    fly = ChatColor.translateAlternateColorCodes('&', "&b&l F");
                }

                boolean invchanged = false;

                switch (args[0].toLowerCase()) {
                    case "elytra":
                        playerequip.put(player.getName(), "elytra");
                        player.getInventory().clear();
                        invchanged = true;
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&3Elytra&8&l] &r") + player.getName() + fly);
                        break;
                    case "swat":
                        playerequip.put(player.getName(), "swat");
                        player.getInventory().clear();
                        invchanged = true;
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&1SWAT&8&l] &r") + player.getName() + fly);
                        break;
                    case "polizei":
                        playerequip.put(player.getName(), "polizei");
                        player.getInventory().clear();
                        invchanged = true;
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&9UCPD&8&l] &r") + player.getName() + fly);
                        break;
                    case "ballas":
                        playerequip.put(player.getName(), "ballas");
                        player.getInventory().clear();
                        invchanged = true;
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&5Ballas&8&l] &r") + player.getName() + fly);
                        break;
                    case "terror":
                        playerequip.put(player.getName(), "terror");
                        player.getInventory().clear();
                        invchanged = true;
                        PlayerInteractListener.rpgcooldown.put(player.getName(), false);
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&eTerror&8&l] &r") + player.getName() + fly);
                        BukkitRunnable rpg = new BukkitRunnable() {
                            @Override
                            public void run() {
                                PlayerInteractListener.rpgcooldown.put(player.getName(), true);
                            }
                        };
                        rpgtask.put(player.getName(), rpg.runTaskLater(plugin, 300L));
                        break;
                    case "zivilist":
                        playerequip.put(player.getName(), "zivilist");
                        player.getInventory().clear();
                        invchanged = true;
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&7Zivi&8&l] &r") + player.getName() + fly);
                        break;
                    case "flammenwerfer":
                        playerequip.put(player.getName(), "flammenwerfer");
                        player.getInventory().clear();
                        invchanged = true;
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&cFlammi&8&l] &r") + player.getName() + fly);
                        break;
                    case "none":
                        player.getInventory().clear();
                        player.setPlayerListName(player.getName() + fly);
                        break;
                    default:
                        player.sendMessage(ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " ist kein vorgegebenes Equip!");
                        return true;
                }

                if (invchanged) {
                    for (int i = 0; i <= 8; i++) {
                        String itemname = (String) JoinListener.playerconfig.get(player.getUniqueId()).get(playerequip.get(player.getName())+"."+i);

                        switch (itemname) {
                            case "SHIELD":
                                player.getInventory().setItem(i, Items.getSchild());
                                break;
                            case "LEATHER_CHESTPLATE":
                                if (Objects.equals(playerequip.get(player.getName()), "swat")) {
                                    player.getInventory().setItem(i, Items.getKev((short) 30));
                                } else {
                                    player.getInventory().setItem(i, Items.getKev((short) 50));
                                }
                                break;
                            case "DIAMOND_BARDING":
                                player.getInventory().setItem(i, Items.getM4());
                                break;
                            case "STONE_HOE":
                                player.getInventory().setItem(i, Items.getSniper());
                                break;
                            case "SLIME_BALL":
                                player.getInventory().setItem(i, Items.getFlashes());
                                break;
                            case "GOLD_HOE":
                                player.getInventory().setItem(i, Items.getJagdflinte());
                                break;
                            case "FEATHER":
                                player.getInventory().setItem(i, Items.getMesser());
                                break;
                            case "WOOD_HOE":
                                player.getInventory().setItem(i, Items.getTazer());
                                break;
                            case "GOLD_AXE":
                                player.getInventory().setItem(i, Items.getRPG());
                                break;
                            case "BLAZE_POWDER":
                                player.getInventory().setItem(i, Items.getFlammenwerfer());
                                break;
                            case "GOLD_BARDING":
                                player.getInventory().setItem(i, Items.getMp5());
                                break;
                            case "ELYTRA":
                                player.getInventory().setItem(i, Items.getElytra());
                                break;
                            default:
                                player.getInventory().setItem(i, Items.getAir());
                                break;
                        }
                    }
                }

                if (player.getGameMode() == GameMode.SURVIVAL) {
                    player.sendMessage(ChatColor.GRAY + "Du hast dein Equip zu " + ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " geändert!");
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String[] equip = new String[] {"Elytra", "SWAT", "Polizei", "Ballas", "Terror", "Zivilist", "Flammenwerfer", "None"};
        if (args.length == 1) {
            for (String items : equip) {
                if (items.toUpperCase().startsWith(args[0].toUpperCase())) {
                    list.add(items);
                }
            }
        }
        return list;
    }
}
