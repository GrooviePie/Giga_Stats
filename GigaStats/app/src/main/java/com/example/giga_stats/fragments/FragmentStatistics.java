package com.example.giga_stats.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.giga_stats.adapter.AdapterStatistics;
import com.example.giga_stats.database.dto.SetAverage;
import com.example.giga_stats.database.dto.WorkoutAveragesPerExercise;
import com.example.giga_stats.database.dto.WorkoutEfficiencyPerExercise;
import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.database.entities.Sets;
import com.example.giga_stats.database.entities.Workout;
import com.example.giga_stats.database.entities.WorkoutExercises;
import com.example.giga_stats.database.manager.AppDatabase;
import com.example.giga_stats.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * FragmentStatistics ist ein Fragment, das für die Anzeige von Statistiken zu Workouts und Übungen zuständig ist.
 * Es verwendet RecyclerView zur Darstellung der Daten und AnyChart für die Visualisierung der Statistiken.
 */
public class FragmentStatistics extends Fragment {
    private AppDatabase appDatabase;
    private final List<WorkoutExercises> workoutsWithExercises = Collections.synchronizedList(new ArrayList<>());
    private final List<WorkoutEfficiencyPerExercise> workoutEfficiencies = Collections.synchronizedList(new ArrayList<>());
    private final List<WorkoutAveragesPerExercise> workoutAverages = new ArrayList<>();
    private RecyclerView statisticsRecyclerView;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;


