package me.rqmses.swattest.global;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.rqmses.swattest.commands.EquipCommand;
import me.rqmses.swattest.listeners.PlayerJoinListener;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Functions {
  public static void createFile(Player player) {
    try {
      ((File) PlayerJoinListener.playersafe.get(player.getUniqueId())).createNewFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } 
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("elytra.0", Items.getElytra().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("elytra.1", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("elytra.2", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("elytra.3", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("elytra.4", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("elytra.5", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("elytra.6", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("elytra.7", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("elytra.8", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("swat.0", Items.getSchild().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("swat.1", Items.getKev((short)30).getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("swat.2", Items.getM4().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("swat.3", Items.getSniper().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("swat.4", Items.getFlashes().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("swat.5", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("swat.6", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("swat.7", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("swat.8", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("polizei.0", Items.getKev((short)50).getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("polizei.1", Items.getM4().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("polizei.2", Items.getMp5().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("polizei.3", Items.getTazer().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("polizei.4", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("polizei.5", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("polizei.6", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("polizei.7", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("polizei.8", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("ballas.0", Items.getKev((short)50).getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("ballas.1", Items.getM4().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("ballas.2", Items.getJagdflinte().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("ballas.3", Items.getMesser().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("ballas.4", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("ballas.5", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("ballas.6", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("ballas.7", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("ballas.8", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("terror.0", Items.getKev((short)50).getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("terror.1", Items.getM4().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("terror.2", Items.getRPG().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("terror.3", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("terror.4", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("terror.5", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("terror.6", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("terror.7", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("terror.8", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("zivilist.0", Items.getMp5().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("zivilist.1", Items.getMesser().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("zivilist.2", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("zivilist.3", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("zivilist.4", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("zivilist.5", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("zivilist.6", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("zivilist.7", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("zivilist.8", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("flammenwerfer.0", Items.getKev((short)50).getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("flammenwerfer.1", Items.getFlammenwerfer().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("flammenwerfer.2", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("flammenwerfer.3", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("flammenwerfer.4", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("flammenwerfer.5", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("flammenwerfer.6", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("flammenwerfer.7", Items.getAir().getType().toString());
    ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).set("flammenwerfer.8", Items.getAir().getType().toString());
    try {
      ((FileConfiguration) PlayerJoinListener.playerconfig.get(player.getUniqueId())).save((File) PlayerJoinListener.playersafe.get(player.getUniqueId()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } 
  }
  
  public static void equipPlayer(Player player) {
    EquipCommand.cooldowns.put(player.getName(), Long.valueOf(0L));
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "elytra")) {
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&3Elytra&8&l] &r") + player.getName());
      player.chat("/equip Elytra");
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "swat")) {
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&1SWAT&8&l] &r") + player.getName());
      player.chat("/equip SWAT");
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "polizei")) {
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&9UCPD&8&l] &r") + player.getName());
      player.chat("/equip Polizei");
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "ballas")) {
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&5Ballas&8&l] &r") + player.getName());
      player.chat("/equip Ballas");
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "terror")) {
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&eTerror&8&l] &r") + player.getName());
      player.chat("/equip Terror");
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "zivilist")) {
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&7Zivi&8&l] &r") + player.getName());
      player.chat("/equip Zivilist");
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "flammenwerfer")) {
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&cFlammi&8&l] &r") + player.getName());
      player.chat("/equip Flammenwerfer");
    } 
    EquipCommand.cooldowns.put(player.getName(), Long.valueOf(0L));
  }

  public static String[] getSkin(Player player, String name) {
    try {
      URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
      InputStreamReader reader = new InputStreamReader(url.openStream());
      String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

      URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
      InputStreamReader reader2 = new InputStreamReader(url2.openStream());
      JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

      String texture = property.get("value").getAsString();
      String signature = property.get("signature").getAsString();
      return new String[] {texture, signature};
    } catch (Exception e) {
      EntityPlayer p = ((CraftPlayer) player).getHandle();
      GameProfile profile = p.getProfile();
      Property property = profile.getProperties().get("textures").iterator().next();
      String texture = property.getValue();
      String signature = property.getSignature();
      return new String[] {texture, signature};
    }
  }

}
