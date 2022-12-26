package me.rqmses.swattest.global;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TextUtils {

    public static TextComponent getAccept() {
        TextComponent message = new TextComponent (ChatColor.GRAY + "» " + ChatColor.GREEN + "Annehmen");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.GREEN + "/annehmen")));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/annehmen"));
        return message;
    }

    public static TextComponent getDecline() {
        TextComponent message = new TextComponent (ChatColor.GRAY + "» " + ChatColor.RED + "Ablehnen");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.RED + "/ablehnen")));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ablehnen"));
        return message;
    }
}
