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

    private Fragment currentFragment;
    private StatisticsFragment statisticsFragment;
    private TimerFragment timerFragment;
    private RunningWorkoutFragment runningWorkoutFragment;
    private ExercisesFragment exercisesFragment;
    private WorkoutsFragment workoutsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // Initialisiere die Instanzvariablen für die Fragmente
        statisticsFragment = new StatisticsFragment();
        timerFragment = new TimerFragment();
        runningWorkoutFragment = new RunningWorkoutFragment();
        exercisesFragment = new ExercisesFragment();
        workoutsFragment = new WorkoutsFragment();

        // Erstelle die Liste von Fragmenten
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(statisticsFragment);
        fragments.add(timerFragment);
        fragments.add(runningWorkoutFragment);
        fragments.add(exercisesFragment);
        fragments.add(workoutsFragment);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this, fragments);
        viewPager2.setAdapter(pagerAdapter);

        // Set up the OnPageChangeCallback to detect swiping
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);


                // Wechsle basierend auf der ausgewählten Position zu den entsprechenden Fragmenten
                switch (position) {
                    case 0:
                        switchToFragment(statisticsFragment);
                        Log.d("CHAD", "StatisticsFragment wird erzeugt ||| per swipe");
                        bottomNavigationView.setSelectedItemId(R.id.STATS_BUTTONMENU_BUTTON);
                        break;
                    case 1:
                        switchToFragment(timerFragment);
                        Log.d("CHAD", "TimerFragment wird erzeugt ||| per swipe");
                        bottomNavigationView.setSelectedItemId(R.id.TIMER_BUTTONMENU_BUTTON);
                        break;
                    case 2:
                        switchToFragment(runningWorkoutFragment);
                        Log.d("CHAD", "RunningWorkoutFragment wird erzeugt ||| per swipe");
                        bottomNavigationView.setSelectedItemId(R.id.START_BUTTONMENU_BUTTON);
                        break;
                    case 3:
                        switchToFragment(exercisesFragment);
                        Log.d("CHAD", "ExercisesFragment wird erzeugt ||| per swipe");
                        bottomNavigationView.setSelectedItemId(R.id.EXERCISES_BUTTONMENU_BUTTON);
                        break;
                    case 4:
                        switchToFragment(workoutsFragment);
                        Log.d("CHAD", "WorkoutsFragment wird erzeugt ||| per swipe");
                        bottomNavigationView.setSelectedItemId(R.id.WORKOUT_BUTTONMENU_BUTTON);
                        break;
                }
            }
        });

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
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
                if (!isCurrentFragment(statisticsFragment)) {
                    switchToFragment(statisticsFragment);
                    Log.d("CHAD", "showStatisticsFragment(): StatisticsFragment wird erzeugt ||| per Bottommenü-Klick");
                }
                return true;
            } else if (itemId == R.id.TIMER_BUTTONMENU_BUTTON) {
                if (!isCurrentFragment(timerFragment)) {
                    switchToFragment(timerFragment);
                    Log.d("CHAD", "showTimerFragment(): TimerFragment wird erzeugt ||| per Bottommenü-Klick");
                }
                return true;
            } else if (itemId == R.id.START_BUTTONMENU_BUTTON) {
                if (!isCurrentFragment(runningWorkoutFragment)) {
                    switchToFragment(runningWorkoutFragment);
                    Log.d("CHAD", "RunningWorkoutFragment wird erzeugt ||| per Bottommenü-Klick");
                }
                return true;
            } else if (itemId == R.id.EXERCISES_BUTTONMENU_BUTTON) {
                if (!isCurrentFragment(exercisesFragment)) {
                    switchToFragment(exercisesFragment);
                    Log.d("CHAD", "showExercisesFragment(): ExercisesFragment wird erzeugt ||| per Bottommenü-Klick");
                }
                return true;
            } else if (itemId == R.id.WORKOUT_BUTTONMENU_BUTTON) {
                if (!isCurrentFragment(workoutsFragment)) {
                    switchToFragment(workoutsFragment);
                    Log.d("CHAD", "showWorkoutsFragment(): WorkoutsFragment wird erzeugt ||| per Bottommenü-Klick");
                }
                return true;
            }
            return false;
        });
    }

    private void switchToFragment(Fragment fragment) {
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return; // Das gewählte Fragment ist bereits aktiv, daher nichts tun
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);

        transaction.commit();

        currentFragment = fragment;
    }

    private boolean isCurrentFragment(Fragment fragment) {
        return currentFragment != null && currentFragment.getClass().equals(fragment.getClass());
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
