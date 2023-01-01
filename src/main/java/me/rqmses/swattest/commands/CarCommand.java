package me.rqmses.swattest.commands;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.Admins;
import me.rqmses.swattest.global.Functions;
import me.rqmses.swattest.listeners.MinecartListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.material.Rails;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

import static me.rqmses.swattest.SWATtest.commandtoggles;
import static me.rqmses.swattest.commands.FlyCommand.flyingplayers;

public class CarCommand implements CommandExecutor {

    public static final HashMap<String, Minecart> minecarts = new HashMap<>();
    public static final HashMap<String, BukkitTask> cartasks = new HashMap<>();

    public static final HashMap<String, Double> kilometer = new HashMap<>();
    public static final HashMap<String, Integer> zustand = new HashMap<>();
    public static final HashMap<String, Double> tacho = new HashMap<>();
    public static final HashMap<String, Double> tank = new HashMap<>();
    public static final HashMap<String, Integer> gang = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (commandtoggles.get(command.getName()) || Admins.isAdmin(((Player) sender).getPlayer())) {
                Player player = (Player) sender;
                minecarts.putIfAbsent(player.getName(), null);
                if (args.length == 0) {
                    if (minecarts.get(player.getName()) == null) {
                        Minecart minecart = player.getWorld().spawn(player.getLocation(), Minecart.class);
                        minecart.setCustomName(player.getName());
                        minecart.setMaxSpeed(Double.MAX_VALUE);
                        minecarts.put(player.getName(), minecart);
                        tank.put(player.getName(), 100.0);
                        player.sendMessage(ChatColor.GRAY + "Du hast dein Auto gesetzt.");
                    } else {
                        minecarts.get(player.getName()).remove();
                        minecarts.put(player.getName(), null);
                        minecarts.remove(player.getName());
                        player.sendMessage(ChatColor.GRAY + "Du hast dein Auto gelöscht.");
                    }
                }

                if (args.length >= 1) {
                    switch (args[0]) {
                        case "find":
                            if (minecarts.get(player.getName()) != null) {
                                player.chat("/warp " + minecarts.get(player.getName()).getLocation().getBlockX() + "/" + minecarts.get(player.getName()).getLocation().getBlockY() + "/" + minecarts.get(player.getName()).getLocation().getBlockZ());
                            }
                            break;
                        case "start":
                            if (MinecartListener.minecartplayerslist.contains(player.getName())) {
                                Minecart car = minecarts.get(player.getName());
                                cartasks.putIfAbsent(player.getName(), null);
                                if (cartasks.get(player.getName()) != null) {
                                    cartasks.get(player.getName()).cancel();
                                    cartasks.put(player.getName(), null);
                                }

                                BukkitRunnable cartask = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        Block carblock = car.getLocation().add(0, 1, 0).getBlock();
                                        if (carblock.getType() == Material.AIR && car.getLocation().getBlock() instanceof Rails) {
                                            carblock.setType(Material.RAILS);
                                            carblock.setType(Material.AIR);
                                        }

                                        if (gang.get(player.getName()) == 0) {
                                            tacho.put(player.getName(), tacho.get(player.getName()) + 0.1);

                                            if (tacho.get(player.getName()) >= 1) {
                                                gang.put(player.getName(), 1);
                                            }
                                        }
                                        if (gang.get(player.getName()) == 1) {
                                            tacho.put(player.getName(), tacho.get(player.getName()) + 0.2);

                                            if (tacho.get(player.getName()) >= 5) {
                                                gang.put(player.getName(), 2);
                                            }
                                        }
                                        if (gang.get(player.getName()) == 2) {
                                            tacho.put(player.getName(), tacho.get(player.getName()) + 0.4);

                                            if (tacho.get(player.getName()) >= 15) {
                                                gang.put(player.getName(), 3);
                                            }
                                        }
                                        if (gang.get(player.getName()) == 3) {
                                            tacho.put(player.getName(), tacho.get(player.getName()) + 0.8);

                                            if (tacho.get(player.getName()) >= 30) {
                                                gang.put(player.getName(), 4);
                                            }
                                        }
                                        if (gang.get(player.getName()) == 4) {
                                            tacho.put(player.getName(), tacho.get(player.getName()) + 1.6);

                                            if (tacho.get(player.getName()) >= 60) {
                                                gang.put(player.getName(), 5);
                                            }
                                        }
                                        if (gang.get(player.getName()) == 5) {
                                            tacho.put(player.getName(), tacho.get(player.getName()) + 3.2);

                                            if (tacho.get(player.getName()) >= 120) {
                                                gang.put(player.getName(), 6);
                                            }
                                        }
                                        if (gang.get(player.getName()) == 6) {
                                            tacho.put(player.getName(), tacho.get(player.getName()) + 6.4);

                                            if (tacho.get(player.getName()) >= 167) {
                                                tacho.put(player.getName(), 167.0);
                                            }
                                        }

                                        if (player.getLocation().getPitch() == 90) {
                                            cancel();
                                        }
                                        if (player.getLocation().getPitch() < 0) {
                                            if (!flyingplayers.contains(player)) {
                                                cancel();
                                            }
                                        }
                                        float pitch = Math.abs(player.getLocation().getPitch());
                                        if (pitch < 15) {
                                            pitch = 10;
                                        }
                                        double speed = tacho.get(player.getName()) / (pitch * 10);
                                        car.setVelocity(player.getLocation().getDirection().multiply(1 + speed));
                                        kilometer.put(player.getName(), kilometer.get(player.getName()) + 0.002);
                                        tank.put(player.getName(), tank.get(player.getName()) - 0.002);
                                        Functions.setScoreBoard(player, "car");
                                    }
                                };
                                cartasks.put(player.getName(), cartask.runTaskTimer(SWATtest.plugin, 0L, 0L));

                                kilometer.putIfAbsent(player.getName(), 0.0);
                                zustand.putIfAbsent(player.getName(), 1500);
                                tacho.put(player.getName(), 0.0);
                                tank.putIfAbsent(player.getName(), 100.0);
                                gang.put(player.getName(), 0);

                                if (args.length == 1) {
                                    player.sendMessage(ChatColor.GRAY + "Du hast dein Auto gestartet.");
                                }
                            }
                            break;
                        default:
                            player.sendMessage(ChatColor.DARK_GRAY + args[0] + ChatColor.GRAY + " ist kein gültiges Argument.");
                            break;
                    }
                }
            }
        }
        if (!commandtoggles.get(command.getName())) {
            sender.sendMessage("Dieser Befehl ist deaktiviert!");
        }
        return true;
    }
}
