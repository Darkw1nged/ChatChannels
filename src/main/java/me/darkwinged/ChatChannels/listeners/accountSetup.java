package me.darkwinged.ChatChannels.listeners;

import me.darkwinged.ChatChannels.Main;
import me.darkwinged.ChatChannels.libaries.Channel;
import me.darkwinged.ChatChannels.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class accountSetup implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.users.inDatabase(player)) {
            plugin.users.addUser(player);
        }

        String channelName = plugin.users.getPlayerChannel(player);
        if (channelName.equals("")) {
            List<Player> players = new ArrayList<>(Utils.channels.get(0).getPlayers());
            players.add(player);
            Utils.channels.get(0).setPlayers(players);
            return;
        }
        for (Channel channel : Utils.channels) {
            if (channel.getChannelName().equals(channelName)) {
                if (channel.getPlayers().contains(player)) return;
                List<Player> players = new ArrayList<>(channel.getPlayers());
                players.add(player);
                channel.setPlayers(players);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String channelName = plugin.users.getPlayerChannel(player);
        for (Channel channel : Utils.channels) {
            if (channel.getChannelName().equals(channelName)) {
                if (!channel.getPlayers().contains(player)) return;
                List<Player> players = new ArrayList<>(channel.getPlayers());
                players.remove(player);
                channel.setPlayers(players);
            }
        }
    }

}
