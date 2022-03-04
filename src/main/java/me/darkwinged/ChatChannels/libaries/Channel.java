package me.darkwinged.ChatChannels.libaries;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Channel {

    private String channelName;
    private int range;
    private List<Player> players;

    public Channel(String channelName, int range) {
        this.channelName = channelName;
        this.range = range;
        this.players = new ArrayList<>();
    }

    public Channel(String channelName, int range, List<Player> players) {
        this.channelName = channelName;
        this.range = range;
        this.players = players;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}
