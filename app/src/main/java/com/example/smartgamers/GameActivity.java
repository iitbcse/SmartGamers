package com.example.smartgamers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartgamers.GameList.GameRewardAdapter;
import com.example.smartgamers.GameList.RewardData;
import com.example.smartgamers.ServerRequests.GameList;
import com.example.smartgamers.ServerRequests.LoginSmartCookies;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameActivity extends AppCompatActivity {
    String username, gameName;
    TextView mTotal, mLastReward, mRewardTime, mGameName;
    Button mSendData;
    
    RecyclerView rewardData;
    RecyclerView.Adapter rewardDataAdapter;
    RecyclerView.LayoutManager rewardDataManager;

    ArrayList<RewardData> rewardDataArrayList;
    JsonObject gameData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        username = this.getIntent().getExtras().getString("username");
        gameName = this.getIntent().getExtras().getString("gameName");
        mTotal = findViewById(R.id.total_game_activity);
        mLastReward = findViewById(R.id.lastReward_game_activity);
        mRewardTime = findViewById(R.id.lastRewardTime_game_activity);
        mSendData = findViewById(R.id.submit_data_btn);
        mGameName = findViewById(R.id.gameName_game_activity);
        mGameName.setVisibility(View.GONE);
//        Log.d("_GG", "USERNAME: " + username + "gamen: " + gameName);
        this.setTitle(gameName.toUpperCase());

        rewardDataArrayList = new ArrayList<RewardData>();

        gameResponses();

        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"TODO:Show a popup showing rewards received and update the total,last info...", Toast.LENGTH_LONG).show();
            }
        });

//        TODO: Get the given games data form the server and display it. Also add an option to submit a response.
    }
//TODO: BASE URL-
    private void gameResponses() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.cse.iitb.ac.in/").addConverterFactory(GsonConverterFactory.create()).build();

        LoginSmartCookies loginSmartCookies = retrofit.create(LoginSmartCookies.class);

        Call<GameList> gl = loginSmartCookies.getGameInfo(username, gameName);

        gl.enqueue(new Callback<GameList>() {
            @Override
            public void onResponse(Call<GameList> call, Response<GameList> response) {
                if (response.isSuccessful()) {
                    GameList info = response.body();
                    gameData = info.getDetails();
                    Log.d("_GG","The josn"  + gameData.toString() + "size" + gameData.size());
                    mTotal.setText(gameData.get("total").toString());
                    mLastReward.setText(gameData.get("last_reward").toString());
                    mRewardTime.setText(gameData.get("last_reward_time").toString());
                }
                else
                    Toast.makeText(getApplicationContext(), "responseError: " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GameList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeRecyclerView() {
        rewardData = findViewById(R.id.rewardData_recView);
        rewardData.setHasFixedSize(false);
        rewardData.setNestedScrollingEnabled(false);

        rewardDataManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rewardData.setLayoutManager(rewardDataManager);
        rewardDataAdapter = new GameRewardAdapter(rewardDataArrayList);
        rewardData.setAdapter(rewardDataAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
