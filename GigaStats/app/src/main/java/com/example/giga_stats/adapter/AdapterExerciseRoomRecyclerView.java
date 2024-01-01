package com.example.giga_stats.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.R;

import java.util.List;

public class AdapterExerciseRoomRecyclerView extends RecyclerView.Adapter<AdapterExerciseRoomRecyclerView.MyViewHolder> {

    private int selectedItem;

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    private List<Exercise> exercises;
    private OnItemClickListener listener;
    private Context context;

    public AdapterExerciseRoomRecyclerView(Context context, List<Exercise> exercises, OnItemClickListener listener) {
        this.exercises = exercises;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CHAD", "Adapter erreicht! JJJJJJJJJJJJJJJJJJJUUUUUUUUUUUUUUUUUUUUUHHHHHHUUUUUUUUUUUU");
        View itemView = LayoutInflater.from(context).inflate(R.layout.dialog_layout_add_exercise_list, parent, false);
        itemView.setBackgroundResource(R.drawable.selected_item);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("CHAD", "Adapter erreicht! JJJJJJJJJJJJJJJJJJJUUUUUUUUUUUUUUUUUUUUUHHHHHHUUUUUUUUUUUU");
        Exercise exercise = exercises.get(position);

        Log.d("CHAD", "Exercises im Adapter: " + exercises);

        if(exercise != null) {
            Log.d("CHAD", "Exercise not null");
            holder.nameTextView.setText(exercise.getName());
            holder.categoryTextView.setText(exercise.getCategory());
            holder.idTextView.setText(String.valueOf(exercise.getExercise_id()));
        }

        holder.itemView.setSelected(selectedItem == position);
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(exercise);
            selectedItem = holder.getAdapterPosition();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView categoryTextView;
        public TextView idTextView;

        public MyViewHolder(@NonNull View view){
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.nameExerciseAddDialog);
            categoryTextView = (TextView) view.findViewById(R.id.categoryExerciseAddDialog);
            idTextView = (TextView) view.findViewById(R.id.idExerciseAddDialog);
        }
    }
}
