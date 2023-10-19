package com.example.giga_stats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.example.giga_stats.adapter.MyPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2;
    private int currentPage = 0; // Um den aktuellen Tab zu speichern

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: -Startbildschirm muss auf RunningWorkouts gesetzt werden
        //      -Bottommenü Klicks funktionieren nicht mehr

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Initialisieren Sie die Toolbar und setzen Sie sie als Aktionsleiste
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager2 = findViewById(R.id.viewPager2);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Erstellen Sie eine Instanz des FragmentManagers
        fragmentManager = getSupportFragmentManager();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new StatisticsFragment());
        fragments.add(new TimerFragment());
        fragments.add(new RunningWorkoutFragment());
        fragments.add(new ExercisesFragment());
        fragments.add(new WorkoutsFragment());

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this, fragments);
        viewPager2.setAdapter(pagerAdapter);


        // Set up the OnPageChangeCallback to detect swiping
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // This method is called when a new page is selected
                // You can handle different cases based on the selected page
                switch (position) {
                    case 0:
                        showStatisticsFragment();
                        bottomNavigationView.setSelectedItemId(R.id.STATS_BUTTONMENU_BUTTON);
                        break;
                    case 1:
                        showTimerFragment();
                        bottomNavigationView.setSelectedItemId(R.id.TIMER_BUTTONMENU_BUTTON);
                        break;
                    case 2:
                        showRunningWorkout();
                        bottomNavigationView.setSelectedItemId(R.id.START_BUTTONMENU_BUTTON);
                        break;
                    case 3:
                        showExercisesFragment();
                        bottomNavigationView.setSelectedItemId(R.id.EXERCISES_BUTTONMENU_BUTTON);
                        break;
                    case 4:
                        showWorkoutsFragment();
                        bottomNavigationView.setSelectedItemId(R.id.WORKOUT_BUTTONMENU_BUTTON);
                        break;
                }
                currentPage = position; // Update the current page
            }
        });

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            // Hier können Sie die Tab-Texte festlegen, z.B.:
            Log.e("CHAD", "Setting tab text for position: " + position);
            tab.setText("Tab " + (position + 1));
        }).attach();

        // Initialize the Bottom Navigation View
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Set the "Start" button as selected
        bottomNavigationView.setSelectedItemId(R.id.START_BUTTONMENU_BUTTON);

        // Konfigurieren Sie die Bottom Navigation View
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
            } else if (itemId == R.id.START_BUTTONMENU_BUTTON) {
                showRunningWorkout();
                return true;
            }
            return false;
        });
    }


    private void showStatisticsFragment() {
        Log.d("CHAD", "showStatisticsFragment(): StatisticsFragment wird erzeugt");
        StatisticsFragment statisticsFragment = new StatisticsFragment();


        //showFragment(statisticsFragment);
    }

    private void showRunningWorkout() {
        Log.d("CHAD", "showRunningWorkout(): RunningWorkoutFragment wird erzeugt");
        RunningWorkoutFragment runningWorkoutFragment = new RunningWorkoutFragment();
        //showFragment(runningWorkoutFragment);
    }

    private void showTimerFragment() {
        Log.d("CHAD", "showTimerFragment(): TimerFragment wird erzeugt");
        TimerFragment timerFragment = new TimerFragment();
        //showFragment(timerFragment);
    }

    private void showExercisesFragment() {
        Log.d("CHAD", "showExercisesFragment(): ExercisesFragment wird erzeugt");
        ExercisesFragment exercisesFragment = new ExercisesFragment();
        //showFragment(exercisesFragment);
    }

    private void showWorkoutsFragment() {
        Log.d("CHAD", "showWorkoutsFragment(): WorkoutsFragment wird erzeugt");
        WorkoutsFragment workoutsFragment = new WorkoutsFragment();
        //showFragment(workoutsFragment);
    }

    private void showFragment(Fragment fragment) {
        Log.d("CHAD", "Fragment wird ausgetauscht: " + fragment.getClass().getSimpleName());

        // Beginne eine Transaktion, um das Fragment hinzuzufügen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment); // "frameLayout" ist die ID des Containers, in dem das Fragment angezeigt werden soll
        transaction.addToBackStack(null); // Optional: Füge die Transaktion zum Back-Stack hinzu, um den Zurück-Knopf zu unterstützen
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CHAD", "LIFE MAIN: onStart(): Die Activity wird bald komplett sichtbar");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CHAD", "LIFE MAIN: onResume(): Die Activity ist komplett sichtbar und aktiv");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CHAD", "LIFE MAIN: onPause(): Eine andere Activity ist jetzt im Fokus");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CHAD", "LIFE MAIN: onStop(): Activity ist vollständig nicht mehr sichtbar");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("CHAD", "LIFE MAIN: onRestart(): Die Activity wird erneut gestartet");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CHAD", "LIFE MAIN: onDestroy(): Letzter Aufruf der Activity vor der Zerstörung ");
    }
}
