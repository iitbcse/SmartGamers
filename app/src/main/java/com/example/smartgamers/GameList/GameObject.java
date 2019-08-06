package com.example.smartgamers.GameList;

import java.io.Serializable;
import java.util.Date;

public class GameObject implements Serializable {

    private String gameName = "DefaultName";
    private int lastReward, totalReward;
    private Date lastTimePlayed;

    public GameObject(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public int getLastReward() {
        return lastReward;
    }

    public int getTotalReward() {
        return totalReward;
    }

    public Date getLastTimePlayed() {
        return lastTimePlayed;
    }

    public void setLastReward(int lastReward) {
        this.lastReward = lastReward;
    }

    public void setTotalReward(int totalReward) {
        this.totalReward = totalReward;
    }

    public void setLastTimePlayed(Date lastTimePlayed) {
        this.lastTimePlayed = lastTimePlayed;
    }

}
