package com.example.giga_stats.fragments;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
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
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.giga_stats.R;

/**
 * Ein Fragment zur Anzeige und Steuerung eines Timer für Workout- und Pausenintervalle.
 */
public class FragmentTimer extends Fragment {
    private CountDownTimer workoutTimer;
    private CountDownTimer restTimer;
    private TextView workoutTimeTextView;
    private TextView restTimeTextView;
    private TextView currentTime;
    private SeekBar workoutSeekBar;
    private SeekBar restSeekBar;
    private FragmentTimerRoundProgressBar workoutProgressBar;
    private boolean isWorkoutRunning = false;
    private boolean isRestRunning = false;
    private boolean isWorkoutPaused = false;
    private boolean isRestPaused = false;
    private long workoutDurationInMillis = 60000; // Workout-Dauer in Millisekunden (hier 60 Sekunden)
    private long workoutTemp = workoutDurationInMillis; //Dient als temporäre Variable um Änderungen während der Laufzeit des Timers zu speichern
    private long restDurationInMillis = 20000;    // Pause-Dauer in Millisekunden (hier 30 Sekunden)
    private long restTemp = restDurationInMillis; //Dient als temporäre Variable um Änderungen während der Laufzeit des Timers zu speichern
    private long remainingWorkoutTime = 0;
    private long remainingRestTime = 0;


    /**
     * Leerer Standardkonstruktor für das FragmentTimer.
     */
    public FragmentTimer() {
        // Erforderlicher leerer Standardkonstruktor
    }



    //=====================================================LEBENSZYKLUS==========================================================================

    /**
     * Wird aufgerufen, wenn das Fragment erstellt wird.
     *
     * @param savedInstanceState Die gespeicherten Daten des Fragments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen
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
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        workoutSeekBar = view.findViewById(R.id.workoutSeekBar);
        restSeekBar = view.findViewById(R.id.restSeekBar);
        workoutTimeTextView = view.findViewById(R.id.workoutTimeTextView);
        restTimeTextView = view.findViewById(R.id.restTimeTextView);
        currentTime = view.findViewById(R.id.currentTime);
        currentTime.setText(formatTime(workoutDurationInMillis/1000));
        workoutProgressBar = view.findViewById(R.id.workoutProgressBar);
        workoutProgressBar.setMax(100); // Setzen Sie den maximalen Wert
        workoutProgressBar.setBackgroundColor(getResources().getColor(R.color.dividerGray)); // Setzen Sie die Hintergrundfarbe
        workoutProgressBar.setProgress(100);
        Button playButton = view.findViewById(R.id.play);
        Button stopButton = view.findViewById(R.id.stop);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePlayButtonClick();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleStopButtonClick();
            }
        });



        workoutSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // workoutDurationInMillis nur beim Start oder Neustart aktualisieren
                workoutTimeTextView.setText("Übungszeit: " + formatTime(progress));
                workoutTemp = progress * 1000;
                if (!isRestPaused&&!isWorkoutPaused&&!isWorkoutRunning&&!isRestRunning) {
                    currentTime.setText(formatTime(workoutDurationInMillis / 1000));
                    workoutDurationInMillis = progress * 1000;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        restSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                restTemp = progress * 1000;
                restTimeTextView.setText("Pausenzeit: " + formatTime(progress));
                if (!isRestPaused&&!isWorkoutPaused&&!isWorkoutRunning&&!isRestRunning) {
                    restDurationInMillis = progress * 1000;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return view;
    }

    /**
     * Wird aufgerufen, wenn das Fragment in den Vordergrund tritt.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d("CHAD", "LIFE TIMER: onResume(): Das Fragment tritt in den Vordergrund");
        // Hier können Sie Aktualisierungen durchführen und Benutzerinteraktionen ermöglichen.
    }

    /**
     * Wird aufgerufen, wenn das Fragment in den Hintergrund wechselt.
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d("CHAD", "LIFE TIMER: onPause(): Das Fragment wechselt in den Hintergrund");
        // Hier können Sie Aufgaben ausführen, wenn das Fragment in den Hintergrund wechselt.
    }



    //=====================================================OPTIONSMENÜ==========================================================================

    /**
     * Erstellt das Optionsmenü für das Fragment.
     *
     * @param menu     Das Optionsmenü.
     * @param inflater Der MenuInflater zum Aufblasen des Menüs.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_option_timer, menu);
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
        if (itemId == R.id.option_menu_tutorial_timer) {
            openTutorialDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Behandelt den Klick auf den Abspiel-Button.
     */
    private void handlePlayButtonClick() {
        if (isWorkoutRunning || isRestRunning) {
            pauseWorkoutTimer();
        } else {
            startWorkoutTimer(workoutDurationInMillis);
        }
    }

    /**
     * Behandelt den Klick auf den Stop-Button.
     */
    private void handleStopButtonClick() {
        stopTimers();
    }


