package net.gurm.minecraft.server.studios.rpg.plugin.chat;

import net.gurm.minecraft.server.studios.rpg.plugin.Main;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;

import java.util.ArrayList;

public class ChatBubbles {
    private int handicapChars;
    private int readSpeed;
    private String chatFormat;

    public ChatBubbles(Main plugin)
    {
        handicapChars = plugin.getConfig().getInt("handicapChars");
        readSpeed = plugin.getConfig().getInt("readSpeed");
        chatFormat = plugin.getConfig().getString("chatFormat").replaceAll("(.)", "\u00A7$1");
    }


    int receiveMessage(Player player, String playerId, String chat)
    {
        // prepare chat message and empty bubble
        String[] chatLines = chat.split("\n");
        new ArrayList<LivingEntity>();

        int duration = (chat.length()+(handicapChars*chatLines.length))*1200/readSpeed;
        Location spawnPoint = player.getLocation();
        spawnPoint.setY(-1);

        Entity vehicle = player;
        for (int i = chatLines.length -1 ; i >= 0 ; --i)
            vehicle = spawnNameTag(vehicle, chatLines[i], spawnPoint, duration);
        return duration;
    }

    private AreaEffectCloud spawnNameTag(Entity vehicle, String text, Location spawnPoint, int duration)
    {

        AreaEffectCloud nameTag = (AreaEffectCloud) spawnPoint.getWorld().spawnEntity(spawnPoint, EntityType.AREA_EFFECT_CLOUD);
        nameTag.setParticle(Particle.TOWN_AURA);
        nameTag.setRadius(0);

        vehicle.addPassenger(nameTag);
        nameTag.setCustomName(chatFormat+text);
        nameTag.setCustomNameVisible(true);

        nameTag.setWaitTime(0);
        nameTag.setDuration(duration);
        return nameTag;
    }
}
