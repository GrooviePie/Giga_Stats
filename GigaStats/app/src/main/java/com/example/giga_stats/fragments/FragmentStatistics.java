package com.example.giga_stats.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FragmentStatistics extends Fragment {
    private AppDatabase appDatabase;
    private List<WorkoutExercises> workoutsWithExercises = new ArrayList<>();
    private List<WorkoutAveragesPerExercise> workoutAverages = new ArrayList<>();
    private List<WorkoutEfficiencyPerExercise> workoutEfficiencies = new ArrayList<>();
    private RecyclerView statisticsRecyclerView;
    private Context context;


    //private boolean isFragmentInitialized = false; // Flagge, um zu überprüfen, ob das Fragment bereits initialisiert wurde

    public FragmentStatistics() {
        // Required empty public constructor
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in StatisticsFragment.java aufgerufen");
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen

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
        // Hier können Sie Aufgaben ausführen, wenn das Fragment in den Hintergrund wechselt.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {// Überprüfen, ob das Fragment bereits initialisiert wurde
        inflater.inflate(R.menu.menu_option_statistics, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

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

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            // Konfigurieren Sie die Toolbar nach Bedarf
            toolbar.setTitle("Statistiken"); // Setzen Sie den Titel für die Toolbar
        }
    }

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

        return view;
    }

    private void openTutorialDialog() {
        // Der Textinhalt, den du anzeigen möchtest
        //TODO: Tutorial schreiben für Fragment "Statistics"
        String textContent = "Hier werden in Zukunft die Statistiken der Benutzer angezeigt.";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Tutorial Statistik");
        builder.setCustomTitle(titleView);

        // Erstellen Sie ein TextView, um den Textinhalt anzuzeigen
        final TextView textView = new TextView(requireContext());
        textView.setText(textContent);

        // Fügen Sie das TextView zum Dialog hinzu
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

    public void updateStatisticsList(){
        LiveData<List<WorkoutExercises>> workoutExercisesLiveDataList = appDatabase.workoutDao().getWorkoutExercises();
        workoutExercisesLiveDataList.observe(getViewLifecycleOwner(), workoutsWithExercises -> {
            if (workoutsWithExercises != null) {
                this.workoutsWithExercises = workoutsWithExercises;

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

                AdapterStatistics adapter = new AdapterStatistics(context, workoutsWithExercises, workoutEfficiencies);
                statisticsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                statisticsRecyclerView.setAdapter(adapter);
            } else {
                Log.d("CHAD", "FragmentRunningWorkoutBottomSheet - updateList() -> workoutsWithExercises = null");
            }
        });
    }

    private SetAverage calculateSetAverages(List<Sets> sets) {
        double totalWeight = 0.0;
        double totalReps = 0.0;
        for (Sets set : sets) {
            totalWeight += set.getWeight();
            totalReps += set.getRepetitions();
        }
        return new SetAverage(totalWeight / sets.size(), totalReps / sets.size());
    }

    private void fetchSetsAndCalculateAverages(Workout workout, int exerciseId) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
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
        });

        future.join();
    }

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
