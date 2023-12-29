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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;


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
    private HashMap<Integer, SetAverage> setAveragePerExercise;
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
            insertSets(workoutExercises);
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
        builder.setCustomTitle(titleView);

        View dialogView = inflater.inflate(R.layout.dialog_layout_total_stats, null);

        // Hinzufügen der Glückwunschnachricht zur Nachricht des Dialogs
        String message = "Herzlichen Glückwunsch! Hier Ihre Statistik:\n";

        // Hinzufügen der Timer-Zeit zur Nachricht
        message += "Verstrichene Zeit: " + timerTextView.getText() + "\n";

        builder.setMessage(message);

        // Fügt einen "Ja"-Button zum Dialog hinzu
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Hier können Sie die Logik für die Anzeige der Gesamtstatistik implementieren
            // Schließen Sie das Bottom Sheet
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
                HashMap<Integer, SetAverage> setAveragePerExercise = new HashMap<>();
                this.workoutExercises = workoutExercises;
                for (int i = 0; i < workoutExercises.getExercises().size(); i++) {
                    SetAverage setAverage = calculateSetAverages(workout.getWorkout_id(), workoutExercises.getExercises().get(i).getExercise_id());
                    setAveragePerExercise.put(workoutExercises.getExercises().get(i).getExercise_id(), setAverage);
                }
                AdapterRunningWorkoutBottomSheet adapter = new AdapterRunningWorkoutBottomSheet(context, workoutExercises, setAveragePerExercise, this);
                recyclerViewBottomSheet.setLayoutManager(new LinearLayoutManager(context));
                recyclerViewBottomSheet.setAdapter(adapter);
            }
            Log.d("CHAD", "FragmentRunningWorkoutBottomSheet - updateList() -> workoutExercises = null");
        });
    }

    private SetAverage calculateSetAverages(int workout_id, int exercise_id) {
        SetAverage setAverage = new SetAverage();
        LiveData<List<Sets>> setsLiveData = appDatabase.setDao().getSetsForExerciseAndWorkout(workout_id, exercise_id);
        setsLiveData.observe(this, sets -> {
            if (sets != null && !sets.isEmpty()) {
                double totalWeight = 0.0;
                double totalReps = 0.0;
                for (Sets set : sets) {
                    totalWeight += set.getWeight();
                    totalReps += set.getRepetitions();
                }
                setAverage.setAverageWeight(totalWeight / sets.size());
                setAverage.setAverageReps(totalReps / sets.size());
            }
        });
        return setAverage;
    }

    private void insertSets(WorkoutExercises workoutExercises) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateString = format.format(calendar.getTime());

        // TODO: Statistik evtl Durchschnitt Gewicht und Reps pro Exercise auflisten
        //       unter Workout bearbeiten alle bereits zum Workout gehörigen Exercises hervorheben (Logik auch so im Hintergrund umsetzen; momentan wird das Workout gelöscht und ein neues angelegt)
        //       Datenbank überarbeiten (sinnvolle CASCADE-Beziehungen einrichten)

        ArrayList<Sets> setList = new ArrayList<>();

        for (Exercise ex : workoutExercises.getExercises()) {
            for (SetDetails e : setDetailsPerExercise.get(ex.getExercise_id())) {
                Sets set = new Sets();
                set.setWorkout_id(workoutExercises.getWorkout().getWorkout_id());
                set.setExercise_id(ex.getExercise_id());
                set.setWeight(e.getWeight());
                set.setRepetitions(e.getReps());
                set.setDate(dateString);
                setList.add(set);
            }
        }

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            for (Sets set : setList) {
                appDatabase.setDao().insertSet(set);
            }
        });

        future.join();
    }

    @Override
    public void onDataChanged(HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise) {
        this.setDetailsPerExercise = setDetailsPerExercise;
        Log.d("CHAD", "FragmentBottomSheet: onDataChanged: setDetailsPerExercise: " + setDetailsPerExercise.toString());
    }
}
