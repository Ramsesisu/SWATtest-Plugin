package me.rqmses.swattest.global;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.rqmses.swattest.commands.EquipCommand;
import me.rqmses.swattest.listeners.PlayerJoinListener;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

import static me.rqmses.swattest.SWATtest.*;
import static me.rqmses.swattest.commands.TeamCommand.*;

public class Functions {
  public static void createFile(Player player) {
    try {
      //noinspection ResultOfMethodCallIgnored
      PlayerJoinListener.playersafe.get(player.getUniqueId()).createNewFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } 
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("elytra.0", Items.getElytra().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("elytra.1", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("elytra.2", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("elytra.3", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("elytra.4", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("elytra.5", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("elytra.6", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("elytra.7", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("elytra.8", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("swat.0", Items.getSchild().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("swat.1", Items.getKev((short)30).getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("swat.2", Items.getM4().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("swat.3", Items.getSniper().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("swat.4", Items.getFlashes().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("swat.5", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("swat.6", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("swat.7", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("swat.8", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("polizei.0", Items.getKev((short)50).getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("polizei.1", Items.getM4().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("polizei.2", Items.getMp5().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("polizei.3", Items.getTazer().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("polizei.4", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("polizei.5", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("polizei.6", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("polizei.7", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("polizei.8", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("ballas.0", Items.getKev((short)50).getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("ballas.1", Items.getM4().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("ballas.2", Items.getJagdflinte().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("ballas.3", Items.getMesser().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("ballas.4", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("ballas.5", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("ballas.6", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("ballas.7", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("ballas.8", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("terror.0", Items.getKev((short)50).getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("terror.1", Items.getM4().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("terror.2", Items.getRPG().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("terror.3", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("terror.4", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("terror.5", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("terror.6", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("terror.7", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("terror.8", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("zivilist.0", Items.getMp5().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("zivilist.1", Items.getMesser().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("zivilist.2", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("zivilist.3", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("zivilist.4", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("zivilist.5", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("zivilist.6", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("zivilist.7", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("zivilist.8", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("flammenwerfer.0", Items.getKev((short)50).getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("flammenwerfer.1", Items.getFlammenwerfer().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("flammenwerfer.2", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("flammenwerfer.3", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("flammenwerfer.4", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("flammenwerfer.5", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("flammenwerfer.6", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("flammenwerfer.7", Items.getAir().getType().toString());
    PlayerJoinListener.playerconfig.get(player.getUniqueId()).set("flammenwerfer.8", Items.getAir().getType().toString());
    try {
      PlayerJoinListener.playerconfig.get(player.getUniqueId()).save(PlayerJoinListener.playersafe.get(player.getUniqueId()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } 
  }
  
  public static void equipPlayer(Player player) {
    String fly = "";
    if (player.getPlayerListName().endsWith(" F"))
      fly = ChatColor.translateAlternateColorCodes('&', "&b&l F");
    String playername = Functions.getTeam(player).getColor() + player.getName();
    EquipCommand.cooldowns.put(player.getName(), 0L);
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "elytra")) {
      player.chat("/equip Elytra " + player.getName() + " f");
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&3Elytra&8&l] &r") + playername + fly);
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "swat")) {
      player.chat("/equip SWAT " + player.getName() + " f");
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&1SWAT&8&l] &r") + playername + fly);
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "polizei")) {
      player.chat("/equip Polizei " + player.getName() + " f");
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&9UCPD&8&l] &r") + playername + fly);
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "ballas")) {
      player.chat("/equip Ballas " + player.getName() + " f");
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&5Ballas&8&l] &r") + playername + fly);
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "terror")) {
      player.chat("/equip Terror " + player.getName() + " f");
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&eTerror&8&l] &r") + playername + fly);
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "zivilist")) {
      player.chat("/equip Zivilist " + player.getName() + " f");
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&7Zivi&8&l] &r") + playername + fly);
    } 
    if (Objects.equals(EquipCommand.playerequip.get(player.getName()), "flammenwerfer")) {
      player.chat("/equip Flammenwerfer " + player.getName() + " f");
      player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&8&l[&cFlammi&8&l] &r") + playername + fly);
    } 
    EquipCommand.cooldowns.put(player.getName(), 0L);
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

  public static Team getTeam(Player player) {
    if (player.hasMetadata("NPC")) {
      return team0;
    }
    Team playerteam = team0;
    if (team1.getEntries().contains(player.getName())) {
      playerteam = team1;
    }
    if (team2.getEntries().contains(player.getName())) {
      playerteam = team2;
    }
    if (team3.getEntries().contains(player.getName())) {
      playerteam = team3;
    }
    if (team4.getEntries().contains(player.getName())) {
      playerteam = team4;
    }
    return playerteam;
  }

  public static void setScoreBoard(Player player) {
    ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

    Objective objective = scoreboard.registerNewObjective(ChatColor.BOLD + "HowToSWAT", "dummy");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    if (team1.getSize() > 0) {
      Score score1 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&c&l" + teamname1));
      score1.setScore(21);
      Score score11 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "➝&c&r&7&o " + team1.getSize() + " Spieler"));
      score11.setScore(20);
      Score score12 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "➝&c&r&7&o " + kills1 + " Kills"));
      score12.setScore(19);
    }

    if (team2.getSize() > 0) {
      Score score2 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&9&l" + teamname2));
      score2.setScore(18);
      Score score21 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "➝&9&r&7&o " + team2.getSize() + " Spieler"));
      score21.setScore(17);
      Score score22 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "➝&9&r&7&o " + kills2 + " Kills"));
      score22.setScore(16);
    }

    if (team3.getSize() > 0) {
      Score score3 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&a&l" + teamname3));
      score3.setScore(15);
      Score score31 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "➝&a&r&7&o " + team3.getSize() + " Spieler"));
      score31.setScore(14);
      Score score32 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "➝&a&r&7&o " + kills3 + " Kills"));
      score32.setScore(13);
    }

    if (team4.getSize() > 0) {
      Score score4 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&6&l" + teamname4));
      score4.setScore(12);
      Score score41 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "➝&6&r&7&o " + team4.getSize() + " Spieler"));
      score41.setScore(11);
      Score score42 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "➝&6&r&7&o " + kills4 + " Kills"));
      score42.setScore(10);
    }

    player.setScoreboard(scoreboard);
  }
}
