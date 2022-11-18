package me.rqmses.swattest.global;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.rqmses.swattest.SWATtest;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NPCs {

    public static ArrayList<NPC> NPCList = new ArrayList<NPC>();
    static NPC npc;

    public static void spawnNPC(Player player, String name) {
        npc = SWATtest.registry.createNPC(EntityType.PLAYER, ChatColor.GRAY + name+"-KI");
        NPCs.NPCList.add(npc);

        npc.spawn(player.getLocation());

        String[] skin = getSkin(player, name);
        Player npcplayer = (Player) npc.getEntity();
        ((CraftPlayer) npcplayer).getProfile().getProperties().put("textures", new Property("textures", skin[0], skin[1]));
        npcplayer.setGameMode(GameMode.SURVIVAL);
        npc.setProtected(false);
    }

    private static String[] getSkin(Player player, String name) {
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

    public static List<NPC> getNPCs() {
        return NPCList;
    }
}