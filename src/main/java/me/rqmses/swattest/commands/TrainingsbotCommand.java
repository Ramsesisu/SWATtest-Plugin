package me.rqmses.swattest.commands;

import com.mojang.authlib.properties.Property;
import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.Functions;
import me.rqmses.swattest.global.npctraits.AttackTrait;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TrainingsbotCommand implements CommandExecutor {

    public static net.citizensnpcs.api.npc.NPC npc;

    public static final ArrayList<NPC> NPCList = new ArrayList<>();

    public static final HashMap<String, ArrayList<NPC>> NPCListPlayer = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            NPCListPlayer.putIfAbsent(sender.getName(), new ArrayList<>());
            if (NPCListPlayer.get(sender.getName()).size() >= 3) {
                if (!sender.isOp()) {
                    sender.sendMessage(ChatColor.AQUA + "Dein Limit an Bots wurde bereits erreicht.");
                    return true;
                } else if (args.length <= 1) {
                    sender.sendMessage(ChatColor.AQUA + "Dein Limit an Bots wurde bereits erreicht.");
                    return true;
                }
            }
            if (Objects.equals(((Player) sender).getCustomName(), "dead")) {
                return true;
            }
            String name;
            String ki = "-KI";
            if (args.length == 0) {
                name = sender.getName();
            } else {
                name = args[0];
            }
            if (args.length == 2) {
                if (sender.isOp()) {
                    name = args[0];
                    ki = "";
                }
            }
            Player player = (Player) sender;

            npc = SWATtest.registry.createNPC(EntityType.PLAYER, name + ki);
            NPCList.add(npc);

            npc.data().set("origin", player.getName());
            NPCListPlayer.get(player.getName()).add(npc);

            npc.addTrait(new AttackTrait());

            npc.spawn(player.getLocation());

            String[] skin = Functions.getSkin(player, name);
            Player npcplayer = (Player) npc.getEntity();
            ((CraftPlayer) npcplayer).getProfile().getProperties().put("textures", new Property("textures", skin[0], skin[1]));

            ((Player) sender).getPlayer().sendMessage(ChatColor.AQUA + "Du hast eine Trainings-KI erschaffen!");
        }
        return true;
    }
}
