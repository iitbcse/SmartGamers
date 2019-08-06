package com.example.smartgamers.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartgamers.GameList.GameListRecAdapter;
import com.example.smartgamers.GameList.GameObject;
import com.example.smartgamers.R;
import com.example.smartgamers.ServerRequests.GameList;
import com.example.smartgamers.ServerRequests.LoginSmartCookies;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameListFragment extends Fragment {

    private RecyclerView mGameList;
    private RecyclerView.Adapter mGameListAdapter;
    private RecyclerView.LayoutManager mGameListManager;
    private ArrayList<GameObject> gameObjectArrayList;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.game_list,container, false);
        gameObjectArrayList = new ArrayList<>();

//        TODO: BASE URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cse.iitb.ac.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginSmartCookies loginSmartCookies = retrofit.create(LoginSmartCookies.class);
        Call<GameList> call = loginSmartCookies.getGames(getActivity().getIntent().getExtras().getString("username"),"OK");

        call.enqueue(new Callback<GameList>() {
            @Override
            public void onResponse(Call<GameList> call, Response<GameList> response) {
                if (response.isSuccessful()) {
                    GameList gl = response.body();
                    JsonPrimitive games = gl.getList();
                    JSONArray array = null;
                    try {
                        array = new JSONArray(games.getAsString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < array.length();i++) {
                        try {
                            GameObject go = new GameObject(array.get(i).toString());
                            gameObjectArrayList.add(go);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    initializeRecyclerView();
                }
                else
                    Toast.makeText(getActivity().getApplicationContext(), response.body() + "code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GameList> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Game List");
    }

    private void initializeRecyclerView() {
        mGameList = view.findViewById(R.id.game_list_recView);
        mGameList.setHasFixedSize(false);
        mGameList.setNestedScrollingEnabled(false);

        mGameListManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mGameList.setLayoutManager(mGameListManager);
        mGameListAdapter = new GameListRecAdapter(gameObjectArrayList);
        mGameList.setAdapter(mGameListAdapter);
    }


}
