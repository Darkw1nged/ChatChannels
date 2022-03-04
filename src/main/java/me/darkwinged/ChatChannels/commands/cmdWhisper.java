package me.darkwinged.ChatChannels.commands;

import me.darkwinged.ChatChannels.Main;
import me.darkwinged.ChatChannels.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdWhisper implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("whisper")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("ChatChannels.whisper") || player.hasPermission("ChatChannels.*")) {
                if (args.length < 1) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-message")));
                    return true;
                }
                StringBuilder message = new StringBuilder();
                for (String s : args) {
                    message.append(" ").append(s);
                }
                Utils.sendLocalPlayerMessage(player, plugin.getConfig().getInt("whisper-channel-range"), message.toString());
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }
}
