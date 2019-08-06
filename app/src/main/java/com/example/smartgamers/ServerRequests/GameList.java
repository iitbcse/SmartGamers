package com.example.smartgamers.ServerRequests;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GameList {

    private JsonPrimitive games;

    private JsonObject game;

    public JsonPrimitive getList() {
        return games;
    }

    public JsonObject getDetails() {
        return game;
    }
}
