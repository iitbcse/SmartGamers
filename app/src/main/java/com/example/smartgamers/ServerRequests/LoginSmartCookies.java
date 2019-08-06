package com.example.smartgamers.ServerRequests;

import com.example.smartgamers.ServerRequests.SmartCookieLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
// This class records the output of the retrofit post request

public interface LoginSmartCookies {

//    https://dev.smartcookie.in/core/Version3/login_student_V4.php
    @POST("core/Version3/login_student_V4.php")
    Call<SmartCookieLogin> getPosts(@Body SmartCookieLogin post);

    @FormUrlEncoded
    @POST("~yashap/intern/ii2.php")
    Call<HomePage> getData(@Field("username") String data,@Field("info") String val);

    @FormUrlEncoded
    @POST("~yashap/intern/ii2.php")
    Call<GameList> getGames(@Field("username") String log, @Field("game_list") String lis);

    @FormUrlEncoded
    @POST("~yashap/intern/ii2.php")
    Call<GameList> getGameInfo(@Field("username") String user, @Field("game_name") String name);

    @FormUrlEncoded
    @POST("~yashap/intern/ii2.php")
    Call<SmartCookieLogin> logout(@Field("username") String log, @Field("logout") String val);

}
