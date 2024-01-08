/**
 * Der AdapterExerciseRoomRecyclerView ist ein RecyclerView-Adapter, der für die Anzeige von
 * Übungen in einem RecyclerView in der Giga Stats-Anwendung verwendet wird.
 *
 * Diese Klasse ermöglicht die Anzeige von Übungsdaten und deren Auswahl in einem RecyclerView.
 * Sie implementiert die notwendigen Methoden von RecyclerView.Adapter und verwendet eine benutzerdefinierte
 * MyViewHolder-Klasse für die einzelnen Listenelemente.
 *
 * @version 1.0
 */

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

    /**
     * Das Interface OnItemClickListener wird verwendet, um auf Klickereignisse in der RecyclerView zu reagieren.
     */
    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    private List<Exercise> exerciseList;
    private List<Exercise> selectedExercises;
    private OnItemClickListener listener;
    private Context context;

    /**
     * Konstruktor für den AdapterExerciseRoomRecyclerView.
     *
     * @param context           Der Kontext, in dem der Adapter erstellt wird.
     * @param exerciseList      Die Liste von Übungen, die im RecyclerView angezeigt werden sollen.
     * @param selectedExercises Die Liste von ausgewählten Übungen.
     * @param listener          Der Listener für Klickereignisse auf die Übungselemente.
     */
    public AdapterExerciseRoomRecyclerView(Context context, List<Exercise> exerciseList, List<Exercise> selectedExercises, OnItemClickListener listener) {
        this.exerciseList = exerciseList;
        this.selectedExercises = selectedExercises;
        this.listener = listener;
        this.context = context;

    }

    /**
     * Erstellt und gibt einen MyViewHolder zurück, der die Layoutinflation für jedes Element in der RecyclerView handhabt.
     *
     * @param parent   Die übergeordnete Ansicht, in die die neue Ansicht eingefügt wird.
     * @param viewType Der Ansichtstyp des neuen Ansichtselements.
     * @return Ein MyViewHolder, der die Ansicht für jedes Element in der RecyclerView repräsentiert.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CHAD", "Adapter erreicht! JJJJJJJJJJJJJJJJJJJUUUUUUUUUUUUUUUUUUUUUHHHHHHUUUUUUUUUUUU");
        View itemView = LayoutInflater.from(context).inflate(R.layout.dialog_layout_add_exercise_list, parent, false);
        return new MyViewHolder(itemView);
    }

    /**
     * Bindet die Daten an die Ansichtselemente jedes Elements in der RecyclerView.
     *
     * @param holder   Der MyViewHolder, der das aktuelle Ansichtselement repräsentiert.
     * @param position Die Position des aktuellen Elements in der RecyclerView-Liste.
     */
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

    /**
     * Gibt die Anzahl der Elemente in der RecyclerView zurück.
     *
     * @return Die Anzahl der Elemente in der RecyclerView.
     */
    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    /**
     * Die MyViewHolder-Klasse repräsentiert die Ansichtselemente jedes Elements in der RecyclerView.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView categoryTextView;
        public TextView idTextView;
        public LinearLayout exerciseBox;

        /**
         * Konstruktor für den MyViewHolder.
         *
         * @param view Die Ansicht, die die Layoutelemente für jedes Element repräsentiert.
         */
        public MyViewHolder(@NonNull View view){
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.nameExerciseAddDialog);
            categoryTextView = (TextView) view.findViewById(R.id.categoryExerciseAddDialog);
            idTextView = (TextView) view.findViewById(R.id.idExerciseAddDialog);
            exerciseBox = (LinearLayout) view.findViewById(R.id.linearLayoutExerciseAddDialog);
        }
    }
}
