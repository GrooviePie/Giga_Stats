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

import com.example.giga_stats.DB.DTO.SetData;
import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.DB.ENTITY.Sets;
import com.example.giga_stats.DB.ENTITY.Workout;
import com.example.giga_stats.DB.ENTITY.WorkoutExercises;
import com.example.giga_stats.DB.MANAGER.AppDatabase;
import com.example.giga_stats.R;
import com.example.giga_stats.adapter.AdapterRunningWorkoutBottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;


public class FragmentRunningWorkoutBottomSheet extends BottomSheetDialogFragment {
    private Workout workout;
    TextView startWorkout;
    TextView endWorkout;
    TextView timerTextView;
    Handler timerHandler = new Handler();
    int seconds = 0;
    boolean isTimerRunning = false;
    AppDatabase appDatabase;
    RecyclerView recyclerViewBottomSheet;
    private Context context;

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

            endWorkout.setVisibility(View.VISIBLE);
        });

        //Dieser Button erscheint erst wenn auf Start gedrückt wurde
        endWorkout.setOnClickListener(view12 -> {

            showConfirmationDialog();  //Dialog zum Beenden des Workouts
            stopTimer();
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
        builder.setCustomTitle(titleView);

        builder.setMessage("Möchten Sie das Workout wirklich beenden?");
        builder.setPositiveButton("Ja", (dialog, which) -> {
            // Hier können Sie den Dialog für die Gesamtstatistik aufrufen
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

    private void updateRunningWorkoutExercisesList() {
        LiveData<WorkoutExercises> workoutExercisesLiveData = appDatabase.workoutDao().getExercisesForWorkout(workout.getWorkout_id());
        workoutExercisesLiveData.observe(getViewLifecycleOwner(), workoutExercises -> {
            if(workoutExercises != null) {
                AdapterRunningWorkoutBottomSheet adapter = new AdapterRunningWorkoutBottomSheet(context, workoutExercises);
                recyclerViewBottomSheet.setLayoutManager(new LinearLayoutManager(context));
                recyclerViewBottomSheet.setAdapter(adapter);


                ArrayList<Sets> set = new ArrayList<>();
                for (int i = 0; i < workoutExercises.getExercises().size(); i++) {
                    set.add(new Sets());
                }

                //TODO: setDetailsPerExercise aus dem Adapter rauskriegen
                //      sets in die DB schreiben

            }
            Log.d("CHAD", "FragmentRunningWorkoutBottomSheet - updateList() -> workoutExercises = null");
        });
    }
}
