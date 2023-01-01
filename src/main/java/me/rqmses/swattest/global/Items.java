package me.rqmses.swattest.global;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;

public class Items {
  public static final String[] itemlist = { "Elytra" , "Kevlar", "Sprenggürtel", "Schild", "Flashes", "M4", "Mp5", "Pistole", "Sniper", "Jagdflinte", "RPG", "Messer", "Tazer", "Flammenwerfer"};

  public static Material getItem(String item) {
    switch (item.toLowerCase()) {
      case "elytra":
        return getElytra().getType();
      case "kevlar":
        return getKev((short) 50).getType();
      case "sprenggürtel":
        return getSprengi().getType();
      case "schild":
        return getSchild().getType();
      case "flashes":
        return getFlashes().getType();
      case "m4":
        return getM4().getType();
      case "mp5":
        return getMp5().getType();
      case "pistole":
        return getPistole().getType();
      case "sniper":
        return getSniper().getType();
      case "jagdflinte":
        return getJagdflinte().getType();
      case "rpg":
        return getRPG().getType();
      case "messer":
        return getMesser().getType();
      case "tazer":
        return getTazer().getType();
      case "flammenwerfer":
        return getFlammenwerfer().getType();
    }
    return null;
  }

  public static ItemStack getAir() {
    return new ItemStack(Material.AIR);
  }
  
  public static ItemStack getElytra() {
    ItemStack elytra = new ItemStack(Material.ELYTRA);
    ItemMeta elytraname = elytra.getItemMeta();
    elytraname.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lFallschirm"));
    elytra.setItemMeta(elytraname);
    return elytra;
  }
  
  public static ItemStack getKev(short durability) {
    ItemStack kev = new ItemStack(Material.LEATHER_CHESTPLATE);
    LeatherArmorMeta kevcolor = (LeatherArmorMeta)kev.getItemMeta();
    kevcolor.setColor(Color.fromRGB(71, 79, 82));
    kev.setItemMeta(kevcolor);
    ItemMeta kevname = kev.getItemMeta();
    kevname.setDisplayName(ChatColor.GRAY + "Kevlar");
    kevname.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    kev.setItemMeta(kevname);
    kev.setDurability(durability);
    return kev;
  }

  public static ItemStack getSprengi() {
    ItemStack sprengi = new ItemStack(Material.LEATHER_CHESTPLATE);
    ItemMeta kevname = sprengi.getItemMeta();
    kevname.setDisplayName(ChatColor.GRAY + "Sprenggürtel");
    kevname.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    sprengi.setItemMeta(kevname);
    sprengi.setDurability((short) 0);
    return sprengi;
  }

  public static ItemStack getSchild() {
    ItemStack schild = new ItemStack(Material.SHIELD);
    schild.setDurability((short)176);
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
    mp5meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lMp5"));
    ArrayList<String> mp5lore = new ArrayList<>();
    mp5lore.add(ChatColor.translateAlternateColorCodes('&', "&621&8/&6500"));
    mp5meta.setLore(mp5lore);
    mp5.setItemMeta(mp5meta);
    return mp5;
  }

  public static ItemStack getPistole() {
    ItemStack pistole = new ItemStack(Material.IRON_BARDING);
    ItemMeta pistolemeta = pistole.getItemMeta();
    pistolemeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lPistole"));
    ArrayList<String> pistolelore = new ArrayList<>();
    pistolelore.add(ChatColor.translateAlternateColorCodes('&', "&615&8/&6300"));
    pistolemeta.setLore(pistolelore);
    pistole.setItemMeta(pistolemeta);
    return pistole;
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

  public static ArrayList<Material> getSlabs() {
    ArrayList<Material> slabs = new ArrayList<>();
    slabs.add(Material.STEP);
    slabs.add(Material.WOOD_STEP);
    slabs.add(Material.PURPUR_SLAB);
    slabs.add(Material.STONE_SLAB2);
    return slabs;
  }

  public static ArrayList<Material> getStairs() {
    ArrayList<Material> stairs = new ArrayList<>();
    stairs.add(Material.SANDSTONE_STAIRS);
    stairs.add(Material.SMOOTH_STAIRS);
    stairs.add(Material.ACACIA_STAIRS);
    stairs.add(Material.BRICK_STAIRS);
    stairs.add(Material.PURPUR_STAIRS);
    stairs.add(Material.BIRCH_WOOD_STAIRS);
    stairs.add(Material.SPRUCE_WOOD_STAIRS);
    stairs.add(Material.COBBLESTONE_STAIRS);
    stairs.add(Material.DARK_OAK_STAIRS);
    stairs.add(Material.JUNGLE_WOOD_STAIRS);
    stairs.add(Material.NETHER_BRICK_STAIRS);
    stairs.add(Material.QUARTZ_STAIRS);
    stairs.add(Material.RED_SANDSTONE_STAIRS);
    stairs.add(Material.WOOD_STAIRS);
    return stairs;
  }
}
