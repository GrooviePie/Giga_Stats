package com.example.giga_stats.activityNfragments;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.giga_stats.R;

public class TimerFragment extends Fragment {

    private CountDownTimer workoutTimer;
    private CountDownTimer restTimer;
    private TextView workoutTimeTextView;
    private TextView restTimeTextView;
    private TextView currentTime;
    private SeekBar workoutSeekBar;
    private SeekBar restSeekBar;
    private RoundProgressBar workoutProgressBar;
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

    public TimerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_option_timer, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.option_menu_play_timer) {
            startWorkoutTimer(workoutDurationInMillis); // Starte den Timer mit der verbleibenden Zeit
            return true;
        } else if (itemId == R.id.option_menu_pause_timer) {
            pauseWorkoutTimer();
            return true;
        } else if (itemId == R.id.option_menu_stop_timer) {
            stopTimers();
            return true;
        } else if (itemId == R.id.option_menu_restart_timer) {
            restartWorkoutTimer();
            return true;
        } else if (itemId == R.id.option_menu_tutorial_timer) {
            openTutorialDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

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
                }

                @Override
                public void onFinish() {
                    isWorkoutRunning = false;
                    startRestTimer();
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
                }

                @Override
                public void onFinish() {
                    isWorkoutRunning = false;
                    startRestTimer();
                }
            }.start();
        }
        else if (isRestPaused){
            startRestTimer();
        }
    }

    private void updateProgressbar(){
        if(isWorkoutRunning) {

            // Berechnen Sie den Fortschritt basierend auf der verbleibenden Zeit
            int progress = (int) ((double) remainingWorkoutTime / workoutDurationInMillis * 100);

            // Aktualisieren Sie die ProgressBar
            workoutProgressBar.setColor(0xFF00FF00); // Setzen Sie die Farbe der gefüllten Fläche auf Grün
            workoutProgressBar.setProgress(progress);
        }
        else if (isRestRunning){
            // Berechnen Sie den Fortschritt basierend auf der verbleibenden Zeit
            int progress = (int) ((double) remainingRestTime / restDurationInMillis * 100);

            // Aktualisieren Sie die ProgressBar
            workoutProgressBar.setColor(0xFFE27602); // Setzen Sie die Farbe der gefüllten Fläche auf Orange
            workoutProgressBar.setProgress(progress);
        }

    }

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

    private void stopTimers() {
        if (workoutTimer != null) {
            workoutTimer.cancel();
        }
        if (restTimer != null) {
            restTimer.cancel();
        }
        isWorkoutPaused = false;
        isRestPaused = false;
        isRestRunning = false;
        isWorkoutRunning = false;
        workoutDurationInMillis = workoutTemp;
        restDurationInMillis = restTemp;
        currentTime.setText(formatTime(workoutDurationInMillis/1000));
        workoutProgressBar.setColor(R.color.light_blue_400); // Setzen Sie die Farbe der gefüllten Fläche auf Blau
        workoutProgressBar.setProgress(100);
    }

    private void restartWorkoutTimer() {
        stopTimers();
        startWorkoutTimer(workoutDurationInMillis);
    }

    private void openTutorialDialog() {
        String textContent = "Der Platz von GrooviePie :D \n Herrscher der Zeit ";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("DUDORIEL");

        final TextView textView = new TextView(requireContext());
        textView.setText(textContent);

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(textView);
        builder.setView(layout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        workoutSeekBar = view.findViewById(R.id.workoutSeekBar);
        restSeekBar = view.findViewById(R.id.restSeekBar);
        workoutTimeTextView = view.findViewById(R.id.workoutTimeTextView);
        restTimeTextView = view.findViewById(R.id.restTimeTextView);
        currentTime = view.findViewById(R.id.currentTime);
        currentTime.setText(formatTime(workoutDurationInMillis/1000));
        workoutProgressBar = view.findViewById(R.id.workoutProgressBar);
        workoutProgressBar.setMax(100); // Setzen Sie den maximalen Wert
        workoutProgressBar.setBackgroundColor(0x22000000); // Setzen Sie die Hintergrundfarbe
        workoutProgressBar.setProgress(100);

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
}
