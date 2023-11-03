package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.DB.ENTITY.WorkoutExercises;
import com.example.giga_stats.R;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterRunningWorkoutBottomSheet extends RecyclerView.Adapter<AdapterRunningWorkoutBottomSheet.ViewHolder> {

    private Context context;
    private WorkoutExercises workoutWithExercises;
    private HashMap<Integer, ArrayList<Integer>> setsPerExercise;

    public AdapterRunningWorkoutBottomSheet(Context context, WorkoutExercises workoutWithExercises) {
        this.context = context;
        this.workoutWithExercises = workoutWithExercises;
        setsPerExercise = new HashMap<>();
        initializeSetsPerExercise();
    }

    public void updateData() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = workoutWithExercises.getExercises().get(position);
        holder.nameExerciseBottomSheetTextView.setText(exercise.getName());

        AdapterSets adapterSets = new AdapterSets(context, setsPerExercise, position);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(adapterSets);

        holder.addSetRowButton.setOnClickListener(view -> {
            int count = setsPerExercise.get(position).size();
            setsPerExercise.get(position).add(count + 1);
            updateData();
        });
    }

    private void initializeSetsPerExercise() {
        for (int i = 0; i < workoutWithExercises.getExercises().size(); i++) {
            setsPerExercise.put(i, new ArrayList<>());
        }
    }

    @Override
    public int getItemCount() {
        return workoutWithExercises.getExercises().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameExerciseBottomSheetTextView;
        RecyclerView recyclerView;
        Button addSetRowButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameExerciseBottomSheetTextView = itemView.findViewById(R.id.nameExerciseBottomSheet);
            recyclerView = itemView.findViewById(R.id.setRecyclerView);
            addSetRowButton = itemView.findViewById(R.id.addNewRowButton);
        }
    }
}
