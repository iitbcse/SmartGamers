package com.example.smartgamers.GameList;

public class RewardData {

    private String FieldName;
    private String data;

    public RewardData(String fieldName, String data) {
        FieldName = fieldName;
        this.data = data;
    }

    public String getFieldName() {
        return FieldName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
