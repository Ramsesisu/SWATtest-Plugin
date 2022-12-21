package me.rqmses.swattest.global;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static me.rqmses.swattest.SWATtest.admins;
import static me.rqmses.swattest.SWATtest.builders;

public class Admins {

    public static Boolean isAdmin(Player player) {
        return admins.contains(player.getName());
    }

    public static Boolean isBuilder(Player player) {
        return builders.contains(player.getName());
    }

    public static void msgAdmin(String msg) {
        for (Object name : admins) {
            if (Bukkit.getServer().getPlayer((String) name) != null) {
                Bukkit.getServer().getPlayer((String) name).sendMessage(msg);
            }
        }
    }
}
