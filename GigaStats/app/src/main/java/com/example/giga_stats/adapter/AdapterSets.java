package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterSets extends RecyclerView.Adapter<AdapterSets.ViewHolder> {

    Context context;
    ArrayList<Integer> setCount;
    HashMap<Integer, ArrayList<Integer>> setsPerExercise;

    public AdapterSets(Context context, HashMap<Integer, ArrayList<Integer>> setsPerExercise, int pos){
        this.context = context;
        this.setsPerExercise = setsPerExercise;
        this.setCount = setsPerExercise.get(pos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_inner_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int count = setCount.get(position);
        holder.setCount.setText(String.valueOf(count));
    }

    @Override
    public int getItemCount() {
        return setCount.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView setCount;

        public ViewHolder(View itemView) {
            super(itemView);
            setCount = itemView.findViewById(R.id.setCount);
        }
    }
}
