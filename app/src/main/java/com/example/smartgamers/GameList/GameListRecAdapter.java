package com.example.smartgamers.GameList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartgamers.GameActivity;
import com.example.smartgamers.R;

import java.util.ArrayList;

public class GameListRecAdapter extends RecyclerView.Adapter<GameListRecAdapter.TheViewHolder> {

    ArrayList<GameObject> gameList;

    public GameListRecAdapter(ArrayList<GameObject> gameList) {
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public TheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_list, parent,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        TheViewHolder rcv = new TheViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final TheViewHolder holder, int position) {
        holder.gameName.setText(gameList.get(position).getGameName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                SharedPreferences sp = v.getContext().getSharedPreferences("dataFile", Context.MODE_PRIVATE);
                Intent it = new Intent(v.getContext(), GameActivity.class);
                it.putExtra("username", sp.getString("username",null));
                it.putExtra("gameName", gameList.get(holder.getAdapterPosition()).getGameName());
                v.getContext().startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class TheViewHolder extends RecyclerView.ViewHolder {
        public TextView gameName;
        public LinearLayout layout;
        public TheViewHolder(@NonNull View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.game_name_textView);
            layout = itemView.findViewById(R.id.layout_item_gameList);
        }
    }
}
