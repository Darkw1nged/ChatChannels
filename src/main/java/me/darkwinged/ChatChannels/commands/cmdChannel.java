package me.darkwinged.ChatChannels.commands;

import me.darkwinged.ChatChannels.Main;
import me.darkwinged.ChatChannels.libaries.Channel;
import me.darkwinged.ChatChannels.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class cmdChannel implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("channel")) {
            if (sender.hasPermission("ChatChannels.modify") || sender.hasPermission("ChatChannels.*")) {
                if (args.length < 2) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("channel-usage")));
                    return true;
                }
                String channelName = args[1];

                if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("add")) {
                    if (args.length < 3) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("channel-usage")));
                        return true;
                    }
                    if (plugin.fileChannels.getConfig().getConfigurationSection("Channels") != null &&
                            plugin.fileChannels.getConfig().getConfigurationSection("Channels").contains(channelName)) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("channel-exists")));
                        return true;
                    }

                    int range;
                    try {
                        range = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-range")));
                        return true;
                    }
                    plugin.fileChannels.getConfig().set("Channels." + channelName + ".range", range);
                    plugin.fileChannels.saveConfig();

                    Utils.channels.add(new Channel(
                            channelName,
                            plugin.fileChannels.getConfig().getInt("Channels." + channelName + ".range"),
                            new ArrayList<>()
                    ));

                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("channel-created"))
                            .replaceAll("%channel%", channelName));

                } else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove")) {
                    if (!plugin.fileChannels.getConfig().getConfigurationSection("Channels").contains(channelName)) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-channel-exists")));
                        return true;
                    }
                    plugin.fileChannels.getConfig().set("Channels." + channelName, null);
                    plugin.fileChannels.saveConfig();
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("channel-removed"))
                            .replaceAll("%channel%", channelName));

                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("channel-usage")));
                    return true;
                }

            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                return true;
            }
        }
        return false;
    }

}
