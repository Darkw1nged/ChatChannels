package me.darkwinged.ChatChannels.managers;

import me.darkwinged.ChatChannels.libaries.Channel;
import me.darkwinged.ChatChannels.libaries.storage.SQLite;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class mgrUsers {

    public static mgrUsers getInstance;
    public mgrUsers() {
        getInstance = this;
    }

    public boolean inDatabase(OfflinePlayer player) {
        String uuid = String.valueOf(player.getUniqueId());
        String query = "SELECT 1 FROM users WHERE uuid='" + uuid + "';";
        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(OfflinePlayer player) {
        if (inDatabase(player)) return;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "INSERT INTO users values('" + uuid + "', 'Global', 0)";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(OfflinePlayer player) {
        if (!inDatabase(player)) return;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "DELETE FROM users WHERE uuid= '" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerChannel(OfflinePlayer player) {
        if (!inDatabase(player)) return null;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "SELECT uuid, channel FROM users WHERE uuid='" + uuid + "';";
        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString("channel");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUserChannel(OfflinePlayer player, Channel channel) {
        String uuid = String.valueOf(player.getUniqueId());
        String query = "UPDATE users SET channel='" + channel.getChannelName() + "' WHERE uuid='" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getUserChatFilter(OfflinePlayer player) {
        if (!inDatabase(player)) return false;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "SELECT uuid, blockProfanity FROM users WHERE uuid='" + uuid + "';";
        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("blockProfanity") == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setUserChatFilter(OfflinePlayer player, boolean filterChat) {
        String uuid = String.valueOf(player.getUniqueId());
        int value = filterChat ? 1 : 0;
        String query = "UPDATE users SET blockProfanity=" + value + " WHERE uuid='" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
