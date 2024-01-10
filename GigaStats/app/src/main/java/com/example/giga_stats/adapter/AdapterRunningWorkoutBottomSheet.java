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

import com.example.giga_stats.database.dto.SetAverage;
import com.example.giga_stats.database.dto.SetDetails;
import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.database.entities.WorkoutExercises;
import com.example.giga_stats.listener.OnDataChangedListener;
import com.example.giga_stats.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Der AdapterRunningWorkoutBottomSheet ist ein RecyclerView-Adapter, der für die Anzeige von
 * laufenden Workouts im Bottom Sheet in der Giga Stats-Anwendung verwendet wird.
 *
 * Diese Klasse ermöglicht die Anzeige von Übungsdetails, Sets und Durchschnittswerten im Bottom Sheet.
 * Sie implementiert die notwendigen Methoden von RecyclerView.Adapter und verwendet eine benutzerdefinierte
 * ViewHolder-Klasse für die einzelnen Listenelemente.
 *
 * @version 1.0
 */
public class AdapterRunningWorkoutBottomSheet extends RecyclerView.Adapter<AdapterRunningWorkoutBottomSheet.ViewHolder> {

    private Context context;
    private WorkoutExercises workoutWithExercises;
    private HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise;
    private HashMap<Integer, SetAverage> setAveragePerExercise;
    private OnDataChangedListener listener;

    /**
     * Konstruktor für den AdapterRunningWorkoutBottomSheet.
     *
     * @param context               Der Kontext, in dem der Adapter erstellt wird.
     * @param workoutWithExercises Das WorkoutExercises-Objekt, das die Informationen zum laufenden Workout enthält.
     * @param setAveragePerExercise Die Durchschnittswerte der Sets pro Übung.
     * @param listener              Der Listener für Datenänderungen.
     */
    public AdapterRunningWorkoutBottomSheet(Context context, WorkoutExercises workoutWithExercises, HashMap<Integer, SetAverage> setAveragePerExercise, OnDataChangedListener listener) {
        this.context = context;
        this.workoutWithExercises = workoutWithExercises;
        setDetailsPerExercise = new HashMap<>();
        this.listener = listener;
        this.setAveragePerExercise = setAveragePerExercise;

        notifyDataSetChanged();
        initializeHashMaps();
    }

        /**
         * Aktualisiert die Daten im Adapter und löst eine Benachrichtigung an die RecyclerView aus.
         */
        public void updateData() {
            notifyDataSetChanged();
        }

        /**
         * Erstellt und gibt einen ViewHolder zurück, der die Layoutinflation für jedes Element in der RecyclerView handhabt.
         *
         * @param parent   Die übergeordnete Ansicht, in die die neue Ansicht eingefügt wird.
         * @param viewType Der Ansichtstyp des neuen Ansichtselements.
         * @return Ein ViewHolder, der die Ansicht für jedes Element in der RecyclerView repräsentiert.
          */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("CHAD", "AdapterRunningWorkoutBottomSheet: onCreateViewHolder() erreicht");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_list_item, parent, false);
            return new ViewHolder(view);
        }

        /**
        * Bindet die Daten an die Ansichtselemente jedes Elements in der RecyclerView.
        *
         * @param holder   Der ViewHolder, der das aktuelle Ansichtselement repräsentiert.
         * @param position Die Position des aktuellen Elements in der RecyclerView-Liste.
         */
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.d("CHAD", "AdapterRunningWorkoutBottomSheet: onBindViewHolder() erreicht");
        Exercise exercise = workoutWithExercises.getExercises().get(position);
        holder.nameExerciseBottomSheetTextView.setText(exercise.getName());
        holder.weightExerciseBottomSheet.setText(String.valueOf(exercise.getWeight()));
        holder.repExerciseBottomSheet.setText(String.valueOf(exercise.getRep()));

        AdapterSets adapterSets = new AdapterSets(context, setDetailsPerExercise, setAveragePerExercise, exercise.getExercise_id());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(adapterSets);

        holder.addSetRowButton.setOnClickListener(view -> {
            addNewSet(exercise);
            updateData();
        });

        if(listener != null) {
            listener.onDataChanged(setDetailsPerExercise);
        }
    }

    /**
     * Initialisiert die HashMaps für SetDetails pro Übung.
     */
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

    /**
     * Fügt ein neues Set zu einer Übung hinzu.
     *
     * @param exercise Das Übungsobjekt, zu dem ein neues Set hinzugefügt werden soll.
     */
    public void addNewSet(Exercise exercise) {
        SetDetails newSet = new SetDetails();
        newSet.setWeight(0);
        newSet.setReps(0);
        newSet.setSetCount(setDetailsPerExercise.get(exercise.getExercise_id()).size());
        newSet.setExercise(exercise);
        setDetailsPerExercise.get(exercise.getExercise_id()).add(newSet);
        Log.d("CHAD", "addNewSet in AdapterRunningWorkoutBottomSheet newSet: " + setDetailsPerExercise.toString());
    }

    /**
     * Gibt die Anzahl der Elemente in der RecyclerView zurück.
     *
     * @return Die Anzahl der Elemente in der RecyclerView.
     */
    @Override
    public int getItemCount() {
        return workoutWithExercises.getExercises().size();
    }

    /**
     * Aktualisiert die Daten im Adapter mit neuen Werten.
     *
     * @param workoutExercises      Das aktualisierte WorkoutExercises-Objekt.
     * @param setAveragePerExercise Die aktualisierten Durchschnittswerte der Sets pro Übung.
     */
    public void updateData(WorkoutExercises workoutExercises, HashMap<Integer, SetAverage> setAveragePerExercise) {
        this.workoutWithExercises = workoutExercises;
        this.setAveragePerExercise = setAveragePerExercise;
        updateData();
    }

    /**
     * Die ViewHolder-Klasse repräsentiert die einzelnen Ansichtselemente in der RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameExerciseBottomSheetTextView;
        RecyclerView recyclerView;
        Button addSetRowButton;
        TextView weightExerciseBottomSheet;
        TextView repExerciseBottomSheet;

        /**
         * Konstruktor für den ViewHolder.
         *
         * @param itemView Die Ansicht, die von diesem ViewHolder repräsentiert wird.
         */
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
