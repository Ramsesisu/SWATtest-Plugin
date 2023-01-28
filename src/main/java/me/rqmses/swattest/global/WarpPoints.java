package me.rqmses.swattest.global;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

import static me.rqmses.swattest.commands.BlockanfragenCommand.blockedplayers;
import static me.rqmses.swattest.commands.VanishCommand.hidden;

public class WarpPoints {
    public static Location getWarp(String warp, Player player, String type) {
        Location loc = null;
        switch (warp.toLowerCase()) {
            case "sh":
            case "stadthalle":
                loc = new Location(player.getWorld(), 103, 69, 157);
                break;
            case "uran":
            case "uranberg":
                loc = new Location(player.getWorld(), -392, 194, 761);
                break;
            case "würfelpark":
                loc = new Location(player.getWorld(), 139, 69, 221);
                break;
            case "261":
                loc = new Location(player.getWorld(), 810, 75, 94);
                break;
            case "248":
                loc = new Location(player.getWorld(), 8, 74, -564);
                break;
            case "kf-kran":
                loc = new Location(player.getWorld(), 972, 130, 204);
                break;
            case "lu-kran":
                loc = new Location(player.getWorld(), 1562, 206, 330);
                break;
            case "musikladen":
                loc = new Location(player.getWorld(), -38, 117, 234);
                break;
            case "psychiatrie":
                loc = new Location(player.getWorld(), 1631, 68, -413);
                break;
            case "golfplatz":
                loc = new Location(player.getWorld(), 1301, 70, -291);
                break;
            case "funpark":
                loc = new Location(player.getWorld(), 1405, 69, -104);
                break;
            case "staatsbank":
                loc = new Location(player.getWorld(), 1453, 71, 133);
                break;
            case "lu-baustelle":
                loc = new Location(player.getWorld(), 1557, 146, 307);
                break;
            case "schule":
                loc = new Location(player.getWorld(), 1667, 70, 514);
                break;
            case "lu-casino":
                loc = new Location(player.getWorld(), 1445, 69, 240);
                break;
            case "fightclub":
                loc = new Location(player.getWorld(), 1594, 69, 679);
                break;
            case "schwimmbad":
                loc = new Location(player.getWorld(), 1661, 69, 297);
                break;
            case "atomkraftwerk":
                loc = new Location(player.getWorld(), 1145, 68, 402);
                break;
            case "643,5":
                loc = new Location(player.getWorld(), 782, 69, 517);
                break;
            case "obrien":
                loc = new Location(player.getWorld(), 706, 71, 581);
                break;
            case "kf-bar":
                loc = new Location(player.getWorld(), 777, 69, 285);
                break;
            case "kerzakov":
                loc = new Location(player.getWorld(), 877, 68, 203);
                break;
            case "rotlichtbar":
                loc = new Location(player.getWorld(), 794, 69, 29);
                break;
            case "fbi":
                loc = new Location(player.getWorld(), 867, 69, -51);
                break;
            case "triaden":
                loc = new Location(player.getWorld(), 1004, 69, -101);
                break;
            case "chinatown":
                loc = new Location(player.getWorld(), 1079, 69, -162);
                break;
            case "hrt":
                loc = new Location(player.getWorld(), 1042, 69, -285);
                break;
            case "uc17":
                loc = new Location(player.getWorld(), 615, 69, 107);
                break;
            case "insel":
                loc = new Location(player.getWorld(), 720, 68, -499);
                break;
            case "swat":
                loc = new Location(player.getWorld(), 403, 69, -75);
                break;
            case "mall":
                loc = new Location(player.getWorld(), 474, 69, 80);
                break;
            case "farm":
                loc = new Location(player.getWorld(), 482, 66, 533);
                break;
            case "altstadt":
                loc = new Location(player.getWorld(), 248, 69, 691);
                break;
            case "labor":
                loc = new Location(player.getWorld(), 0, 69, 609);
                break;
            case "weinberg":
                loc = new Location(player.getWorld(), 14, 91, 557);
                break;
            case "tabakplantage":
                loc = new Location(player.getWorld(), 400, 64, 634);
                break;
            case "müllhalde":
                loc = new Location(player.getWorld(), 844, 69, 365);
                break;
            case "eisenstollen":
                loc = new Location(player.getWorld(), 1054, 69, 336);
                break;
            case "kh":
            case "krankenhaus":
                loc = new Location(player.getWorld(), 289, 69, 238);
                break;
            case "maklerbüro":
                loc = new Location(player.getWorld(), 163, 69, 304);
                break;
            case "park":
                loc = new Location(player.getWorld(), 114, 69, 327);
                break;
            case "sh-kran":
                loc = new Location(player.getWorld(), 70, 152, 216);
                break;
            case "cherrys":
                loc = new Location(player.getWorld(), 39, 69, 395);
                break;
            case "cafe":
                loc = new Location(player.getWorld(), -50, 69, 321);
                break;
            case "bar":
                loc = new Location(player.getWorld(), -47, 69, 193);
                break;
            case "windrad-mex":
                loc = new Location(player.getWorld(), 35, 135, -197);
                break;
            case "hausaddon-shop":
                loc = new Location(player.getWorld(), 94, 69, -200);
                break;
            case "lasertag":
                loc = new Location(player.getWorld(), 94, 71, -275);
                break;
            case "news":
                loc = new Location(player.getWorld(), -90, 69, -350);
                break;
            case "feuerwehr":
                loc = new Location(player.getWorld(), -117, 69, -248);
                break;
            case "314":
                loc = new Location(player.getWorld(), -187, 69, -252);
                break;
            case "kartell":
                loc = new Location(player.getWorld(), -284, 69, -123);
                break;
            case "disko":
                loc = new Location(player.getWorld(), -236, 69, -1);
                break;
            case "strand-mex":
                loc = new Location(player.getWorld(), -500, 67, -242);
                break;
            case "militärbasis":
                loc = new Location(player.getWorld(), -464, 69, -524);
                break;
            case "freibad":
                loc = new Location(player.getWorld(), -272, 69, -485);
                break;
            case "polizei":
                loc = new Location(player.getWorld(), -219, 71, -473);
                break;
            case "mafia":
                loc = new Location(player.getWorld(), -1, 69, -463);
                break;
            case "yachthafen":
                loc = new Location(player.getWorld(), 284, 69, -505);
                break;
            case "luxus-autohändler":
                loc = new Location(player.getWorld(), -169, 69, -528);
                break;
            case "waffenladen-murica":
                loc = new Location(player.getWorld(), -69, 69, -397);
                break;
            case "tankstelle-polizei":
                loc = new Location(player.getWorld(), -169, 69, -356);
                break;
            case "waffenfabrik":
                loc = new Location(player.getWorld(), -223, 69, -437);
                break;
            case "papierfabrik":
                loc = new Location(player.getWorld(), -242, 69, -393);
                break;
            case "hitman":
                loc = new Location(player.getWorld(), 316, 69, 59);
                break;
            case "luigis":
                loc = new Location(player.getWorld(), 252, 69, 58);
                break;
            case "apotheke-casino":
                loc = new Location(player.getWorld(), 267, 69, 22);
                break;
            case "casino":
                loc = new Location(player.getWorld(), 255, 69, -11);
                break;
            case "kirche":
                loc = new Location(player.getWorld(), 308, 72, -101);
                break;
            case "gemeindehaus":
                loc = new Location(player.getWorld(), 261, 69, -106);
                break;
            case "hausaddon":
                loc = new Location(player.getWorld(), 238, 69, -158);
                break;
            case "autohändler":
                loc = new Location(player.getWorld(), 167, 69, -215);
                break;
            case "mileu":
            case "le-mileu":
                loc = new Location(player.getWorld(), 286, 69, -247);
                break;
            case "asservatenkammer":
                loc = new Location(player.getWorld(), 122, 69, -256);
                break;
            case "apotheke-zentrale":
                loc = new Location(player.getWorld(), -91, 71, -394);
                break;
            case "feinkostladen":
                loc = new Location(player.getWorld(), -94, 69, -331);
                break;
            case "fleischer":
                loc = new Location(player.getWorld(), -150, 69, -325);
                break;
            case "wendys":
                loc = new Location(player.getWorld(), -182, 69, -427);
                break;
            case "shishabar":
                loc = new Location(player.getWorld(), -144, 69, -69);
                break;
            case "tankstelle-mex":
                loc = new Location(player.getWorld(), -157, 69, -3);
                break;
            case "chickenfightclub":
                loc = new Location(player.getWorld(), -366, 69, -113);
                break;
            case "tankstelle-chinatown":
                loc = new Location(player.getWorld(), 1095, 69, -187);
                break;
            case "windrad-chinatown":
                loc = new Location(player.getWorld(), 896, 128, 21);
                break;
            case "fischerhütte":
                loc = new Location(player.getWorld(), -119, 69, 370);
                break;
            case "ballas":
            case "westside-ballas":
                loc = new Location(player.getWorld(), -161, 69, 201);
                break;
            case "mechaniker":
                loc = new Location(player.getWorld(), -153, 69, 259);
                break;
            case "waffenladen-ballas":
                loc = new Location(player.getWorld(), -178, 69, 236);
                break;
            case "basketball":
                loc = new Location(player.getWorld(), -279, 69, 329);
                break;
            case "cfk":
                loc = new Location(player.getWorld(), -325, 69, 348);
                break;
            case "kran-uran":
                loc = new Location(player.getWorld(), -377, 109, 443);
                break;
            case "neulingshotel":
                loc = new Location(player.getWorld(), -368, 69, 530);
                break;
            case "flughafen-unica":
                loc = new Location(player.getWorld(), -264, 69, 631);
                break;
            case "flughafen-chinatown":
                loc = new Location(player.getWorld(), 1256, 69, 45);
                break;
            case "flughafen-lasunicas":
                loc = new Location(player.getWorld(), 1679, 69, 475);
                break;
            case "urantransport":
                loc = new Location(player.getWorld(), -501, 66, 682);
                break;
            case "deathmatch-arena":
                loc = new Location(player.getWorld(), -502, 69, 337);
                break;
            case "gefängnis":
                loc = new Location(player.getWorld(), -634, 69, 222);
                break;
            case "hochseefischer":
                loc = new Location(player.getWorld(), -503, 63, 199);
                break;
            case "feuerwerksladen":
                loc = new Location(player.getWorld(), -275, 70, 129);
                break;
            case "angelschein":
                loc = new Location(player.getWorld(), -345, 63, 67);
                break;
            case "terroristen":
                loc = new Location(player.getWorld(), -53, 69, 490);
                break;
            case "sägewerk":
                loc = new Location(player.getWorld(), 428, 64, 425);
                break;
            case "200":
                loc = new Location(player.getWorld(), 620, 73, 156);
                break;
            case "363":
                loc = new Location(player.getWorld(), 75, 69, 256);
                break;
            case "531":
                loc = new Location(player.getWorld(), 43, 105, 251);
                break;
            case "bäckerei":
                loc = new Location(player.getWorld(), 223, 69, 327);
                break;
            case "shop":
                loc = new Location(player.getWorld(), 45, 69, 197);
                break;
            case "windrad-fbi":
                loc = new Location(player.getWorld(), 897, 128, 21);
                break;
            case "ucm":
                loc = new Location(player.getWorld(), 1154, 69, -186);
                break;
            case "altes-gefängnis":
                loc = new Location(player.getWorld(), 117, 37, 218);
                break;
            case "hölle":
                loc = new Location(player.getWorld(), 345, 9, -106);
                break;
            case "himmel":
                loc = new Location(player.getWorld(), 647, 230, -621);
                break;
            case "checkpoint-gefängnis":
                loc = new Location(player.getWorld(), -638, 9, 144);
                break;
            case "anwaltskanzelei":
                loc = new Location(player.getWorld(), -134, 71, -500);
                break;
            case "alcatraz":
                loc = new Location(player.getWorld(), 1183, 75, 685);
                break;
            case "36":
                loc = new Location(player.getWorld(), -157, 69, -21);
                break;
            case "144":
                loc = new Location(player.getWorld(), -382, 69, -267);
                break;
            case "171":
                loc = new Location(player.getWorld(), -81, 74, 284);
                break;
            case "190":
                loc = new Location(player.getWorld(), 590, 69, 206);
                break;
            case "382":
                loc = new Location(player.getWorld(), -61, 69, -566);
                break;
            case "stadion":
                loc = new Location(player.getWorld(), 1558, 69, 48);
                break;
            case "433":
                loc = new Location(player.getWorld(), -450, 69, -279);
                break;
            case "metzgerei":
                loc = new Location(player.getWorld(), -145, 69, -325);
                break;
            case "las-unicas":
                loc = new Location(player.getWorld(), 1509, 69, 305);
                break;
            case "ct-shop":
                loc = new Location(player.getWorld(), 1062, 69, -188);
                break;
            case "278":
                loc = new Location(player.getWorld(), -538, 70, 338);
                break;
            case "280":
                loc = new Location(player.getWorld(), -548, 70, 308);
                break;
            case "523":
                loc = new Location(player.getWorld(), 693, 69, -19);
                break;
            case "121":
                loc = new Location(player.getWorld(), -250, 69, 281);
                break;
            default:
                String[] coords = warp.split("/");
                if (coords.length == 3) {
                    loc = new Location(player.getWorld(), Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
                } else if (Bukkit.getServer().getPlayer(warp) != null) {
                    if (hidden.contains(Bukkit.getServer().getPlayer(warp)) && Bukkit.getServer().getPlayer(warp) != player) {
                        player.sendMessage(ChatColor.GOLD + warp + ChatColor.YELLOW + " ist kein gültiges Ziel!");
                    } else {
                        if (type.equals("warp")) {
                            Player target = Bukkit.getServer().getPlayer(warp);
                            player.sendMessage(ChatColor.YELLOW + "Du hast eine Warp-Anfrage an " + ChatColor.GOLD + target.getName() + ChatColor.YELLOW + " geschickt!");

                            blockedplayers.putIfAbsent(target.getName(), new ArrayList<>());
                            if (!blockedplayers.get(target.getName()).contains(player.getName().toLowerCase())) {
                                target.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " hat dir eine Warp-Anfrage geschickt!");
                                target.spigot().sendMessage(TextUtils.getAccept());
                                target.spigot().sendMessage(TextUtils.getDecline());

                                BukkitRunnable anfrage = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (Functions.choice.get(target.getName())) {
                                            if (Bukkit.getServer().getPlayer(player.getName()) != null) {
                                                player.teleport(target.getLocation());
                                                player.sendMessage(ChatColor.YELLOW + "Die Warp-Anfrage an " + ChatColor.GOLD + target.getName() + ChatColor.YELLOW + " wurde angenommen!");
                                            }
                                        }
                                    }
                                };
                                Functions.accepttask.put(target.getName(), anfrage);
                            }
                        } else {
                            loc = Bukkit.getServer().getPlayer(warp).getLocation();
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.GOLD + warp + ChatColor.YELLOW + " ist kein gültiges Ziel!");
                }
        }
        return loc;
    }

    public static String[] getTargets(Player player) {
        String[] targets = new String[] {"Stadthalle", "Uranberg", "Würfelpark", "261", "248", "KF-Kran", "LU-Kran",
                "LU-Baustelle", "Psychiatrie", "Golfplatz", "Funpark", "Staatsbank", "Schule", "LU-Casino", "Fightclub",
                "Schwimmbad", "Atomkraftwerk", "643,5", "OBrien", "KF-Bar", "Kerzakov", "Rotlichtbar", "FBI", "Triaden",
                "Chinatown", "HRT", "UC17", "Insel", "SWAT", "Mall", "Farm", "Altstadt", "Labor", "Weinberg",
                "Tabakplantage", "Müllhalde", "Eisenstollen", "Krankenhaus", "Maklerbüro", "Park", "SH-Kran", "Cherrys",
                "Cafe", "Bar", "Windrad-Mex", "Hausaddon-Shop", "Lasertag", "News", "Feuerwehr", "314", "Kartell", "Disko",
                "Strand-Mex", "Militärbasis", "Freibad", "Polizei", "Mafia", "Yachthafen", "Luxus-Autohändler",
                "Waffenladen-Murica", "Tankestelle-Polizei", "Waffenfabrik", "Papierfabrik", "Hitman", "Luigis", "Apotheke-Casino",
                "Casino", "Kirche", "Gemeindehaus", "Tankstelle-Hausaddon", "Autohändler", "Le-Mileu", "Asservatenkammer",
                "Apotheke-Zentrale", "Feinkostladen", "Fleischer", "Wendys", "Shishabar", "Tankstelle-Mex", "Chickenfightclub",
                "Tankstelle-Chinatown", "Windrad-Chinatown", "Fischerhütte", "Westside-Ballas", "Mechaniker", "Waffenladen-Ballas",
                "Basketball", "CFK", "Kran-Uran", "Neulingshotel", "Flughafen-Unica", "Flughafen-Chinatown", "Flughafen-LasUnicas",
                "Urantransport", "Deathmatch-Arena", "Gefängnis", "Hochseefischer", "Feuerwerksladen", "Angelschein", "Terroristen",
                "Sägewerk", "200", "363", "531", "Bäckerei", "Shop", "Windrad-FBI", "UCM", "Altes-Gefängnis", "Hölle",
                "Himmel", "Checkpoint-Gefängnis", "Anwaltskanzelei", "Musikladen", "Alcatraz", "36", "144", "171", "190", "Stadion", "433",
                "382", "Metzgerei", "Las-Unicas", "CT-Shop", "Mileu", "Ballas","SH", "KH", "Uran", "278", "280", "523", "121"};
        ArrayList<String> targetsList = new ArrayList<>(Arrays.asList(targets));
        ArrayList<String> playersList = new ArrayList<>();
        for (Player tempplayer : Bukkit.getServer().getOnlinePlayers()) {
            playersList.add(tempplayer.getName());
        }
        Location loc = player.getLocation();
        targetsList.add(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ());
        targetsList.addAll(playersList);
        targets = targetsList.toArray(new String[0]);
        return targets;
    }
}
