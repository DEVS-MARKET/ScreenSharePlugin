package eu.devsmarket.screenshare.utils;

import org.bukkit.entity.Player;

public class MessageUtil {
    public static void sendMessageToPlayer(String message, Player player){
        player.sendMessage(formatTextColor("&8[&bScreenShare&8] &7" + message));
    }

    public static String formatTextColor(String string){
        return string.replace("&", "§");
    }

}
