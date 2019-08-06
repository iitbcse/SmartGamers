package com.example.smartgamers.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartgamers.R;
import com.example.smartgamers.ServerRequests.HomePage;
import com.example.smartgamers.ServerRequests.LoginSmartCookies;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private TextView mUsername,mLastReward, mLastActivity, mLastTime, mTotalRewards,mError;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_screen, container, false);
        mUsername = view.findViewById(R.id.username_textView);
        mTotalRewards = view.findViewById(R.id.total_reward_textView);
        mLastReward = view.findViewById(R.id.last_reward_textView);
        mLastActivity = view.findViewById(R.id.last_activity_textView);
        mLastTime = view.findViewById(R.id.last_activity_time_textView);
        mError = view.findViewById(R.id.error_textView);

        username = getActivity().getIntent().getExtras().getString("username");
        mUsername.setText("123456");
        mLastReward.setText("last r");
        mLastActivity.setText("default");
        mLastTime.setText("yesterday");
        mTotalRewards.setText("90909");

        loadData();
        return view;
    }

    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cse.iitb.ac.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginSmartCookies loginSmartCookies = retrofit.create(LoginSmartCookies.class);

        Call<HomePage> call = loginSmartCookies.getData(username, "YES");

        call.enqueue(new Callback<HomePage>() {
            @Override
            public void onResponse(Call<HomePage> call, Response<HomePage> response) {
                if (response.isSuccessful()) {
                    HomePage hp = response.body();
                    mUsername.setText(username);
                    mLastReward.setText(hp.getLast_reward());
                    mLastActivity.setText(hp.getLast_activity());
                    mLastTime.setText(hp.getLast_reward_time());
                    mTotalRewards.setText(hp.getTotal());
                    mError.setText("");
                }
                else
                    mError.setText("Error: " + response.code() + "--" +  response.message());
            }

            @Override
            public void onFailure(Call<HomePage> call, Throwable t) {
                mError.setText(t.getMessage());
            }
        });



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Home");
    }
}
