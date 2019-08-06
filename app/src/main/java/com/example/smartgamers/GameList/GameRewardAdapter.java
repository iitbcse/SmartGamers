package com.example.smartgamers.GameList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartgamers.R;

import java.util.ArrayList;

public class GameRewardAdapter extends RecyclerView.Adapter<GameRewardAdapter.GameRewardHolder> {

    private ArrayList<RewardData> rewardData;

    public GameRewardAdapter(ArrayList<RewardData> rewardData) {
        this.rewardData = rewardData;
    }

    @NonNull
    @Override
    public GameRewardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
//Get the same json file as that in GameActivity and show the necessary field names
//        Accept the field values and send it to server. UPdate there and get the updated data back and show it to the screen... #NOT_TRIVIAL
        GameRewardHolder rcv = new GameRewardHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull GameRewardHolder holder, int position) {
//        holder.data.setText();

    }

    @Override
    public int getItemCount() {
        return rewardData.size();
    }

    public class GameRewardHolder extends RecyclerView.ViewHolder {
        TextView field;
        EditText data;

        public GameRewardHolder(@NonNull View itemView) {
            super(itemView);
            field = itemView.findViewById(R.id.field_name_item_game);
            data = itemView.findViewById(R.id.field_value_item_game);
        }
    }
}
