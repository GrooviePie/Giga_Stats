package com.example.giga_stats.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.R;

import java.util.List;

public class AdapterExerciseRoomRecyclerView extends RecyclerView.Adapter<AdapterExerciseRoomRecyclerView.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    private List<Exercise> exerciseList;
    private List<Exercise> selectedExercises;
    private OnItemClickListener listener;
    private Context context;

    public AdapterExerciseRoomRecyclerView(Context context, List<Exercise> exerciseList, List<Exercise> selectedExercises, OnItemClickListener listener) {
        this.exerciseList = exerciseList;
        this.selectedExercises = selectedExercises;
        this.listener = listener;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CHAD", "Adapter erreicht! JJJJJJJJJJJJJJJJJJJUUUUUUUUUUUUUUUUUUUUUHHHHHHUUUUUUUUUUUU");
        View itemView = LayoutInflater.from(context).inflate(R.layout.dialog_layout_add_exercise_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);

        boolean isSelected = selectedExercises.contains(exercise);
        holder.exerciseBox.setBackgroundColor(isSelected ? ContextCompat.getColor(context, R.color.fadedPastelGreen) : ContextCompat.getColor(context, android.R.color.transparent));

        holder.nameTextView.setText(exercise.getName());
        holder.categoryTextView.setText(exercise.getCategory());
        holder.idTextView.setText(String.valueOf(exercise.getExercise_id()));

        holder.itemView.setOnClickListener(v -> {
            if (isSelected) {
                selectedExercises.remove(exercise);
            } else {
                selectedExercises.add(exercise);
            }
            notifyItemChanged(position);
        });
    }


    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView categoryTextView;
        public TextView idTextView;
        public LinearLayout exerciseBox;

        public MyViewHolder(@NonNull View view){
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.nameExerciseAddDialog);
            categoryTextView = (TextView) view.findViewById(R.id.categoryExerciseAddDialog);
            idTextView = (TextView) view.findViewById(R.id.idExerciseAddDialog);
            exerciseBox = (LinearLayout) view.findViewById(R.id.linearLayoutExerciseAddDialog);
        }
    }
}
