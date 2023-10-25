/*

package com.example.giga_stats.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.DB.ENTITY.Sets;
import com.example.giga_stats.R;

import java.util.List;

public class SetsAdapter extends RecyclerView.Adapter<SetsAdapter.ViewHolder> {

    private List<Sets> setsList;
    private Context context;

    public SetsAdapter(List<Sets> setsList, Context context) {
        this.setsList = setsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    /*
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sets_list_item, parent, false);
            return new ViewHolder(view);
        }
    */
/*
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sets setItem = setsList.get(position);

        holder.workoutIdTextView.setText("Workout ID: " + setItem.workout_id);
        holder.exerciseIdTextView.setText("Exercise ID: " + setItem.exercise_id);
        holder.dateTextView.setText("Date: " + setItem.date);
        holder.repetitionsTextView.setText("Repetitions: " + setItem.repetitions);
        holder.weightTextView.setText("Weight: " + setItem.weight);
    }

    @Override
    public int getItemCount() {
        return setsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView workoutIdTextView;
        TextView exerciseIdTextView;
        TextView dateTextView;
        TextView repetitionsTextView;
        TextView weightTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            workoutIdTextView = itemView.findViewById(R.id.workoutIdTextView2);
            exerciseIdTextView = itemView.findViewById(R.id.exerciseIdTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            repetitionsTextView = itemView.findViewById(R.id.repetitionsTextView);
            weightTextView = itemView.findViewById(R.id.weightTextView);
        }
    }
}


*/