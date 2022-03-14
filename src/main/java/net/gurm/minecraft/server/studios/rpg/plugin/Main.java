package net.gurm.minecraft.server.studios.rpg.plugin;

import net.gurm.minecraft.server.studios.rpg.plugin.chat.ChatBubbles;
import net.gurm.minecraft.server.studios.rpg.plugin.chat.ChatBuffer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Main extends JavaPlugin implements Listener {
    private boolean disableChatWindow;
    private ChatBuffer buffer;
    public ChatBubbles bubbles;

    @Override
    public void onEnable() {
        getLogger().info("RPGPlugin [✔]");
        disableChatWindow = getConfig().getBoolean("disableChatWindow");

        saveDefaultConfig();
        bubbles = new ChatBubbles(this);
        buffer = new ChatBuffer(this);

        Bukkit.getPluginManager().registerEvents(this, this);

    }






    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (!e.isCancelled())
        {
            buffer.receiveChat(e.getPlayer(), e.getMessage());

            if(disableChatWindow)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        if (Bukkit.getServer().hasWhitelist()) {
            e.setMotd("           §d§lGurm Studios §b§l서버 §8/ §c§l[1.12 ~ 1.18]\n            §e§l공식 커뮤니티 : §6gurm-studios.net\n");

        } else {
            e.setMotd("           §d§lGurm Studios §b§l서버 §8/ §c§l[1.12 ~ 1.18]\n            §e§l공식 커뮤니티 : §6gurm-studios.net");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity().getPlayer();
        e.setDeathMessage(null);
    }


    // e.setJoinMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "사람이 접속했다.");
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "" + p.getDisplayName() + "" + ChatColor.YELLOW + "" + ChatColor.BOLD + "님이 접속했습니다.");
        e.getPlayer().setResourcePack("");
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.setQuitMessage(null);
    }

    @Override
    public void onDisable() { getLogger().info("RPGPlugin [❌]"); }
}
