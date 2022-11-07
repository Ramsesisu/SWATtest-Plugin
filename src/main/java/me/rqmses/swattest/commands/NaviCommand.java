package me.rqmses.swattest.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.rqmses.swattest.SWATtest.plugin;

public class NaviCommand implements CommandExecutor, TabCompleter {

    public static final HashMap<String, BukkitTask> navitask = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0 ) {
                if (navitask.get(player.getName()) == null) {
                    player.sendMessage(ChatColor.YELLOW + "Du musst ein Ziel angeben!");
                } else {
                    navitask.get(player.getName()).cancel();
                    player.sendMessage(ChatColor.YELLOW + "Du hast dein Navi gelöscht!");
                    navitask.put(player.getName(), null);
                }
            } else {
                Location loc;
                switch (args[0].toLowerCase()) {
                    case "stadthalle":
                        loc = new Location(Bukkit.getWorld("world"), 103, 69, 157);
                        break;
                    case "uranberg":
                        loc = new Location(Bukkit.getWorld("world"), -392, 194, 761);
                        break;
                    case "x3":
                        loc = new Location(Bukkit.getWorld("world"), 128, 71, 219);
                        break;
                    case "x3-oben":
                        loc = new Location(Bukkit.getWorld("world"), 139, 160, 230);
                        break;
                    case "261":
                        loc = new Location(Bukkit.getWorld("world"), 810, 75, 94);
                        break;
                    case "248":
                        loc = new Location(Bukkit.getWorld("world"), 8, 74, -564);
                        break;
                    case "kf-kran":
                        loc = new Location(Bukkit.getWorld("world"), 972, 130, 204);
                        break;
                    case "lu-kran":
                        loc = new Location(Bukkit.getWorld("world"), 1562, 206, 330);
                        break;
                    case "flug-1":
                        loc = new Location(Bukkit.getWorld("world"), 1562, 206, 330);
                        break;
                    case "flug-2":
                        loc = new Location(Bukkit.getWorld("world"), 1154, 80, -199);
                        break;
                    case "flug-3":
                        loc = new Location(Bukkit.getWorld("world"), 70, 95, -448);
                        break;
                    case "flug-4":
                        loc = new Location(Bukkit.getWorld("world"), -274, 76, -397);
                        break;
                    case "psychiatrie":
                        loc = new Location(Bukkit.getWorld("world"), 1631, 68, -413);
                        break;
                    case "golfplatz":
                        loc = new Location(Bukkit.getWorld("world"), 1301, 70, -291);
                        break;
                    case "funpark":
                        loc = new Location(Bukkit.getWorld("world"), 1405, 69, -104);
                        break;
                    case "staatsbank":
                        loc = new Location(Bukkit.getWorld("world"), 1453, 71, 133);
                        break;
                    case "lu-baustelle":
                        loc = new Location(Bukkit.getWorld("world"), 1557, 146, 307);
                        break;
                    case "schule":
                        loc = new Location(Bukkit.getWorld("world"), 1667, 70, 514);
                        break;
                    case "lu-casino":
                        loc = new Location(Bukkit.getWorld("world"), 1445, 69, 240);
                        break;
                    case "fightclub":
                        loc = new Location(Bukkit.getWorld("world"), 1594, 69, 679);
                        break;
                    case "schwimmbad":
                        loc = new Location(Bukkit.getWorld("world"), 1661, 69, 297);
                        break;
                    case "atomkraftwerk":
                        loc = new Location(Bukkit.getWorld("world"), 1145, 68, 402);
                        break;
                    case "643,5":
                        loc = new Location(Bukkit.getWorld("world"), 782, 69, 517);
                        break;
                    case "obrien":
                        loc = new Location(Bukkit.getWorld("world"), 706, 71, 581);
                        break;
                    case "kf-bar":
                        loc = new Location(Bukkit.getWorld("world"), 777, 69, 285);
                        break;
                    case "kerzakov":
                        loc = new Location(Bukkit.getWorld("world"), 877, 68, 203);
                        break;
                    case "rotlichtbar":
                        loc = new Location(Bukkit.getWorld("world"), 794, 69, 29);
                        break;
                    case "fbi":
                        loc = new Location(Bukkit.getWorld("world"), 867, 69, -51);
                        break;
                    case "triaden":
                        loc = new Location(Bukkit.getWorld("world"), 1004, 69, -101);
                        break;
                    case "chinatown":
                        loc = new Location(Bukkit.getWorld("world"), 1079, 69, -162);
                        break;
                    case "hrt":
                        loc = new Location(Bukkit.getWorld("world"), 1042, 69, -285);
                        break;
                    case "uc17":
                        loc = new Location(Bukkit.getWorld("world"), 615, 69, 107);
                        break;
                    case "insel":
                        loc = new Location(Bukkit.getWorld("world"), 720, 68, -499);
                        break;
                    case "swat":
                        loc = new Location(Bukkit.getWorld("world"), 403, 69, -75);
                        break;
                    case "mall":
                        loc = new Location(Bukkit.getWorld("world"), 474, 69, 80);
                        break;
                    case "farm":
                        loc = new Location(Bukkit.getWorld("world"), 482, 66, 533);
                        break;
                    case "altstadt":
                        loc = new Location(Bukkit.getWorld("world"), 248, 69, 691);
                        break;
                    case "labor":
                        loc = new Location(Bukkit.getWorld("world"), 0, 69, 609);
                        break;
                    case "weinberg":
                        loc = new Location(Bukkit.getWorld("world"), 14, 91, 557);
                        break;
                    case "tabakplantage":
                        loc = new Location(Bukkit.getWorld("world"), 400, 64, 634);
                        break;
                    case "müllhalde":
                        loc = new Location(Bukkit.getWorld("world"), 844, 69, 365);
                        break;
                    case "eisenstollen":
                        loc = new Location(Bukkit.getWorld("world"), 1054, 69, 336);
                        break;
                    case "krankenhaus":
                        loc = new Location(Bukkit.getWorld("world"), 289, 69, 238);
                        break;
                    case "maklerbüro":
                        loc = new Location(Bukkit.getWorld("world"), 163, 69, 304);
                        break;
                    case "park":
                        loc = new Location(Bukkit.getWorld("world"), 114, 69, 327);
                        break;
                    case "sh-kran":
                        loc = new Location(Bukkit.getWorld("world"), 70, 152, 216);
                        break;
                    case "cherrys":
                        loc = new Location(Bukkit.getWorld("world"), 39, 69, 395);
                        break;
                    case "cafe":
                        loc = new Location(Bukkit.getWorld("world"), -50, 69, 321);
                        break;
                    case "bar":
                        loc = new Location(Bukkit.getWorld("world"), -47, 69, 193);
                        break;
                    case "windrad-mex":
                        loc = new Location(Bukkit.getWorld("world"), 35, 135, -197);
                        break;
                    case "hausaddon-shop":
                        loc = new Location(Bukkit.getWorld("world"), 94, 69, -200);
                        break;
                    case "lasertag":
                        loc = new Location(Bukkit.getWorld("world"), 94, 71, -275);
                        break;
                    case "news":
                        loc = new Location(Bukkit.getWorld("world"), -90, 69, -350);
                        break;
                    case "feuerwehr":
                        loc = new Location(Bukkit.getWorld("world"), -117, 69, -248);
                        break;
                    case "314":
                        loc = new Location(Bukkit.getWorld("world"), -187, 69, -252);
                        break;
                    case "kartell":
                        loc = new Location(Bukkit.getWorld("world"), -284, 69, -123);
                        break;
                    case "disko":
                        loc = new Location(Bukkit.getWorld("world"), -236, 69, -1);
                        break;
                    case "strand-mex":
                        loc = new Location(Bukkit.getWorld("world"), -500, 67, -242);
                        break;
                    case "militärbasis":
                        loc = new Location(Bukkit.getWorld("world"), -464, 69, -524);
                        break;
                    case "freibad":
                        loc = new Location(Bukkit.getWorld("world"), -272, 69, -485);
                        break;
                    case "polizei":
                        loc = new Location(Bukkit.getWorld("world"), -219, 71, -473);
                        break;
                    case "mafia":
                        loc = new Location(Bukkit.getWorld("world"), -1, 69, -463);
                        break;
                    case "yachthafen":
                        loc = new Location(Bukkit.getWorld("world"), 284, 69, -505);
                        break;
                    case "luxus-autohändler":
                        loc = new Location(Bukkit.getWorld("world"), -169, 69, -528);
                        break;
                    case "waffenladen-murica":
                        loc = new Location(Bukkit.getWorld("world"), -69, 69, -397);
                        break;
                    case "tankstelle-polizei":
                        loc = new Location(Bukkit.getWorld("world"), -169, 69, -356);
                        break;
                    case "waffenfabrik":
                        loc = new Location(Bukkit.getWorld("world"), -223, 69, -437);
                        break;
                    case "papierfabrik":
                        loc = new Location(Bukkit.getWorld("world"), -242, 69, -393);
                        break;
                    case "hitman":
                        loc = new Location(Bukkit.getWorld("world"), 316, 69, 59);
                        break;
                    case "luigis":
                        loc = new Location(Bukkit.getWorld("world"), 252, 69, 58);
                        break;
                    case "apotheke-casino":
                        loc = new Location(Bukkit.getWorld("world"), 267, 69, 22);
                        break;
                    case "casino":
                        loc = new Location(Bukkit.getWorld("world"), 255, 69, -11);
                        break;
                    case "kirche":
                        loc = new Location(Bukkit.getWorld("world"), 308, 72, -101);
                        break;
                    case "gemeindehaus":
                        loc = new Location(Bukkit.getWorld("world"), 261, 69, -106);
                        break;
                    case "hausaddon":
                        loc = new Location(Bukkit.getWorld("world"), 238, 69, -158);
                        break;
                    case "autohändler":
                        loc = new Location(Bukkit.getWorld("world"), 167, 69, -215);
                        break;
                    case "le-mileu":
                        loc = new Location(Bukkit.getWorld("world"), 286, 69, -247);
                        break;
                    case "asservatenkammer":
                        loc = new Location(Bukkit.getWorld("world"), 122, 69, -256);
                        break;
                    case "apotheke-zentrale":
                        loc = new Location(Bukkit.getWorld("world"), -91, 71, -394);
                        break;
                    case "feinkostladen":
                        loc = new Location(Bukkit.getWorld("world"), -94, 69, -331);
                        break;
                    case "fleischer":
                        loc = new Location(Bukkit.getWorld("world"), -150, 69, -325);
                        break;
                    case "wendys":
                        loc = new Location(Bukkit.getWorld("world"), -182, 69, -427);
                        break;
                    case "shishabar":
                        loc = new Location(Bukkit.getWorld("world"), -144, 69, -69);
                        break;
                    case "tankstelle-mex":
                        loc = new Location(Bukkit.getWorld("world"), -157, 69, -3);
                        break;
                    case "chickenfightclub":
                        loc = new Location(Bukkit.getWorld("world"), -366, 69, -113);
                        break;
                    case "tankstelle-chinatown":
                        loc = new Location(Bukkit.getWorld("world"), 1095, 69, -187);
                        break;
                    case "windrad-chinatown":
                        loc = new Location(Bukkit.getWorld("world"), 896, 128, 21);
                        break;
                    case "fischerhütte":
                        loc = new Location(Bukkit.getWorld("world"), -119, 69, 370);
                        break;
                    case "westside-ballas":
                        loc = new Location(Bukkit.getWorld("world"), -161, 69, 201);
                        break;
                    case "mechaniker":
                        loc = new Location(Bukkit.getWorld("world"), -153, 69, 259);
                        break;
                    case "waffenladen-ballas":
                        loc = new Location(Bukkit.getWorld("world"), -178, 69, 236);
                        break;
                    case "basketball":
                        loc = new Location(Bukkit.getWorld("world"), -279, 69, 329);
                        break;
                    case "cfk":
                        loc = new Location(Bukkit.getWorld("world"), -325, 69, 348);
                        break;
                    case "kran-uran":
                        loc = new Location(Bukkit.getWorld("world"), -377, 109, 443);
                        break;
                    case "neulingshotel":
                        loc = new Location(Bukkit.getWorld("world"), -368, 69, 530);
                        break;
                    case "flughafen-unica":
                        loc = new Location(Bukkit.getWorld("world"), -368, 69, 530);
                        break;
                    case "flughafen-chinatown":
                        loc = new Location(Bukkit.getWorld("world"), 1256, 69, 45);
                        break;
                    case "flughafen-lasunicas":
                        loc = new Location(Bukkit.getWorld("world"), 1679, 69, 475);
                        break;
                    case "urantransport":
                        loc = new Location(Bukkit.getWorld("world"), -501, 66, 682);
                        break;
                    case "deathmatch-arena":
                        loc = new Location(Bukkit.getWorld("world"), -502, 69, 337);
                        break;
                    case "gefängnis":
                        loc = new Location(Bukkit.getWorld("world"), -634, 69, 222);
                        break;
                    case "hochseefischer":
                        loc = new Location(Bukkit.getWorld("world"), -503, 63, 199);
                        break;
                    case "feuerwerksladen":
                        loc = new Location(Bukkit.getWorld("world"), -275, 70, 129);
                        break;
                    case "angelschein":
                        loc = new Location(Bukkit.getWorld("world"), -345, 63, 67);
                        break;
                    case "terroristen":
                        loc = new Location(Bukkit.getWorld("world"), -53, 69, 490);
                        break;
                    case "sägewerk":
                        loc = new Location(Bukkit.getWorld("world"), 428, 64, 425);
                        break;
                    case "200":
                        loc = new Location(Bukkit.getWorld("world"), 620, 73, 156);
                        break;
                    case "363":
                        loc = new Location(Bukkit.getWorld("world"), 75, 69, 256);
                        break;
                    case "531":
                        loc = new Location(Bukkit.getWorld("world"), 43, 105, 251);
                        break;
                    case "bäckerei":
                        loc = new Location(Bukkit.getWorld("world"), 223, 69, 327);
                        break;
                    case "shop":
                        loc = new Location(Bukkit.getWorld("world"), 45, 69, 197);
                        break;
                    case "windrad-fbi":
                        loc = new Location(Bukkit.getWorld("world"), 897, 128, 21);
                        break;
                    case "ucm":
                        loc = new Location(Bukkit.getWorld("world"), 1154, 69, -186);
                        break;
                    case "anwaltskanzelei":
                        loc = new Location(Bukkit.getWorld("world"), -134, 71, -500);
                        break;
                    default:
                        player.sendMessage(ChatColor.GOLD + args[0] + ChatColor.YELLOW + " ist kein gültiges Ziel!");
                        return true;
                }

                BukkitRunnable navi = new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location origin = player.getLocation();
                        Vector direction = loc.clone().subtract(player.getLocation()).toVector();

                        direction.normalize();
                        for (int i = 0; i < 20 * 20; i++) {
                            Location temploc = origin.add(direction);
                            player.spawnParticle(Particle.REDSTONE, temploc.subtract(direction.clone().multiply(0.75)), 1, 0.05, 0.05, 0.05, 0);
                        }
                    }
                };
                player.sendMessage(ChatColor.YELLOW + "Dir wird nun ein Navi zu " + ChatColor.GOLD + args[0] + ChatColor.YELLOW + " angezeigt!");

                if (navitask.get(player.getName()) != null) {
                    navitask.get(player.getName()).cancel();
                    navitask.put(player.getName(), null);
                }

                navitask.put(player.getName(), navi.runTaskTimer(plugin, 0, 5L));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String[] targets = new String[] {"Stadthalle", "Uranberg", "X3", "261", "248", "KF-Kran", "LU-Kran", "LU-Baustelle",
                "Flug-1", "Flug-2", "Flug-3", "Flug-4", "Psychiatrie", "Golfplatz", "Funpark", "Staatsbank", "Schule", "LU-Casino",
                "Fightclub", "Schwimmbad", "Atomkraftwerk", "643,5", "OBrien", "KF-Bar", "Kerzakov", "Rotlichbar", "FBI",
                "Triaden", "Chinatown", "HRT", "UC17", "Insel", "SWAT", "Mall", "Farm", "Altstadt", "Labor", "Weinberg",
                "Tabakplantage", "Müllhalde", "Eisenstollen", "Krankenhaus", "Maklerbüro", "Park", "SH-Kran", "Cherrys",
                "Cafe", "Bar", "Windrad-Mex", "Hausaddon-Shop", "Lasertag", "News", "Feuerwehr", "314", "Kartell", "Disko",
                "Strand-Mex", "Militärbasis", "Freibad", "Polizei", "Mafia", "Yachthafen", "Luxus-Autohändler",
                "Waffenladen-Murica", "Tankestelle-Polizei", "Waffenfabrik", "Papierfabrik", "Hitman", "Luigis", "Apotheke-Casino",
                "Casino", "Kirche", "Gemeindehaus", "Tankstelle-Hausaddon", "Autohändler", "Le-Mileu", "Asservatenkammer",
                "Apotheke-Zentrale", "Feinkostladen", "Fleischer", "Wendys", "Shishabar", "Tankstelle-Mex", "Chickenfightclub",
                "Tankstelle-Chinatown", "Windrad-Chinatown", "Fischerhütte", "Westside-Ballas", "Mechaniker", "Waffenladen-Ballas",
                "Basketball", "CFK", "Kran-Uran", "Neulingshotel", "Flughafen-Unica", "Flughafen-Chinatown", "Flughafen-LasUnicas",
                "Urantransport", "Deathmatch-Arena", "Gefängnis", "Hochseefischer", "Feuerwerksladen", "Angelschein", "Terroristen",
                "Sägewerk", "200", "363", "531", "Bäckerei", "Shop", "Windrad-FBI", "UCM", "X3-Oben", "Anwaltskanzelei"};
        if (args.length == 1) {
            for (String target : targets) {
                if (target.toUpperCase().startsWith(args[0].toUpperCase())) {
                    list.add(target);
                }
            }
        }
        return list;
    }
}
