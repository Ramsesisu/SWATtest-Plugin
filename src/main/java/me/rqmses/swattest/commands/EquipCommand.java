package me.rqmses.swattest.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

public class EquipCommand implements CommandExecutor, TabCompleter {

    public static HashMap<String, ItemStack[]> playerinv = new HashMap<String, ItemStack[]>();

    public static HashMap<String, Long> cooldowns = new HashMap<String, Long>();

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

                ItemStack elytra = new ItemStack(Material.ELYTRA);
                ItemMeta elytraname = elytra.getItemMeta();
                elytraname.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lFallschirm"));
                elytra.setItemMeta(elytraname);

                ItemStack kev = new ItemStack(Material.LEATHER_CHESTPLATE);
                LeatherArmorMeta kevcolor = (LeatherArmorMeta) kev.getItemMeta();
                kevcolor.setColor(Color.fromRGB(71, 79, 82));
                kev.setItemMeta(kevcolor);
                ItemMeta kevname = kev.getItemMeta();
                kevname.setDisplayName(ChatColor.GRAY + "Kevlar");
                kev.setItemMeta(kevname);

                ItemStack schild = new ItemStack(Material.SHIELD);
                schild.setDurability((short) 176);
                ItemMeta schildname = schild.getItemMeta();
                schildname.setDisplayName(ChatColor.GRAY + "Einsatzschild");
                schild.setItemMeta(schildname);

                ItemStack flashes = new ItemStack(Material.SLIME_BALL, 5);
                ItemMeta flashmeta = flashes.getItemMeta();
                flashmeta.setDisplayName(ChatColor.GRAY + "Blendgranate");
                flashes.setItemMeta(flashmeta);

                ItemStack m4 = new ItemStack(Material.DIAMOND_BARDING);
                ItemMeta m4meta = m4.getItemMeta();
                m4meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lM4"));
                ArrayList<String> m4lore = new ArrayList<String>();
                m4lore.add(ChatColor.translateAlternateColorCodes('&', "&621&8/&6500"));
                m4meta.setLore(m4lore);
                m4.setItemMeta(m4meta);

                ItemStack mp5 = new ItemStack(Material.GOLD_BARDING);
                ItemMeta mp5meta = mp5.getItemMeta();
                mp5meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lMP5"));
                ArrayList<String> mp5lore = new ArrayList<String>();
                mp5lore.add(ChatColor.translateAlternateColorCodes('&', "&621&8/&6500"));
                mp5meta.setLore(mp5lore);
                mp5.setItemMeta(mp5meta);

                ItemStack sniper = new ItemStack(Material.STONE_HOE);
                ItemMeta snipermeta = sniper.getItemMeta();
                snipermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lSniper"));
                ArrayList<String> sniperlore = new ArrayList<String>();
                sniperlore.add(ChatColor.translateAlternateColorCodes('&', "&65&8/&630"));
                snipermeta.setLore(sniperlore);
                sniper.setItemMeta(snipermeta);

                ItemStack jagdflinte = new ItemStack(Material.GOLD_HOE);
                ItemMeta jagdflintemeta = jagdflinte.getItemMeta();
                jagdflintemeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lJagdflinte"));
                ArrayList<String> jagdflintelore = new ArrayList<String>();
                jagdflintelore.add(ChatColor.translateAlternateColorCodes('&', "&65&8/&680"));
                jagdflintemeta.setLore(jagdflintelore);
                jagdflinte.setItemMeta(jagdflintemeta);

                ItemStack rpg = new ItemStack(Material.GOLD_AXE);
                ItemMeta rpgmeta = rpg.getItemMeta();
                rpgmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lRPG-7"));
                ArrayList<String> rpglore = new ArrayList<String>();
                rpglore.add(ChatColor.translateAlternateColorCodes('&', "&61&8/&6100"));
                rpgmeta.setLore(rpglore);
                rpg.setItemMeta(rpgmeta);

                ItemStack messer = new ItemStack(Material.FEATHER);
                ItemMeta messermeta = messer.getItemMeta();
                messermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8Messer"));
                ArrayList<String> messerlore = new ArrayList<String>();
                messerlore.add(ChatColor.translateAlternateColorCodes('&', "&6100&8/&6100"));
                messermeta.setLore(messerlore);
                messer.setItemMeta(messermeta);

