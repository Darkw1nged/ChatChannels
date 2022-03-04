package me.darkwinged.ChatChannels.commands;

import me.darkwinged.ChatChannels.Main;
import me.darkwinged.ChatChannels.libaries.Channel;
import me.darkwinged.ChatChannels.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class cmdChat implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chat")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player)sender;
            if (args.length < 1) {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("chat-join-usage")));
                return true;
            }
            if (args[0].equalsIgnoreCase("join")) {
                if (args.length != 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("chat-join-usage")));
                    return true;
                }

                Channel currentChannel = null;
                for (Channel foundChannel : Utils.channels) {
                    if (plugin.users.getPlayerChannel(player).equals(foundChannel.getChannelName())) {
                        currentChannel = foundChannel;
                        break;
                    }
                }

                if (currentChannel != null) {
                    List<Player> players = currentChannel.getPlayers();
                    players.remove(player);
                    currentChannel.setPlayers(players);
                }
                for (Channel toJoin : Utils.channels) {
                    if (toJoin.getChannelName().equalsIgnoreCase(args[1])) {
                        List<Player> players = toJoin.getPlayers();
                        players.add(player);
                        toJoin.setPlayers(players);

                        plugin.users.setUserChannel(player, toJoin);

                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("successfully-joined-chat")
                                .replaceAll("%channel%", toJoin.getChannelName())));
                        return true;
                    }
                }
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-channel-exists")));

            } else if (args[0].equalsIgnoreCase("leave")) {
                Channel currentChannel = null;
                for (Channel foundChannel : Utils.channels) {
                    if (plugin.users.getPlayerChannel(player).equals(foundChannel.getChannelName())) {
                        currentChannel = foundChannel;
                        break;
                    }
                }

                Channel globalChannel = Utils.channels.get(0);

                if (currentChannel == null) return true;
                if (currentChannel.getChannelName().equals("Global")) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cannot-leave-global")));
                    return true;
                }

                List<Player> toRemove = currentChannel.getPlayers();
                toRemove.remove(player);
                currentChannel.setPlayers(toRemove);
                plugin.users.setUserChannel(player, globalChannel);

                List<Player> toAdd = globalChannel.getPlayers();
                toAdd.add(player);
                globalChannel.setPlayers(toAdd);

                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("successfully-left-chat")
                        .replaceAll("%channel%", currentChannel.getChannelName())));
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("chat-join-usage")));
            }
        }
        return false;
    }

}
