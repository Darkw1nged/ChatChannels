package me.darkwinged.ChatChannels.libaries;

import me.darkwinged.ChatChannels.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.List;
import java.util.*;

public class Utils {

    private static final Main plugin = Main.getInstance;

    // ---- [ Managing chat color within the plugin ] ----
    public static String chatColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    // ---- [ Managing chat color within the plugin | Supports Amount ] ----
    public static String chatColor(String s, Double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        String converted = nf.format(amount);
        return ChatColor.translateAlternateColorCodes('&', s)
                .replaceAll("%amount%", converted);
    }

    // ---- [ Converting a lore to include colors ] ----
    public static List<String> getConvertedLore(FileConfiguration config, String path) {
        if (config == null) return null;
        List<String> oldList = config.getStringList(path + ".lore");
        List<String> newList = new ArrayList<>();
        for (String a : oldList)
            newList.add(ChatColor.translateAlternateColorCodes('&', a));
        return newList;
    }

    // ---- [ Available space ] ----
    public static boolean hasSpace(Player player, ItemStack targetItem) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() == targetItem.getType()) {
                if (item.getAmount() != item.getMaxStackSize()) {
                    item.setAmount(item.getAmount() + 1);
                    return true;
                }
            }
        }
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(targetItem);
            return true;
        }
        return false;
    }

    // ---- [ Removes all color codes from string ] ----
    public static String replaceAllColorCodes(String string) {
        return string.replaceAll("&a", "")
                .replaceAll("&b", "")
                .replaceAll("&c", "")
                .replaceAll("&d", "")
                .replaceAll("&e", "")
                .replaceAll("&f", "")
                .replaceAll("&1", "")
                .replaceAll("&2", "")
                .replaceAll("&3", "")
                .replaceAll("&4", "")
                .replaceAll("&5", "")
                .replaceAll("&6", "")
                .replaceAll("&7", "")
                .replaceAll("&8", "")
                .replaceAll("&9", "")
                .replaceAll("&k", "")
                .replaceAll("&l", "")
                .replaceAll("&m", "")
                .replaceAll("&n", "")
                .replaceAll("&o", "")
                .replaceAll("&r", "");

    }

    // ---- [ Cached Items ] ----
    public static List<Channel> channels = new ArrayList<>();

    // ---- [ Load channels to list ] ----
    public static void loadChannels() {
        Channel channel = new Channel(
                "Global",
                -1,
                new ArrayList<>()
        );
        channels.add(channel);

        channel = new Channel(
                "Local",
                plugin.getConfig().getInt("local-channel-range"),
                new ArrayList<>()
        );
        channels.add(channel);

        if (plugin.fileChannels.getConfig().getConfigurationSection("Channels") == null) return;
        for (String key : plugin.fileChannels.getConfig().getConfigurationSection("Channels").getKeys(false)) {
            channel = new Channel(
                key,
                plugin.fileChannels.getConfig().getInt("Channels." + key + ".range"),
                new ArrayList<>()
            );
            channels.add(channel);
        }
    }

    // ---- [ Getting all players within a certain distance of another ] ----
    public static void sendPlayerMessage(Player messageSender, Player target, int range, String message) {
        if (target.getLocation().distance(messageSender.getLocation()) <= range) {
            target.sendMessage(Utils.chatColor(plugin.getConfig().getString("chat-format")
                    .replaceAll("%player%", messageSender.getDisplayName())
                    .replaceAll("%message%", message)));
        }
    }

    public static void sendLocalPlayerMessage(Player messageSender, int range, String message) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.getLocation().distance(messageSender.getLocation()) <= range) {
                target.sendMessage(Utils.chatColor(plugin.getConfig().getString("chat-format")
                        .replaceAll("%player%", messageSender.getDisplayName())
                        .replaceAll("%message%", message)));
            }
        }
    }

}
