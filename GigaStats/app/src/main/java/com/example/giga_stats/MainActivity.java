package com.example.giga_stats;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Erstelle eine Instanz des FragmentManagers
        fragmentManager = getSupportFragmentManager();

        // Initialize the Bottom Navigation View
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Set the "Start" button as selected
        bottomNavigationView.setSelectedItemId(R.id.START_BUTTONMENU_BUTTON);

        // Zeige standardmäßig das StatisticsFragment an
        showRunningWorkout();

        // Konfiguriere die Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Verwende setOnItemSelectedListener anstelle von setOnNavigationItemSelectedListener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.STATS_BUTTONMENU_BUTTON) {
                showStatisticsFragment();
                return true;
            } else if (itemId == R.id.TIMER_BUTTONMENU_BUTTON) {
                showTimerFragment();
                return true;
            } else if (itemId == R.id.EXERCISES_BUTTONMENU_BUTTON) {
                showExercisesFragment();
                return true;
            } else if (itemId == R.id.WORKOUT_BUTTONMENU_BUTTON) {
                showWorkoutsFragment();
                return true;
            }
            else if (itemId == R.id.START_BUTTONMENU_BUTTON) {
                showRunningWorkout();
                return true;
            }
            return false;
        });
    }

    private void showFragment(Fragment fragment) {
        // Beginne eine Transaktion, um das Fragment hinzuzufügen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.FrameLayout, fragment); // "FrameLayout" ist die ID des Containers, in dem das Fragment angezeigt werden soll
        transaction.addToBackStack(null); // Optional: Füge die Transaktion zum Back-Stack hinzu, um den Zurück-Knopf zu unterstützen
        transaction.commit();
    }

    private void showStatisticsFragment() {
        StatisticsFragment statisticsFragment = new StatisticsFragment();
        showFragment(statisticsFragment);
    }

    private void showRunningWorkout(){
        RunningWorkoutFragment runningWorkoutFragment = new RunningWorkoutFragment();
        showFragment(runningWorkoutFragment);
    }

    private void showTimerFragment() {
        TimerFragment timerFragment = new TimerFragment();
        showFragment(timerFragment);
    }

    private void showExercisesFragment() {
        ExercisesFragment exercisesFragment = new ExercisesFragment();
        showFragment(exercisesFragment);
    }

    private void showWorkoutsFragment() {
        WorkoutsFragment workoutsFragment = new WorkoutsFragment();
        showFragment(workoutsFragment);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("CHAD", "onStart(): Die Activity wird bald komplett sichtbar");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("CHAD", "onResume(): Die Activity ist komplett sichtbar und aktiv");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("CHAD", "onPause(): Eine andere Activity ist jetzt im Fokus");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("CHAD", "onStop(): Activity ist vollständig nicht mehr sichtbar");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("CHAD", "onRestart(): Die Activity wird erneut gestartet");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("CHAD", "onDestroy(): Letzter Aufruf der Activity vor der Zerstörung ");
    }
}
