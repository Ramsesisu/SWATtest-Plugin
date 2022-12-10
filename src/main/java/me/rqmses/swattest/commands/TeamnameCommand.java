package me.rqmses.swattest.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.rqmses.swattest.commands.TeamCommand.*;

public class TeamnameCommand implements CommandExecutor, TabCompleter {

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player = (Player) sender;
    if (player.isOp()) {
      if (args.length != 2) {
        player.sendMessage(ChatColor.AQUA + "Du musst ein Team angeben!");
        return true;
      }
      if (args[0].equalsIgnoreCase(teamname1)) {
        teamname1 = args[1];
        player.sendMessage(ChatColor.AQUA + "Du hast den Namen von " + ChatColor.RED + args[0] + ChatColor.AQUA + " zu " + ChatColor.RED + args[1] + ChatColor.AQUA + " geändert.");
      } else if (args[0].equalsIgnoreCase(teamname2)) {
        teamname2 = args[1];
        player.sendMessage(ChatColor.AQUA + "Du hast den Namen von " + ChatColor.BLUE + args[0] + ChatColor.AQUA + " zu " + ChatColor.BLUE + args[1] + ChatColor.AQUA + " geändert.");
      } else if (args[0].equalsIgnoreCase(teamname3)) {
        teamname3 = args[1];
        player.sendMessage(ChatColor.AQUA + "Du hast den Namen von " + ChatColor.GREEN + args[0] + ChatColor.AQUA + " zu " + ChatColor.GREEN + args[1] + ChatColor.AQUA + " geändert.");
      } else if (args[0].equalsIgnoreCase(teamname4)) {
        teamname4 = args[1];
        player.sendMessage(ChatColor.AQUA + "Du hast den Namen von " + ChatColor.GOLD + args[0] + ChatColor.AQUA + " zu " + ChatColor.GOLD + args[1] + ChatColor.AQUA + " geändert.");
      } else {
        player.sendMessage(ChatColor.DARK_AQUA + args[0] + ChatColor.AQUA + " ist kein Team!");
      }
    } else {
      player.sendMessage("Du bist kein OP!");
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
