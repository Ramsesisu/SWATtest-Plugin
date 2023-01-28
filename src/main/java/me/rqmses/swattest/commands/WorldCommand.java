package me.rqmses.swattest.commands;

import me.rqmses.swattest.global.Admins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.rqmses.swattest.SWATtest.commandtoggles;

public class WorldCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                Player player = (Player) sender;

                if (args.length != 0) {
                    String arg;
                    switch (args[0].toLowerCase()) {
                        case "create":
                            arg = "create";
                            break;
                        case "copy":
                            arg = "copy";
                            break;
                        case "teleport":
                            arg = "teleport";
                            break;
                        case "list":
                            player.sendMessage(ChatColor.DARK_GREEN + "Welten:");
                            for (World world : Bukkit.getWorlds()) {
                                if (!world.getName().contains("world")) {
                                    player.sendMessage(ChatColor.GREEN + world.getName());
                                }
                            }
                            return true;
                        case "info":
                            player.sendMessage(ChatColor.GREEN + "Aktuelle Welt: " + ChatColor.DARK_GREEN + player.getWorld().getName());
                            return true;
                        default:
                            player.sendMessage(ChatColor.DARK_GREEN + args[0] + ChatColor.GREEN + " ist kein gültiges Argument!");
                            return true;
                    }
                    if (args.length != 1) {
                        if (Admins.isAdmin(player)) {
                            if (arg.equals("create")) {
                                new WorldCreator(args[1]).createWorld().save();
                                player.sendMessage(ChatColor.GREEN + "Du hast die Welt " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " erstellt.");
                            }
                            if (arg.equals("copy")) {
                                copyWorld(player.getWorld(), args[1]);
                                player.sendMessage(ChatColor.GREEN + "Du hast die Welt " + ChatColor.DARK_GREEN + player.getWorld().getName() + ChatColor.GREEN + " zu " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " kopiert.");
                            }
                            if (arg.equals("teleport")) {
                                if (args.length > 2) {
                                    player = Bukkit.getServer().getPlayer(args[2]);
                                    if (player == null) {
                                        sender.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.DARK_GREEN + args[2] + ChatColor.GREEN + " wurde nicht gefunden!");
                                        return true;
                                    }
                                }
                                World world = Bukkit.getWorld(args[1]);
                                if (world == null) {
                                    sender.sendMessage(ChatColor.GREEN + "Die Welt " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " wurde nicht gefunden!");
                                    return true;
                                }
                                player.teleport(world.getSpawnLocation());
                            }
                            Admins.msgAdmin(ChatColor.DARK_RED + sender.getName() + ChatColor.RED + " benutzt " + ChatColor.DARK_RED + "/world " + args[0] + " " + args[1] + ChatColor.RED + ".");
                        } else {
                            player.sendMessage("Du bist kein Admin!");
                        }
                    } else {
                        player.sendMessage(ChatColor.GREEN + "Du musst einen Welt-Namen angeben!");
                    }
                } else {
                    player.sendMessage(ChatColor.GREEN + "Du musst ein Argument angeben!");
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        List<String> targets = new ArrayList<>();
        if (args.length == 1) {
            targets = Arrays.asList("teleport", "create", "list", "copy", "info");
        }
        if (args.length == 2) {
            targets = Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList());
        }

        for (String target : targets) {
            if (target.toUpperCase().startsWith(args[0].toUpperCase()))
                list.add(target);
        }
        return list;
    }

    private static void copyFileStructure(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
                        if (!target.mkdirs())
                            throw new IOException("Couldn't create world directory!");
                    String[] files = source.list();
                    assert files != null;
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyFileStructure(srcFile, destFile);
                    }
                } else {
                    InputStream in = Files.newInputStream(source.toPath());
                    OutputStream out = Files.newOutputStream(target.toPath());
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyWorld(World originalWorld, String newWorldName) {
        copyFileStructure(originalWorld.getWorldFolder(), new File(Bukkit.getWorldContainer(), newWorldName));
        new WorldCreator(newWorldName).createWorld();
    }
}
