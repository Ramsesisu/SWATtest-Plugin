package me.rqmses.swattest.commands;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor, TabCompleter {
  public final HashMap<String, Long> cooldowns = new HashMap<>();
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player = (Player)sender;
    if (args.length == 2 && 
      sender.isOp())
      player = Bukkit.getPlayer(args[1]); 
    if (args.length == 0) {
      sender.sendMessage(ChatColor.AQUA + "Du musst ein Team angeben!");
    } else {
      int cooldownTime = 60;
      if (this.cooldowns.containsKey(player.getName())) {
        long secondsLeft = this.cooldowns.get(player.getName()) / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
        if (secondsLeft > 0L) {
          player.sendMessage(ChatColor.AQUA + "Du kannst dein Team erst in " + ChatColor.DARK_AQUA + secondsLeft + " Sekunden" + ChatColor.AQUA + " wieder wechseln.");
          return true;
        } 
      } 
      this.cooldowns.put(player.getName(), System.currentTimeMillis());
      switch (args[0].toLowerCase()) {
        case "1":
          player.setCustomName(ChatColor.RED + player.getDisplayName());
          player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.RED + player.getDisplayName()));
          changeName(ChatColor.RED + player.getDisplayName(), player);
          player.sendMessage(ChatColor.AQUA + "Du bist zu " + ChatColor.RED + "Team " + args[0] + ChatColor.AQUA + " gewechselt.");
          return true;
        case "2":
          player.setCustomName(ChatColor.BLUE + player.getDisplayName());
          player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.BLUE + player.getDisplayName()));
          changeName(ChatColor.BLUE + player.getDisplayName(), player);
          player.sendMessage(ChatColor.AQUA + "Du bist zu " + ChatColor.BLUE + "Team " + args[0] + ChatColor.AQUA + " gewechselt.");
          return true;
        case "3":
          player.setCustomName(ChatColor.GREEN + player.getDisplayName());
          player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.GREEN + player.getDisplayName()));
          changeName(ChatColor.GREEN + player.getDisplayName(), player);
          player.sendMessage(ChatColor.AQUA + "Du bist zu " + ChatColor.GREEN + "Team " + args[0] + ChatColor.AQUA + " gewechselt.");
          return true;
        case "4":
          player.setCustomName(ChatColor.GOLD + player.getDisplayName());
          player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.GOLD + player.getDisplayName()));
          changeName(ChatColor.GOLD + player.getDisplayName(), player);
          player.sendMessage(ChatColor.AQUA + "Du bist zu " + ChatColor.GOLD + "Team " + args[0] + ChatColor.AQUA + " gewechselt.");
          return true;
        case "none":
          player.setCustomName(player.getDisplayName());
          player.setPlayerListName(player.getPlayerListName().replace(player.getDisplayName(), ChatColor.WHITE + player.getDisplayName()));
          changeName(player.getDisplayName(), player);
          player.sendMessage(ChatColor.AQUA + "Du hast dein Team verlassen.");
          return true;
      } 
      player.sendMessage(ChatColor.DARK_AQUA + args[0] + ChatColor.AQUA + " ist kein gTeam!");
      return true;
    } 
    return true;
  }
  
  public static void changeName(String name, Player player) {
    try {
      //noinspection rawtypes
      Method getHandle = player.getClass().getMethod("getHandle", (Class[])null);
      try {
        Class.forName("com.mojang.authlib.GameProfile");
      } catch (ClassNotFoundException e) {
        return;
      } 
      Object profile = getHandle.invoke(player).getClass().getMethod("getProfile").invoke(getHandle.invoke(player));
      Field ff = profile.getClass().getDeclaredField("name");
      ff.setAccessible(true);
      ff.set(profile, name);
    } catch (NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|NoSuchFieldException e) {
      e.printStackTrace();
    } 
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    ArrayList<String> list = new ArrayList<>();
    String[] targets = { "1", "2", "3", "4", "None" };
    if (args.length == 1)
      for (String target : targets) {
        if (target.toUpperCase().startsWith(args[0].toUpperCase()))
          list.add(target); 
      }  
    return list;
  }
}
