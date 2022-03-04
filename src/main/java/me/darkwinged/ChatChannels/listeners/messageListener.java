package me.darkwinged.ChatChannels.listeners;

import me.darkwinged.ChatChannels.Main;
import me.darkwinged.ChatChannels.libaries.Channel;
import me.darkwinged.ChatChannels.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class messageListener implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Channel currentChannel = null;
        for (Channel foundChannel : Utils.channels) {
            if (plugin.users.getPlayerChannel(player).equals(foundChannel.getChannelName())) {
                currentChannel = foundChannel;
                break;
            }
        }

        if (currentChannel == null) return;
        for (Player target : currentChannel.getPlayers()) {
            if (currentChannel.getRange() != -1 && !currentChannel.getChannelName().equals("Global")) {
                if (player.getWorld() == target.getWorld()) {
                    if (plugin.users.getUserChatFilter(target)) {
                        String message = event.getMessage();
                        for (String word : event.getMessage().split(" ")) {
                            if (plugin.getConfig().getStringList("world-filter").contains(word)) {
                                message = message.replaceAll(word, "****");
                            }
                        }
                        Utils.sendPlayerMessage(player, target, currentChannel.getRange()," " + message);
                        continue;
                    }
                    Utils.sendPlayerMessage(player, target, currentChannel.getRange()," " + event.getMessage());
                }
                continue;
            }
            if (plugin.users.getUserChatFilter(target)) {
                String message = event.getMessage();
                for (String word : event.getMessage().split(" ")) {
                    if (plugin.getConfig().getStringList("world-filter").contains(word)) {
                        message = message.replaceAll(word, "****");
                    }
                }
                target.sendMessage(Utils.chatColor(plugin.getConfig().getString("chat-format")
                        .replaceAll("%player%", player.getDisplayName())
                        .replaceAll("%message%", " " + message)));
                continue;
            }
            target.sendMessage(Utils.chatColor(plugin.getConfig().getString("chat-format")
                    .replaceAll("%player%", player.getDisplayName())
                    .replaceAll("%message%", " " + event.getMessage())));
        }
        event.setCancelled(true);
    }

}
