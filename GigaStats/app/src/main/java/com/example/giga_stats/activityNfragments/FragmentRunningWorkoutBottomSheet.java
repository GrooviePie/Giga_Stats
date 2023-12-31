package com.example.giga_stats.activityNfragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.DB.DTO.ExerciseTotalStats;
import com.example.giga_stats.DB.DTO.SetAverage;
import com.example.giga_stats.DB.DTO.SetDetails;
import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.DB.ENTITY.Sets;
import com.example.giga_stats.DB.ENTITY.Workout;
import com.example.giga_stats.DB.ENTITY.WorkoutExercises;
import com.example.giga_stats.DB.MANAGER.AppDatabase;
import com.example.giga_stats.OnDataChangedListener;
import com.example.giga_stats.R;
import com.example.giga_stats.adapter.AdapterRunningWorkoutBottomSheet;
import com.example.giga_stats.adapter.TotalStatsAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Statistik evtl Durchschnitt Gewicht und Reps pro Exercise auflisten
//       unter Workout bearbeiten alle bereits zum Workout gehörigen Exercises hervorheben (Logik auch so im Hintergrund umsetzen; momentan wird das Workout gelöscht und ein neues angelegt)
//       Datenbank überarbeiten (sinnvolle CASCADE-Beziehungen einrichten)

public class FragmentRunningWorkoutBottomSheet extends BottomSheetDialogFragment implements OnDataChangedListener {
    private Workout workout;
    private TextView startWorkout;
    private TextView endWorkout;
    private TextView timerTextView;
    private Handler timerHandler = new Handler();
    int seconds = 0;
    boolean isTimerRunning = false;
    private AppDatabase appDatabase;
    private RecyclerView recyclerViewBottomSheet;
    private Context context;
    private HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise;
    HashMap<Integer, SetAverage> setAveragePerExercise = new HashMap<>();
    private WorkoutExercises workoutExercises;

    public FragmentRunningWorkoutBottomSheet(Workout workout) {
        this.workout = workout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout_bottom_sheet_running_workout, container, false);

        startWorkout = view.findViewById(R.id.buttonStartWorkout);
        endWorkout = view.findViewById(R.id.buttonEndWorkout);
        timerTextView = view.findViewById(R.id.timerTextView);
        TextView bottomSheetExerciseNameTextView = view.findViewById(R.id.bottomSheetExerciseNameTextView);
        bottomSheetExerciseNameTextView.setText(workout.getName());

        recyclerViewBottomSheet = view.findViewById(R.id.gridViewBottomSheet);

        appDatabase = MainActivity.getAppDatabase();
        context = getContext();


        //Workout wird gestartet
        startWorkout.setOnClickListener(view1 -> {
            setCancelable(false); //Lässt das BottomSheet nicht mehr schließen

            isTimerRunning = true;
            startTimer();
            updateRunningWorkoutExercisesList();

            startWorkout.setClickable(false);
            startWorkout.setVisibility(View.INVISIBLE);
            endWorkout.setVisibility(View.VISIBLE);
        });

        //Dieser Button erscheint erst wenn auf Start gedrückt wurde
        endWorkout.setOnClickListener(view12 -> {
            showConfirmationDialog();  //Dialog zum Beenden des Workouts
        });


