package me.darkwinged.ChatChannels.commands;

import me.darkwinged.ChatChannels.Main;
import me.darkwinged.ChatChannels.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdShout implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("shout")) {
            if (sender.hasPermission("ChatChannels.shout") || sender.hasPermission("ChatChannels.*")) {
                if (args.length < 1) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-message")));
                    return true;
                }
                StringBuilder message = new StringBuilder();
                for (String s : args) {
                    message.append(" ").append(s);
                }
                String name = sender instanceof Player ? ((Player) sender).getDisplayName() : sender.getName();
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("shout-prefix") +
                            plugin.getConfig().getString("chat-format").replaceAll("%player%", name)
                            .replaceAll("%message%", message.toString())));
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
