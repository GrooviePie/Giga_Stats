package com.example.giga_stats.fragments;

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

import com.example.giga_stats.database.dto.ExerciseTotalStats;
import com.example.giga_stats.database.dto.SetAverage;
import com.example.giga_stats.database.dto.SetDetails;
import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.database.entities.Sets;
import com.example.giga_stats.database.entities.Workout;
import com.example.giga_stats.database.entities.WorkoutExercises;
import com.example.giga_stats.database.manager.AppDatabase;
import com.example.giga_stats.activity.MainActivity;
import com.example.giga_stats.listener.OnDataChangedListener;
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

/**
 * Ein BottomSheetDialogFragment zur Anzeige von laufenden Workouts und deren Statistiken.
 */
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

    /**
     * Konstruktor für das FragmentRunningWorkoutBottomSheet.
     *
     * @param workout Das aktuelle Workout, das angezeigt wird.
     */
    public FragmentRunningWorkoutBottomSheet(Workout workout) {
        this.workout = workout;
    }

    /**
     * Wird aufgerufen, um die Benutzeroberfläche des Fragments zu erstellen oder zu ändern.
     *
     * @param inflater           Der LayoutInflater zum Aufblasen des Layouts.
     * @param container          Die ViewGroup, in der das Fragment platziert wird.
     * @param savedInstanceState Die gespeicherten Daten des Fragments.
     * @return Die erstellte Benutzeroberfläche.
     */
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

    /**
     * Zeigt einen Bestätigungsdialog zum Beenden des Workouts an.
     */
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

    /**
     * Zeigt einen Dialog mit den Gesamtstatistiken des Workouts an.
     */
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

    /**
     * Stoppt den Timer für das Workout.
     */
    private void stopTimer() {
        // Beende den Timer
        timerHandler.removeCallbacks(timerRunnable);
    }

    /**
     * Startet den Timer für das Workout.
     */
    private void startTimer() {
        // Starte den Timer, der alle 1 Sekunde aktualisiert wird
        timerHandler.postDelayed(timerRunnable, 1000);
    }
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            // Aktualisiert den Timer und startet ihn erneut
            seconds++;
            timerTextView.setText(formatTimer(seconds));
            startTimer();
        }
    };

    /**
     * Formatieren der Timer-Anzeige in Minuten und Sekunden.
     *
     * @param seconds Die vergangenen Sekunden.
     * @return Das formatierte Zeitformat (MM:SS).
     */
    private String formatTimer(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }



    //=================================================HILFSMETHODEN======================================================================

    /**
     * Aktualisiert die Liste der laufenden Workout-Übungen.
     */
    private void updateRunningWorkoutExercisesList() {
        LiveData<WorkoutExercises> workoutExercisesLiveData = appDatabase.workoutDao().getExercisesForWorkoutLD(workout.getWorkout_id());
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

    /**
     * Holt die Details der Sätze für eine Übung ab und berechnet die Durchschnittswerte.
     *
     * @param workoutId        Die ID des Workouts.
     * @param exerciseId       Die ID der Übung.
     * @param workoutExercises Die Daten der laufenden Workout-Übungen.
     */
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

    /**
     * Berechnet die Durchschnittswerte für eine Liste von Sets.
     *
     * @param sets Die Liste der Sets.
     * @return Die Durchschnittswerte.
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
     * Speichert die Sets in der Datenbank.
     */
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

    /**
     * Konvertiert SetDetails in eine Map von Sets für jede Übung.
     *
     * @return Die Map von Sets.
     */
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

    /**
     * Berechnet die Effizienz zwischen alten und aktuellen Durchschnittswerten.
     *
     * @param oldSetAvg Die alten Durchschnittswerte.
     * @param currSetAvg Die aktuellen Durchschnittswerte.
     * @return Die berechnete Effizienz.
     */
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

    /**
     * Wird aufgerufen, wenn sich die Daten geändert haben.
     *
     * @param setDetailsPerExercise Die Set-Details pro Übung.
     */
    @Override
    public void onDataChanged(HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise) {
        this.setDetailsPerExercise = setDetailsPerExercise;
        Log.d("CHAD", "FragmentBottomSheet: onDataChanged: setDetailsPerExercise: " + setDetailsPerExercise.toString());
    }

    /**
     * Wird aufgerufen, wenn das Fragment zerstört wird.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