        return view;
    }

    //=====================================================DIALOGE==========================================================================

    //Dialog zum Beenden des Workouts
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Workout beenden");

        View dialogView = inflater.inflate(R.layout.dialog_layout_delete_entity, null);

        builder.setView(dialogView);
        builder.setCustomTitle(titleView);

        TextView delDialogTextView = dialogView.findViewById(R.id.delDialogTextView);
        delDialogTextView.setText("Möchten Sie das Workout wirklich beenden?");

        builder.setPositiveButton("Ja", (dialog, which) -> {
            // Hier können Sie den Dialog für die Gesamtstatistik aufrufen
            persistSets();
            stopTimer();
            showTotalStatsDialog();
            setCancelable(true);
        });
        builder.setNegativeButton("Nein", (dialog, which) -> {
            // Hier können Sie die Aktion ausführen, wenn "Nein" ausgewählt wurde
            dialog.dismiss(); // Schließt den Dialog
        });

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
        dialog.show();

        // Zugriff auf die Buttons und Anpassen des Stils
        int green = ContextCompat.getColor(requireContext(), R.color.pastelGreen);
        int red = ContextCompat.getColor(requireContext(), R.color.softRed);
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextSize(16);
        positiveButton.setTextColor(green);

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(red);
        negativeButton.setTextSize(14);
    }

    private void showTotalStatsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Stats");

        View dialogView = inflater.inflate(R.layout.dialog_layout_total_stats, null);

        builder.setCustomTitle(titleView);
        builder.setView(dialogView);

        // Hinzufügen der Timer-Zeit zur Nachricht
        String message = "Verstrichene Zeit: " + timerTextView.getText();

        TextView timerStatsTextView = dialogView.findViewById(R.id.timerStatsTextView);
        timerStatsTextView.setText(message);

        HashMap<Integer, SetAverage> setAveragePerExerciseCurr = new HashMap<>();
        HashMap<Integer, List<Sets>> setsMap = convertSetDetailsToSetsMap();
        List<ExerciseTotalStats> statsList = new ArrayList<>();

        setsMap.forEach((key, value) -> {
            SetAverage setAvgCurr = calculateSetAverages(value);
            setAveragePerExerciseCurr.put(key, setAvgCurr);
        });

        for (Exercise ex : workoutExercises.getExercises()) {
            SetAverage oldSetAvg = new SetAverage(setAveragePerExercise.get(ex.getExercise_id()).getAverageWeight(), setAveragePerExercise.get(ex.getExercise_id()).getAverageReps());
            SetAverage currSetAvg = new SetAverage(setAveragePerExerciseCurr.get(ex.getExercise_id()).getAverageWeight(), setAveragePerExerciseCurr.get(ex.getExercise_id()).getAverageReps());

            statsList.add(new ExerciseTotalStats(ex.getName(), oldSetAvg, currSetAvg, calculateEfficiency(oldSetAvg, currSetAvg)));
        }

        RecyclerView recyclerView = dialogView.findViewById(R.id.statsRecyclerView);
        TotalStatsAdapter adapter = new TotalStatsAdapter(statsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        // Fügt einen "OK"-Button zum Dialog hinzu
        builder.setPositiveButton("OK", (dialog, which) -> {
            dismiss();
        });

        // Dialog erstellen und anzeigen
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
        dialog.show();

        // Zugriff auf die Buttons und Anpassen des Stils
        int green = ContextCompat.getColor(requireContext(), R.color.pastelGreen);
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextSize(16);
        positiveButton.setTextColor(green);
    }


    //=====================================================TIMER==========================================================================

    private void stopTimer() {
        // Beende den Timer
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void startTimer() {
        // Starte den Timer, der alle 1 Sekunde aktualisiert wird
        timerHandler.postDelayed(timerRunnable, 1000);
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            // Aktualisiere den Timer und starte erneut
            seconds++;
            timerTextView.setText(formatTimer(seconds));
            startTimer();
        }
    };

    private String formatTimer(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    //=================================================HILFSMETHODEN======================================================================

    private void updateRunningWorkoutExercisesList() {
        LiveData<WorkoutExercises> workoutExercisesLiveData = appDatabase.workoutDao().getExercisesForWorkout(workout.getWorkout_id());
        workoutExercisesLiveData.observe(getViewLifecycleOwner(), workoutExercises -> {
            if (workoutExercises != null) {
                this.workoutExercises = workoutExercises;
                for (Exercise exercise : workoutExercises.getExercises()) {
                    fetchSetsAndCalculateAverages(workout.getWorkout_id(), exercise.getExercise_id(), workoutExercises);
                }
            } else {
                Log.d("CHAD", "FragmentRunningWorkoutBottomSheet - updateList() -> workoutExercises = null");
            }
        });
    }

    private void fetchSetsAndCalculateAverages(int workoutId, int exerciseId, WorkoutExercises workoutExercises) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            List<Sets> sets = appDatabase.setDao().getSetsForExerciseAndWorkout(workoutId, exerciseId);
            if (sets != null && !sets.isEmpty()) {
                SetAverage average = calculateSetAverages(sets);
                setAveragePerExercise.put(exerciseId, average);
                Log.d("CHAD", "fetchSetsAndCalculateAverages: setAveragePerExercise: " + setAveragePerExercise.toString());
            } else {
                setAveragePerExercise.put(exerciseId, new SetAverage());
            }
        });

        future.thenRunAsync(() -> {
            AdapterRunningWorkoutBottomSheet adapter = new AdapterRunningWorkoutBottomSheet(context, workoutExercises, setAveragePerExercise, this);
            recyclerViewBottomSheet.setLayoutManager(new LinearLayoutManager(context));
            recyclerViewBottomSheet.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }, ContextCompat.getMainExecutor(context));
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

    private void persistSets() {
        HashMap<Integer, List<Sets>> setsMap = convertSetDetailsToSetsMap();

        setsMap.forEach((key, value) -> {
            List<Sets> setList = value;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                for (Sets set : setList) {
                    appDatabase.setDao().insertSet(set);
                }
            });

            future.join();
        });
    }

    private HashMap<Integer, List<Sets>> convertSetDetailsToSetsMap() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateString = format.format(calendar.getTime());

        HashMap<Integer, List<Sets>> setsMap = new HashMap<>();

        for (Exercise ex : workoutExercises.getExercises()) {
            List<Sets> setList = new ArrayList<>();

            for (SetDetails e : setDetailsPerExercise.get(ex.getExercise_id())) {
                Sets set = new Sets();
                set.setWorkout_id(workoutExercises.getWorkout().getWorkout_id());
                set.setExercise_id(ex.getExercise_id());
                set.setWeight(e.getWeight());
                set.setRepetitions(e.getReps());
                set.setDate(dateString);

                setList.add(set);
            }
            setsMap.put(ex.getExercise_id(), setList);
        }
        return setsMap;
    }

    private double calculateEfficiency(SetAverage oldSetAvg, SetAverage currSetAvg) {
        double weightEfficiency = 0.0;
        if (oldSetAvg.getAverageWeight() != 0) { // to avoid division by zero
            weightEfficiency = ((currSetAvg.getAverageWeight() - oldSetAvg.getAverageWeight()) / oldSetAvg.getAverageWeight()) * 100;
        }

        double repsEfficiency = 0.0;
        if (oldSetAvg.getAverageReps() != 0) { // to avoid division by zero
            repsEfficiency = ((currSetAvg.getAverageReps() - oldSetAvg.getAverageReps()) / oldSetAvg.getAverageReps()) * 100;
        }

        return (weightEfficiency + repsEfficiency) / 2;
    }

    @Override
    public void onDataChanged(HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise) {
        this.setDetailsPerExercise = setDetailsPerExercise;
        Log.d("CHAD", "FragmentBottomSheet: onDataChanged: setDetailsPerExercise: " + setDetailsPerExercise.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
