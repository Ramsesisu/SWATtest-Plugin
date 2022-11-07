package me.rqmses.swattest.variables;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;

public class Items {

    public static ItemStack getAir() {
        return new ItemStack(Material.AIR);
    }

    public static ItemStack getElytra() {
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemMeta elytraname = elytra.getItemMeta();
        elytraname.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&3&lFallschirm"));
        elytra.setItemMeta(elytraname);
        return elytra;
    }

    public static ItemStack getKev(short durability) {
        ItemStack kev = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta kevcolor = (LeatherArmorMeta) kev.getItemMeta();
        kevcolor.setColor(Color.fromRGB(71, 79, 82));
        kev.setItemMeta(kevcolor);
        ItemMeta kevname = kev.getItemMeta();
        kevname.setDisplayName(ChatColor.GRAY + "Kevlar");
        kevname.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        kev.setItemMeta(kevname);
        kev.setDurability(durability);
        return kev;
    }

    public static ItemStack getSchild() {
        ItemStack schild = new ItemStack(Material.SHIELD);
        schild.setDurability((short) 176);
        ItemMeta schildname = schild.getItemMeta();
        schildname.setDisplayName(ChatColor.GRAY + "Einsatzschild");
        schild.setItemMeta(schildname);
        return schild;
    }

    public static ItemStack getFlashes() {
        ItemStack flashes = new ItemStack(Material.SLIME_BALL, 6);
        ItemMeta flashmeta = flashes.getItemMeta();
        flashmeta.setDisplayName(ChatColor.GRAY + "Blendgranate");
        flashes.setItemMeta(flashmeta);
        return flashes;
    }

    public static ItemStack getM4() {
        ItemStack m4 = new ItemStack(Material.DIAMOND_BARDING);
        ItemMeta m4meta = m4.getItemMeta();
        m4meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lM4"));
        ArrayList<String> m4lore = new ArrayList<>();
        m4lore.add(ChatColor.translateAlternateColorCodes('&', "&621&8/&6500"));
        m4meta.setLore(m4lore);
        m4.setItemMeta(m4meta);
        return m4;
    }

    public static ItemStack getMp5() {
        ItemStack mp5 = new ItemStack(Material.GOLD_BARDING);
        ItemMeta mp5meta = mp5.getItemMeta();
        mp5meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lMP5"));
        ArrayList<String> mp5lore = new ArrayList<>();
        mp5lore.add(ChatColor.translateAlternateColorCodes('&', "&621&8/&6500"));
        mp5meta.setLore(mp5lore);
        mp5.setItemMeta(mp5meta);
        return mp5;
    }

    public static ItemStack getSniper() {
        ItemStack sniper = new ItemStack(Material.STONE_HOE);
        ItemMeta snipermeta = sniper.getItemMeta();
        snipermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lSniper"));
        ArrayList<String> sniperlore = new ArrayList<>();
        sniperlore.add(ChatColor.translateAlternateColorCodes('&', "&65&8/&630"));
        snipermeta.setLore(sniperlore);
        snipermeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        sniper.setItemMeta(snipermeta);
        return sniper;
    }

    public static ItemStack getJagdflinte() {
        ItemStack jagdflinte = new ItemStack(Material.GOLD_HOE);
        ItemMeta jagdflintemeta = jagdflinte.getItemMeta();
        jagdflintemeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lJagdflinte"));
        ArrayList<String> jagdflintelore = new ArrayList<>();
        jagdflintelore.add(ChatColor.translateAlternateColorCodes('&', "&65&8/&680"));
        jagdflintemeta.setLore(jagdflintelore);
        jagdflintemeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        jagdflinte.setItemMeta(jagdflintemeta);
        return jagdflinte;
    }

    public static ItemStack getRPG() {
        ItemStack rpg = new ItemStack(Material.GOLD_AXE);
        ItemMeta rpgmeta = rpg.getItemMeta();
        rpgmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lRPG-7"));
        ArrayList<String> rpglore = new ArrayList<>();
        rpglore.add(ChatColor.translateAlternateColorCodes('&', "&61&8/&610"));
        rpgmeta.setLore(rpglore);
        rpgmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        rpg.setItemMeta(rpgmeta);
        return rpg;
    }

    public static ItemStack getMesser() {
        ItemStack messer = new ItemStack(Material.FEATHER);
        ItemMeta messermeta = messer.getItemMeta();
        messermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8Messer"));
        ArrayList<String> messerlore = new ArrayList<>();
        messerlore.add(ChatColor.translateAlternateColorCodes('&', "&6100&8/&6100"));
        messermeta.setLore(messerlore);
        messer.setItemMeta(messermeta);
        return messer;
    }

    public static ItemStack getTazer() {
        ItemStack tazer = new ItemStack(Material.WOOD_HOE);
        ItemMeta tazermeta = tazer.getItemMeta();
        tazermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eTazer"));
        tazermeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        tazer.setItemMeta(tazermeta);
        return tazer;
    }

    public static ItemStack getFlammenwerfer() {
        ItemStack flammenwerfer = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta flammenwerfermeta = flammenwerfer.getItemMeta();
        flammenwerfermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cFlammenwerfer"));
        ArrayList<String> flammenwerferlore = new ArrayList<>();
        flammenwerferlore.add(ChatColor.translateAlternateColorCodes('&', "&6500&8/&6500"));
        flammenwerfermeta.setLore(flammenwerferlore);
        flammenwerfermeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        flammenwerfer.setItemMeta(flammenwerfermeta);
        return flammenwerfer;
    }
}
