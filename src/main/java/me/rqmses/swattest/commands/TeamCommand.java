package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.haoshoku.nick.api.NickAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.rqmses.swattest.SWATtest.*;

public class TeamCommand implements CommandExecutor, TabCompleter {

  public static final HashMap<String, Long> cooldowns = new HashMap<>();

  public static Integer kills1 = 0;
  public static Integer kills2 = 0;
  public static Integer kills3 = 0;
  public static Integer kills4 = 0;

  public static String teamname1 = "Team-1";
  public static String teamname2 = "Team-2";
  public static String teamname3 = "Team-3";
  public static String teamname4 = "Team-4";

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player = (Player) sender;
    if (args.length == 2 && 
      sender.isOp())
      player = Bukkit.getPlayer(args[1]); 
    if (args.length == 0) {
      sender.sendMessage(ChatColor.AQUA + "Du musst ein Team angeben!");
    } else {
      int cooldownTime = 60;
      if (cooldowns.containsKey(player.getName())) {
        long secondsLeft = cooldowns.get(player.getName()) / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
        if (secondsLeft > 0L) {
          player.sendMessage(ChatColor.AQUA + "Du kannst dein Team erst in " + ChatColor.DARK_AQUA + secondsLeft + " Sekunden" + ChatColor.AQUA + " wieder wechseln.");
          return true;
        } 
      } 
      cooldowns.put(player.getName(), System.currentTimeMillis());

      Functions.getTeam(player).removeEntry(player.getName());

      String finalPlayerName = player.getName();
      BukkitRunnable scoreboardRunnable = new BukkitRunnable() {
        @Override
        public void run() {
          if (Bukkit.getServer().getPlayer(finalPlayerName) == null) {
            cancel();
          } else if (team0.getEntries().contains(Bukkit.getServer().getPlayer(finalPlayerName).getName())) {
            Bukkit.getServer().getPlayer(finalPlayerName).setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            cancel();
          } else {
            Functions.setScoreBoard(Bukkit.getServer().getPlayer(finalPlayerName));
          }
        }
      };
      if (args[0].equalsIgnoreCase(teamname1)) {
        player.setCustomName(ChatColor.RED + player.getDisplayName());
        player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.RED + player.getDisplayName()));
        team1.addEntry(player.getName());
        NickAPI.nick(player, ChatColor.RED + player.getName());
        NickAPI.refreshPlayer(player);
        scoreboardRunnable.runTaskTimer(plugin, 20L, 0L);
        player.sendMessage(ChatColor.AQUA + "Du bist zu " + ChatColor.RED + args[0] + ChatColor.AQUA + " gewechselt.");
      } else if (args[0].equalsIgnoreCase(teamname2)) {
        player.setCustomName(ChatColor.BLUE + player.getDisplayName());
        player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.BLUE + player.getDisplayName()));
        team2.addEntry(player.getName());
        NickAPI.nick(player, ChatColor.BLUE + player.getName());
        NickAPI.refreshPlayer(player);
        scoreboardRunnable.runTaskTimer(plugin, 20L, 0L);
        player.sendMessage(ChatColor.AQUA + "Du bist zu " + ChatColor.BLUE + args[0] + ChatColor.AQUA + " gewechselt.");
      } else if (args[0].equalsIgnoreCase(teamname3)) {
        player.setCustomName(ChatColor.GREEN + player.getDisplayName());
        player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.GREEN + player.getDisplayName()));
        team3.addEntry(player.getName());
        NickAPI.nick(player, ChatColor.GREEN + player.getName());
        NickAPI.refreshPlayer(player);
        scoreboardRunnable.runTaskTimer(plugin, 20L, 0L);
        player.sendMessage(ChatColor.AQUA + "Du bist zu " + ChatColor.GREEN + args[0] + ChatColor.AQUA + " gewechselt.");
      } else if (args[0].equalsIgnoreCase(teamname4)) {
        player.setCustomName(ChatColor.GOLD + player.getDisplayName());
        player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.GOLD + player.getDisplayName()));
        team4.addEntry(player.getName());
        NickAPI.nick(player, ChatColor.GOLD + player.getName());
        NickAPI.refreshPlayer(player);
        scoreboardRunnable.runTaskTimer(plugin, 20L, 0L);
        player.sendMessage(ChatColor.AQUA + "Du bist zu " + ChatColor.GOLD + args[0] + ChatColor.AQUA + " gewechselt.");
      } else if (args[0].equalsIgnoreCase("none")) {
        player.setCustomName(player.getDisplayName());
        player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.WHITE + player.getDisplayName()));
        team0.addEntry(player.getName());
        player.sendMessage(ChatColor.AQUA + "Du hast dein Team verlassen.");
      } else {
        player.setCustomName(player.getDisplayName());
        player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.WHITE + player.getDisplayName()));
        team0.addEntry(player.getName());
        player.sendMessage(ChatColor.DARK_AQUA + args[0] + ChatColor.AQUA + " ist kein Team!");
      }
    } 
    return true;
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    ArrayList<String> list = new ArrayList<>();
    String[] targets = { teamname1, teamname2, teamname3, teamname4, "None" };
    if (args.length == 1)
      for (String target : targets) {
        if (target.toUpperCase().startsWith(args[0].toUpperCase()))
          list.add(target); 
      }  
    return list;
  }
}