                ItemStack tazer = new ItemStack(Material.WOOD_HOE);
                ItemMeta tazermeta = tazer.getItemMeta();
                tazermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eTazer"));
                tazer.setItemMeta(tazermeta);

                ItemStack flammenwerfer = new ItemStack(Material.BLAZE_POWDER);
                ItemMeta flammenwerfermeta = flammenwerfer.getItemMeta();
                flammenwerfermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cFlammenwerfer"));
                ArrayList<String> flammenwerferlore = new ArrayList<String>();
                flammenwerferlore.add(ChatColor.translateAlternateColorCodes('&', "&6500&8/&6500"));
                flammenwerfermeta.setLore(flammenwerferlore);
                flammenwerfer.setItemMeta(flammenwerfermeta);

                playerinv.putIfAbsent(player.getName(), null);

                switch (args[0].toLowerCase()) {
                    case "elytra":
                        player.getInventory().clear();
                        player.getInventory().addItem(elytra);
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&3Elytra&8&l] &r") + player.getName() + fly);
                        player.getInventory();
                        EquipCommand.playerinv.put(player.getName(),player.getInventory().getContents());
                        break;
                    case "swat":
                        player.getInventory().clear();
                        kev.setDurability((short) 30);
                        player.getInventory().addItem(schild);
                        player.getInventory().addItem(kev);
                        player.getInventory().addItem(m4);
                        player.getInventory().addItem(sniper);
                        player.getInventory().addItem(flashes);
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&1SWAT&8&l] &r") + player.getName() + fly);
                        EquipCommand.playerinv.put(player.getName(),player.getInventory().getContents());
                        break;
                    case "ucpd":
                        player.getInventory().clear();
                        kev.setDurability((short) 50);
                        player.getInventory().addItem(kev);
                        player.getInventory().addItem(m4);
                        player.getInventory().addItem(mp5);
                        player.getInventory().addItem(tazer);
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&9UCPD&8&l] &r") + player.getName() + fly);
                        EquipCommand.playerinv.put(player.getName(),player.getInventory().getContents());
                        break;
                    case "ballas":
                        player.getInventory().clear();
                        kev.setDurability((short) 50);
                        player.getInventory().addItem(kev);
                        player.getInventory().addItem(m4);
                        player.getInventory().addItem(jagdflinte);
                        player.getInventory().addItem(messer);
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&5Ballas&8&l] &r") + player.getName() + fly);
                        EquipCommand.playerinv.put(player.getName(),player.getInventory().getContents());
                        break;
                    case "terror":
                        player.getInventory().clear();
                        kev.setDurability((short) 50);
                        player.getInventory().addItem(kev);
                        player.getInventory().addItem(m4);
                        player.getInventory().addItem(rpg);
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&eTerror&8&l] &r") + player.getName() + fly);
                        EquipCommand.playerinv.put(player.getName(),player.getInventory().getContents());
                        break;
                    case "zivilist":
                        player.getInventory().clear();
                        player.getInventory().addItem(mp5);
                        player.getInventory().addItem(messer);
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&7Zivi&8&l] &r") + player.getName() + fly);
                        EquipCommand.playerinv.put(player.getName(),player.getInventory().getContents());
                        break;
                    case "flammi":
                        player.getInventory().clear();
                        kev.setDurability((short) 50);
                        player.getInventory().addItem(kev);
                        player.getInventory().addItem(flammenwerfer);
                        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&cFlammi&8&l] &r") + player.getName() + fly);
                        EquipCommand.playerinv.put(player.getName(),player.getInventory().getContents());
                        break;
                    case "none":
                        player.getInventory().clear();
                        player.setPlayerListName(player.getName() + fly);
                        EquipCommand.playerinv.put(player.getName(),player.getInventory().getContents());
                        break;
                    default:
                        player.sendMessage(ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " ist kein vorgegebenes Equip!");
                        return true;
                }
                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + player.getName() + ChatColor.GRAY + " hat sein Equip zu " + ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " geändert!");
            }
            return true;
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String[] equip = new String[] {"Elytra", "SWAT", "UCPD", "Ballas", "Terror", "Zivilist", "Flammi", "None"};
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
