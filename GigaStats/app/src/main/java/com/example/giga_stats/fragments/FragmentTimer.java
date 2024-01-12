package com.example.giga_stats.fragments;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
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
    private boolean isPlayPressed = false;
    private long workoutDurationInMillis = 60000; // Workout-Dauer in Millisekunden (hier 60 Sekunden)
    private long workoutTemp = workoutDurationInMillis; //Dient als temporäre Variable um Änderungen während der Laufzeit des Timers zu speichern
    private long restDurationInMillis = 20000;    // Pause-Dauer in Millisekunden (hier 30 Sekunden)
    private long restTemp = restDurationInMillis; //Dient als temporäre Variable um Änderungen während der Laufzeit des Timers zu speichern
    private long remainingWorkoutTime = 0;
    private long remainingRestTime = 0;
    AnimatedVectorDrawable avd2;


    /**
     * Leerer Standardkonstruktor für das FragmentTimer.
     */
    public FragmentTimer() {
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
        setHasOptionsMenu(true);
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
        currentTime.setText(formatTime(workoutDurationInMillis / 1000));
        workoutProgressBar = view.findViewById(R.id.workoutProgressBar);
        workoutProgressBar.setMax(100);
        workoutProgressBar.setBackgroundColor(getResources().getColor(R.color.timerBackground));
        workoutProgressBar.setProgress(100);
        ImageButton playButton = view.findViewById(R.id.play);
        ImageButton stopButton = view.findViewById(R.id.stop);

        playButton.setOnClickListener(view1 -> {
            handlePlayButtonClick();

            if (!isPlayPressed) {
                playButton.setImageDrawable(getResources().getDrawable(R.drawable.avd_play_to_pause));
                Drawable drawable = playButton.getDrawable();
                avd2 = (AnimatedVectorDrawable) drawable;
                avd2.start();

            } else {
                playButton.setImageDrawable(getResources().getDrawable(R.drawable.avd_pause_to_play));
                Drawable drawable = playButton.getDrawable();
                avd2 = (AnimatedVectorDrawable) drawable;
                avd2.start();

            }

            isPlayPressed = !isPlayPressed;
        });

        stopButton.setOnClickListener(v -> {
            handleStopButtonClick();

            playButton.setImageDrawable(getResources().getDrawable(R.drawable.avd_pause_to_play));
            Drawable drawable = playButton.getDrawable();
            avd2 = (AnimatedVectorDrawable) drawable;
            avd2.start();
            if (isPlayPressed) {
                isPlayPressed = !isPlayPressed;
            }
        });


        workoutSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                workoutTimeTextView.setText("Übungszeit: " + formatTime(progress));
                workoutTemp = progress * 1000;
                if (!isRestPaused && !isWorkoutPaused && !isWorkoutRunning && !isRestRunning) {
                    currentTime.setText(formatTime(workoutDurationInMillis / 1000));
                    workoutDurationInMillis = progress * 1000;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        restSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                restTemp = progress * 1000;
                restTimeTextView.setText("Pausenzeit: " + formatTime(progress));
                if (!isRestPaused && !isWorkoutPaused && !isWorkoutRunning && !isRestRunning) {
                    restDurationInMillis = progress * 1000;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
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
    }

    /**
     * Wird aufgerufen, wenn das Fragment in den Hintergrund wechselt.
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d("CHAD", "LIFE TIMER: onPause(): Das Fragment wechselt in den Hintergrund");
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
            toolbar.setTitle("Timer");
        }
    }



    //=====================================================DIALOGE==========================================================================

    /**
     * Öffnet einen Dialog mit einem Tutorial für den Timer.
     */
    private void openTutorialDialog() {
        String textContent = "Über die beiden Regler \"Übungszeit\" und \"Pausenzeit\" stellen sie den gewünschten Trainingszyklus ein. \nAnschließend können sie den Play-Button betätigen um den Timer zu starten.";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Tutorial Timer");
        builder.setCustomTitle(titleView);


        final TextView textView = new TextView(requireContext());
        textView.setText(textContent);
        textView.setPadding(16,16,16,16);

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
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
        currentTime.setTextColor(getResources().getColor(R.color.pastelGreen));

        if (isWorkoutRunning) {
            return;
        }

        if (!isWorkoutPaused && !isRestPaused) {
            isWorkoutRunning = true;
            workoutTimer = new CountDownTimer(initialTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;
                    currentTime.setText(formatTime(seconds));
                    remainingWorkoutTime = millisUntilFinished;
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
        } else if (isWorkoutPaused && !isRestPaused) {
            isWorkoutRunning = true;
            workoutTimer = new CountDownTimer(remainingWorkoutTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;
                    currentTime.setText(formatTime(seconds));
                    remainingWorkoutTime = millisUntilFinished;
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
        } else if (isRestPaused) {
            startRestTimer();
        }
    }


    /**
     * Aktualisiert die ProgressBar basierend auf dem aktuellen Fortschritt.
     */
    private void updateProgressbar(){
        if(isWorkoutRunning) {
            int progress = (int) ((double) remainingWorkoutTime / workoutDurationInMillis * 100);

            workoutProgressBar.setColor(getResources().getColor(R.color.pastelGreen)); // Setzen Sie die Farbe der gefüllten Fläche auf Grün
            workoutProgressBar.setProgress(progress);
            Log.d("CHAD", "update Progressbar aufgerufen" + progress);
        } else if (isRestRunning) {
            int progress = (int) ((double) remainingRestTime / restDurationInMillis * 100);

            workoutProgressBar.setColor(getResources().getColor(R.color.softRed)); // Setzen Sie die Farbe der gefüllten Fläche auf Orange
            Log.d("CHAD", "Rest is running");
            workoutProgressBar.setProgress(progress);
        } else {
            workoutProgressBar.setColor(R.color.timerBackground);
            workoutProgressBar.setProgress(100);
        }

    }

    /**
     * Startet den Rest-Timer.
     */
    private void startRestTimer() {
        currentTime.setTextColor(getResources().getColor(R.color.restTimer));

        remainingWorkoutTime = workoutDurationInMillis;
        isRestRunning = true;
        if (!isRestPaused) {
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
                    startWorkoutTimer(remainingWorkoutTime);
                }
            }.start();
        } else if (isRestPaused) {
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
                    startWorkoutTimer(remainingWorkoutTime);
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
        } else if (isRestRunning) {
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
        currentTime.setText(formatTime(workoutDurationInMillis / 1000));
        updateProgressbar();
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