package me.darkwinged.ChatChannels.commands;

import me.darkwinged.ChatChannels.Main;
import me.darkwinged.ChatChannels.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdChatfilter implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chatfilter")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player)sender;
            if (player.hasPermission("ChatChannels.filter") || player.hasPermission("ChatChannels.*")) {
                if (plugin.users.getUserChatFilter(player)) {
                    plugin.users.setUserChatFilter(player, false);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("message-filter-off")));
                    return true;
                }
                plugin.users.setUserChatFilter(player, true);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("message-filter-on")));
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