    /**
     * Standardkonstruktor für FragmentStatistics.
     */
    public FragmentStatistics() {
    }
    /**
     * Setzt die Referenz zur App-Datenbank für das Fragment.
     *
     * @param appDatabase Die App-Datenbankreferenz.
     */
    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    /**
     * Wird aufgerufen, wenn das Fragment erstellt wird.
     *
     * @param savedInstanceState Die gespeicherten Daten des Fragments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in StatisticsFragment.java aufgerufen");
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("CHAD", "LIFE STATISTICS: onResume(): Das Fragment tritt in den Vordergrund");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("CHAD", "LIFE EXERCISES: onPause(): Das Fragment wechselt in den Hintergrund");
    }

    /**
     * Erstellt das Optionsmenü für das Fragment.
     *
     * @param menu     Das Optionsmenü.
     * @param inflater Der MenuInflater zum Aufblasen des Menüs.
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_option_statistics, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Wird aufgerufen, wenn ein Menüelement ausgewählt wird.
     *
     * @param item Das ausgewählte Menüelement.
     * @return True, wenn die Auswahl behandelt wurde, sonst false.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in StatisticsFragment.java aufgerufen");

        if (itemId == R.id.option_menu_tutorial_statistics) {
            openTutorialDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Vorbereitung des Optionsmenüs.
     *
     * @param menu Das Optionsmenü.
     */
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Statistiken");
        }
    }

    /**
     * Erstellt und gibt die Benutzeroberfläche des Fragments zurück.
     *
     * @param inflater           Der LayoutInflater zum Aufblasen des Layouts.
     * @param container          Die ViewGroup, in der das Fragment platziert wird.
     * @param savedInstanceState Die gespeicherten Daten des Fragments.
     * @return Die erstellte Benutzeroberfläche.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        context = getContext();

        statisticsRecyclerView = view.findViewById(R.id.statistics_recyclerview);

        try {
            updateStatisticsList();
        } catch (Exception e) {
            Log.e("CHAD", "Fehler beim Lesen der Daten: " + e.getMessage());
        }

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::updateStatisticsList);

        return view;
    }

    /**
     * Öffnet einen Dialog mit einem Tutorial für die Statistikansicht.
     */
    private void openTutorialDialog() {
        String textContent = "Hier werden für die einzelnen Übungen die Effizienz angezeigt die Sie in Ihren Workouts erreicht haben.";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Tutorial Statistik");
        builder.setCustomTitle(titleView);

        final TextView textView = new TextView(requireContext());
        textView.setText(textContent);

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(textView);
        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
        dialog.show();

        int green = ContextCompat.getColor(requireContext(), R.color.pastelGreen);

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextSize(16);
        positiveButton.setTextColor(green);
    }

    //============================================HILFSMETHODEN=================================================

    /**
     * Aktualisiert die Liste der Statistiken. Dieser Vorgang umfasst das Abrufen der neuesten Daten aus der Datenbank
     * und deren Anzeige im RecyclerView.
     */
    public void updateStatisticsList() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            List<WorkoutExercises> workoutsWithExercises = appDatabase.workoutDao().getWorkoutExercises();
            if (workoutsWithExercises != null) {
                synchronized (this.workoutsWithExercises) {
                    this.workoutsWithExercises.clear();
                    this.workoutsWithExercises.addAll(workoutsWithExercises);
                    synchronized (this.workoutEfficiencies) {
                        this.workoutEfficiencies.clear();
                    }
                }
                for (WorkoutExercises we : workoutsWithExercises) {
                    WorkoutEfficiencyPerExercise workoutEfficiency = new WorkoutEfficiencyPerExercise();
                    workoutEfficiency.setWorkout(we.getWorkout());

                    for (Exercise ex : we.getExercises()) {
                        fetchSetsAndCalculateAverages(we.getWorkout(), ex.getExercise_id());
                        HashMap<Integer, Double> efficiencyPerExercise = new HashMap<>();

                        for (WorkoutAveragesPerExercise wape : workoutAverages) {

                            wape.getSetAveragePerExercise().forEach((key, value) -> {
                                double efficiency = calculateEfficiency(ex, value);
                                efficiencyPerExercise.put(key, efficiency);
                            });

                            workoutEfficiency.setEfficiencyPerExercise(efficiencyPerExercise);
                        }
                    }
                    workoutEfficiencies.add(workoutEfficiency);
                }
            } else {
                Log.d("CHAD", "FragmentRunningWorkoutBottomSheet - updateList() -> workoutsWithExercises = null");
            }
        });

        future.thenRunAsync(() -> {
            synchronized (this.workoutsWithExercises) {
                synchronized (this.workoutEfficiencies) {
                    AdapterStatistics adapter = new AdapterStatistics(context, workoutsWithExercises, workoutEfficiencies);
                    statisticsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    statisticsRecyclerView.setAdapter(adapter);
                }
            }
            statisticsRecyclerView.invalidate();
            swipeRefreshLayout.setRefreshing(false);
        }, ContextCompat.getMainExecutor(context));
    }

    /**
     * Berechnet die durchschnittlichen Werte für eine Menge von Sets.
     *
     * @param sets Eine Liste von Sets, für die der Durchschnitt berechnet werden soll.
     * @return Ein Objekt der Klasse SetAverage, das die Durchschnittswerte enthält.
     */
    private SetAverage calculateSetAverages(List<Sets> sets) {
        double totalWeight = 0.0;
        double totalReps = 0.0;
        for (Sets set : sets) {
            totalWeight += set.getWeight();
            totalReps += set.getRepetitions();
        }
        return new SetAverage(totalWeight / sets.size(), totalReps / sets.size());
    }

    /**
     * Holt die Sets für eine spezifische Übung und ein Workout aus der Datenbank und berechnet deren Durchschnittswerte.
     *
     * @param workout    Das Workout-Objekt.
     * @param exerciseId Die ID der Übung.
     */
    private void fetchSetsAndCalculateAverages(Workout workout, int exerciseId) {
        HashMap<Integer, SetAverage> setAveragePerExercise = new HashMap<>();
        WorkoutAveragesPerExercise workoutAverage = new WorkoutAveragesPerExercise();
        List<Sets> sets = appDatabase.setDao().getSetsForExerciseAndWorkout(workout.getWorkout_id(), exerciseId);

        if (sets != null && !sets.isEmpty()) {
            SetAverage average = calculateSetAverages(sets);
            setAveragePerExercise.put(exerciseId, average);
        } else {
            setAveragePerExercise.put(exerciseId, new SetAverage());
        }

        workoutAverage.setWorkout(workout);
        workoutAverage.setSetAveragePerExercise(setAveragePerExercise);
        workoutAverages.add(workoutAverage);

        Log.d("CHAD", "fetchSetsAndCalculateAverages: setAveragePerExercise: " + setAveragePerExercise.toString());
    }

    /**
     * Berechnet die Effizienz basierend auf den durchschnittlichen Werten der Sets und den Zielwerten der Übung.
     *
     * @param exercise    Das Exercise-Objekt, für das die Effizienz berechnet werden soll.
     * @param currSetAvg  Das SetAverage-Objekt, das die durchschnittlichen Werte der Sets enthält.
     * @return Die berechnete Effizienz als double-Wert.
     */
    private double calculateEfficiency(Exercise exercise, SetAverage currSetAvg) {
        double weightEfficiency = 0.0;
        if (exercise.getWeight() != 0) { // to avoid division by zero
            weightEfficiency = (currSetAvg.getAverageWeight() / exercise.getWeight()) * 100;
        }

        double repsEfficiency = 0.0;
        if (exercise.getRep() != 0) { // to avoid division by zero
            repsEfficiency = (currSetAvg.getAverageReps() / exercise.getRep()) * 100;
        }

        return (weightEfficiency + repsEfficiency) / 2;
    }

}
