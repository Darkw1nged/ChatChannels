package me.darkwinged.ChatChannels;

import me.darkwinged.ChatChannels.commands.*;
import me.darkwinged.ChatChannels.libaries.CustomConfig;
import me.darkwinged.ChatChannels.libaries.Utils;
import me.darkwinged.ChatChannels.libaries.storage.SQLite;
import me.darkwinged.ChatChannels.listeners.accountSetup;
import me.darkwinged.ChatChannels.listeners.messageListener;
import me.darkwinged.ChatChannels.managers.mgrUsers;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main getInstance;
    public mgrUsers users;

    public CustomConfig fileMessage;
    public CustomConfig fileChannels = new CustomConfig(this, "channels", true);

    public void onEnable() {
        // ---- [ Initializing instance of main class ] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files | Loading Channels] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();
        fileChannels.saveDefaultConfig();
        Utils.loadChannels();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Setting up managers ] ----
        users = new mgrUsers();

        // ---- [ Setting up SQLite ] ----
        SQLite sqlLite = new SQLite();
        sqlLite.loadTables();

        // ---- [ Startup message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("startup")));
    }

    public void onDisable() {
        // ---- [ shutdown message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("shutdown")));
    }

    public void loadCommands() {
        getCommand("chatchannel").setExecutor(new cmdReload());
        getCommand("channel").setExecutor(new cmdChannel());
        getCommand("shout").setExecutor(new cmdShout());
        getCommand("whisper").setExecutor(new cmdWhisper());
        getCommand("chatfilter").setExecutor(new cmdChatfilter());
        getCommand("chat").setExecutor(new cmdChat());
    }
    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new accountSetup(), this);
        getServer().getPluginManager().registerEvents(new messageListener(), this);
    }
}
