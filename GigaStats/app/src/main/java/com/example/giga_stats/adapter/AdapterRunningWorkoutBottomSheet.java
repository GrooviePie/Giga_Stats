package com.example.giga_stats.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.DB.DTO.SetData;
import com.example.giga_stats.DB.DTO.SetDetails;
import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.DB.ENTITY.WorkoutExercises;
import com.example.giga_stats.OnDataChangedListener;
import com.example.giga_stats.R;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterRunningWorkoutBottomSheet extends RecyclerView.Adapter<AdapterRunningWorkoutBottomSheet.ViewHolder> {

    private Context context;
    private WorkoutExercises workoutWithExercises;
    private HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise;
    private OnDataChangedListener listener;

    public AdapterRunningWorkoutBottomSheet(Context context, WorkoutExercises workoutWithExercises, OnDataChangedListener listener) {
        this.context = context;
        this.workoutWithExercises = workoutWithExercises;
        setDetailsPerExercise = new HashMap<>();
        this.listener = listener;

        initializeHashMaps();
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
        holder.weightExerciseBottomSheet.setText(String.valueOf(exercise.getWeight()));
        holder.repExerciseBottomSheet.setText(String.valueOf(exercise.getRep()));

        AdapterSets adapterSets = new AdapterSets(context, setDetailsPerExercise, position, exercise.getExercise_id());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(adapterSets);

        holder.addSetRowButton.setOnClickListener(view -> {
            addNewSet(position, exercise);
            updateData();
        });

        if(listener != null) {
            listener.onDataChanged(setDetailsPerExercise, workoutWithExercises);
        }
    }

    private void initializeHashMaps() {
        for (Exercise exercise : workoutWithExercises.getExercises()) {
            ArrayList<SetDetails> initialList = new ArrayList<>();
            SetDetails initSet = new SetDetails();
            initSet.setSetCount(1);
            initSet.setWeight(0);
            initSet.setReps(0);
            initSet.setExercise(exercise);
            initialList.add(initSet);
            setDetailsPerExercise.put(exercise.getExercise_id(), initialList);
        }
    }

    public void addNewSet(int pos, Exercise exercise) {
        SetDetails newSet = new SetDetails();
        newSet.setWeight(0);
        newSet.setReps(0);
        newSet.setSetCount(setDetailsPerExercise.get(exercise.getExercise_id()).size());
        newSet.setExercise(exercise);
        setDetailsPerExercise.get(exercise.getExercise_id()).add(newSet);
        Log.d("CHAD", "addNewSet in AdapterRunningWorkoutBottomSheet newSet: " + newSet.toString());
    }

    @Override
    public int getItemCount() {
        return workoutWithExercises.getExercises().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameExerciseBottomSheetTextView;
        RecyclerView recyclerView;
        Button addSetRowButton;
        TextView weightExerciseBottomSheet;
        TextView repExerciseBottomSheet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameExerciseBottomSheetTextView = itemView.findViewById(R.id.nameExerciseBottomSheet);
            recyclerView = itemView.findViewById(R.id.setRecyclerView);
            addSetRowButton = itemView.findViewById(R.id.addNewRowButton);
            weightExerciseBottomSheet = itemView.findViewById(R.id.weightExerciseBottomSheet);
            repExerciseBottomSheet = itemView.findViewById(R.id.repsExerciseBottomSheet);
        }
    }
}
