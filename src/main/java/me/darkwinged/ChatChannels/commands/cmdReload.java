package me.darkwinged.ChatChannels.commands;

import me.darkwinged.ChatChannels.Main;
import me.darkwinged.ChatChannels.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdReload  implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chatchannel")) {
            if (sender.hasPermission("ChatChannels.Reload") || sender.hasPermission("ChatChannels.*")) {
                plugin.reloadConfig();
                plugin.fileMessage.reloadConfig();
                plugin.fileChannels.reloadConfig();

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("admin-reload")));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