    /**
     * Aktualisiert das Optionsmenü.
     *
     * @param menu Das Optionsmenü.
     */
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            // Konfigurieren Sie die Toolbar nach Bedarf
            toolbar.setTitle("Timer"); // Setzen Sie den Titel für die Toolbar
        }
    }



    //=====================================================DIALOGE==========================================================================

    /**
     * Öffnet einen Dialog mit einem Tutorial für den Timer.
     */
    private void openTutorialDialog() {
        String textContent = "Hier ist der Tutorial-Text, den du anzeigen möchtest.";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Tutorial Übungen");
        builder.setCustomTitle(titleView);

        // Erstellet ein TextView, um den Textinhalt anzuzeigen
        final TextView textView = new TextView(requireContext());
        textView.setText(textContent);

        // Fügent das TextView zum Dialog hinzu
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



    //=====================================================TIMERFUNKTIONEN==========================================================================

    /**
     * Startet oder pausiert den Workout-Timer basierend auf dem aktuellen Status.
     *
     * @param initialTime Die anfängliche Zeit für den Timer.
     */
    private void startWorkoutTimer(long initialTime) {
        if (isWorkoutRunning) {
            return;
        }

        if (!isWorkoutPaused&&!isRestPaused) {
            isWorkoutRunning = true;
            workoutTimer = new CountDownTimer(initialTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;
                    currentTime.setText(formatTime(seconds));
                    remainingWorkoutTime = millisUntilFinished; // Speichere die verbleibende Zeit
                    updateProgressbar();
                    currentTime.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {
                    isWorkoutRunning = false;
                    startRestTimer();
                    currentTime.setVisibility(View.VISIBLE);
                }
            }.start();
        }
        else if (isWorkoutPaused&&!isRestPaused){
            isWorkoutRunning = true;
            workoutTimer = new CountDownTimer(remainingWorkoutTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;
                    currentTime.setText(formatTime(seconds));
                    remainingWorkoutTime = millisUntilFinished; // Speichere die verbleibende Zeit
                    updateProgressbar();
                    currentTime.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {
                    isWorkoutRunning = false;
                    startRestTimer();
                    currentTime.setVisibility(View.VISIBLE);

                }
            }.start();
        }
        else if (isRestPaused){
            startRestTimer();
        }
    }

    /**
     * Aktualisiert die ProgressBar basierend auf dem aktuellen Fortschritt.
     */
    private void updateProgressbar(){
        if(isWorkoutRunning) {

            // Berechnet den Fortschritt basierend auf der verbleibenden Zeit
            int progress = (int) ((double) remainingWorkoutTime / workoutDurationInMillis * 100);

            // Aktualisiert die ProgressBar
            workoutProgressBar.setColor(getResources().getColor(R.color.light_blue_600)); // Setzen Sie die Farbe der gefüllten Fläche auf Grün
            workoutProgressBar.setProgress(progress);
            Log.d("CHAD", "update Progressbar aufgerufen" + progress);
        }
        else if (isRestRunning){
            // Berechnet den Fortschritt basierend auf der verbleibenden Zeit
            int progress = (int) ((double) remainingRestTime / restDurationInMillis * 100);

            // Aktualisiert die ProgressBar
            workoutProgressBar.setColor(getResources().getColor(R.color.softRed)); // Setzen Sie die Farbe der gefüllten Fläche auf Orange
            Log.d("CHAD", "Rest is running");
            workoutProgressBar.setProgress(progress);
        }

    }

    /**
     * Startet den Rest-Timer.
     */
    private void startRestTimer() {
        remainingWorkoutTime = workoutDurationInMillis;
        isRestRunning = true;
        if (!isRestPaused){
        restTimer = new CountDownTimer(restDurationInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                currentTime.setText(formatTime(seconds));
                remainingRestTime = millisUntilFinished;
                updateProgressbar();
            }

            @Override
            public void onFinish() {
                isWorkoutPaused = false;
                startWorkoutTimer(remainingWorkoutTime); // Starte den Workout-Timer mit der verbleibenden Zeit
            }
        }.start();}
        else if (isRestPaused){
            restTimer = new CountDownTimer(remainingRestTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;
                    currentTime.setText(formatTime(seconds));
                    remainingRestTime = millisUntilFinished;
                    updateProgressbar();
                }

                @Override
                public void onFinish() {
                    isWorkoutPaused = false;
                    isRestPaused = false;
                    startWorkoutTimer(remainingWorkoutTime); // Starte den Workout-Timer mit der verbleibenden Zeit
                }
            }.start();
        }
    }

    /**
     * Pausiert den Workout-Timer oder den Rest-Timer.
     */
    private void pauseWorkoutTimer() {
        if (isWorkoutRunning) {
            isWorkoutRunning = false;
            isWorkoutPaused = true;
            workoutTimer.cancel();
        }
        else if (isRestRunning){
            isRestRunning = false;
            isRestPaused = true;
            restTimer.cancel();
        }
    }

    /**
     * Stoppt beide Timer und setzt die Ansicht zurück.
     */
    private void stopTimers() {
        if (workoutTimer != null) {
            workoutTimer.cancel();
        }
        if (restTimer != null) {
            restTimer.cancel();
        }
        currentTime.setVisibility(View.INVISIBLE);
        isWorkoutPaused = false;
        isRestPaused = false;
        isRestRunning = false;
        isWorkoutRunning = false;
        workoutDurationInMillis = workoutTemp;
        restDurationInMillis = restTemp;
        currentTime.setText(formatTime(workoutDurationInMillis/1000));
        workoutProgressBar.setColor(R.color.pastelGreen);
        workoutProgressBar.setProgress(100);
    }

    /**
     * Formatieren der Zeit in das HH:MM-Format.
     *
     * @param seconds Die Zeit in Sekunden.
     * @return Die formatierte Zeit.
     */
    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }


}
