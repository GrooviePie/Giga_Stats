package com.example.giga_stats.activityNfragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.giga_stats.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class FragmentRunningWorkoutBottomSheet extends BottomSheetDialogFragment {
    TextView startWorkout;
    TextView endWorkout;
    TextView timerTextView;
    Handler timerHandler = new Handler();
    int seconds = 0;
    boolean isTimerRunning = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout_bottom_sheet_running_workout, container, false);

        startWorkout = view.findViewById(R.id.buttonStartWorkout);
        endWorkout = view.findViewById(R.id.buttonEndWorkout);
        timerTextView = view.findViewById(R.id.timerTextView);

        //Workout wird gestartet
        startWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCancelable(false); //Lässt das BottomSheet nicht mehr schließen

                isTimerRunning = true;
                startTimer();

                endWorkout.setVisibility(View.VISIBLE);
            }
        });

        //Dieser Button erscheint erst wenn auf Start gedrückt wurde
        endWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showConfirmationDialog();  //Dialog zum Beenden des Workouts
                stopTimer();
            }
        });


        return view;
    }

    //=====================================================DIALOGE==========================================================================

    //Dialog zum Beenden des Workouts
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Workout beenden");
        builder.setMessage("Möchten Sie das Workout wirklich beenden?");
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hier können Sie den Dialog für die Gesamtstatistik aufrufen
                showTotalStatsDialog();
                setCancelable(true);
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hier können Sie die Aktion ausführen, wenn "Nein" ausgewählt wurde
                dialog.dismiss(); // Schließt den Dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showTotalStatsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Gesamtstatistik");

        // Hinzufügen der Glückwunschnachricht zur Nachricht des Dialogs
        String message = "Herzlichen Glückwunsch! Hier Ihre Statistik:\n";

        // Hinzufügen der Timer-Zeit zur Nachricht
        message += "Verstrichene Zeit: " + timerTextView.getText() + "\n";

        builder.setMessage(message);

        // Fügt einen "Ja"-Button zum Dialog hinzu
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hier können Sie die Logik für die Anzeige der Gesamtstatistik implementieren
                // Schließen Sie das Bottom Sheet
                dismiss();
            }
        });

        // Dialog erstellen und anzeigen
        AlertDialog dialog = builder.create();
        dialog.show();
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

}
