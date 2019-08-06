package com.example.smartgamers.ServerRequests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SmartCookieLogin {
    private Integer responseStatus;
    private String responseMessage;
    private String User_Name, User_Type,User_Pass;

    private String username, logout;
    private String message;

// "User_Name","User_Type":"","User_Pass":"","College_Code":"","country_code":"+91","method":"ANDROID","device_type":"android-phone","device_details":"Moto E (4)","platform_OS":"ANDROID 7.1.1","ip_address":"61.119.161.203","lat":"18.4833353","long":"73.8023457","activity":"","entity_sub_type":""}
//{"User_Name":"242993","User_Type":"MemberId","User_Pass":"KARAN123"}

    public SmartCookieLogin() {
    }

    public SmartCookieLogin(String user_Name, String user_Type, String user_Pass) {
        User_Name = user_Name;
        User_Type = user_Type;
        User_Pass = user_Pass;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getMessage() {
        return message;
    }
}
