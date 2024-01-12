/**
 * Die Klasse `MainActivity` dient als Hauptzugriffspunkt für die Giga Stats-Anwendung.
 * Sie umfasst die Einrichtung der Benutzeroberfläche, der Navigation und der Lebenszyklusereignisse.
 *
 * @author Jens Müller, Artur Gibert und Joshua Reumann
 * @version 1.0
 * @since 08.01.2024
 */

package com.example.giga_stats.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.giga_stats.database.manager.AppDatabase;
import com.example.giga_stats.adapter.AdapterSwipePager;
import com.example.giga_stats.R;
import com.example.giga_stats.fragments.FragmentExercises;
import com.example.giga_stats.fragments.FragmentRunningWorkout;
import com.example.giga_stats.fragments.FragmentStatistics;
import com.example.giga_stats.fragments.FragmentTimer;
import com.example.giga_stats.fragments.FragmentWorkouts;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


/**
 * Die Klasse `MainActivity` dient als Hauptzugriffspunkt für die Giga Stats-Anwendung.
 * Sie umfasst die Einrichtung der Benutzeroberfläche, der Navigation und der Lebenszyklusereignisse.
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2;
    private Fragment currentFragment;
    private FragmentStatistics fragmentStatistics;
    private FragmentTimer fragmentTimer;
    private FragmentRunningWorkout fragmentRunningWorkout;
    private FragmentExercises fragmentExercises;
    private FragmentWorkouts fragmentWorkouts;
    private AppDatabase appDatabase;
    static Context context;

    /**
     * Standardkonstruktor für die Klasse `MainActivity`.
     */
    public MainActivity() {
    }

    /**
     * Gibt die Instanz der `AppDatabase` für Datenbankoperationen zurück.
     *
     * @return Die Instanz der `AppDatabase`.
     */
    public static AppDatabase getAppDatabase() {
        return AppDatabase.getDatabase(context);
    }

    /**
     * Wird aufgerufen, wenn die Aktivität gestartet wird. Zuständig für die Initialisierung der
     * Benutzeroberfläche, der Datenbank und die Einrichtung der Navigation.
     *
     * @param savedInstanceState Wenn die Aktivität neu initialisiert wird, nachdem sie zuvor
     *                           geschlossen wurde, enthält dieses Bundle die Daten, die sie zuletzt
     *                           in {@link #onSaveInstanceState} bereitgestellt hat.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }

        context = getBaseContext();
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "GS.db").fallbackToDestructiveMigration().build();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager2 = findViewById(R.id.viewPager2);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        TabLayout tabLayout = findViewById(R.id.tabLayout);


        fragmentStatistics = new FragmentStatistics();
        fragmentStatistics.setAppDatabase(appDatabase);
        fragmentTimer = new FragmentTimer();
        fragmentRunningWorkout = new FragmentRunningWorkout();
        fragmentRunningWorkout.setAppDatabase(appDatabase);
        fragmentExercises = new FragmentExercises();
        fragmentExercises.setAppDatabase(appDatabase);
        fragmentWorkouts = new FragmentWorkouts();
        fragmentWorkouts.setAppDatabase(appDatabase);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragmentStatistics);
        fragments.add(fragmentTimer);
        fragments.add(fragmentRunningWorkout);
        fragments.add(fragmentExercises);
        fragments.add(fragmentWorkouts);

        AdapterSwipePager pagerAdapter = new AdapterSwipePager(this, fragments);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setCurrentItem(2);// Startfragment wird gesetzt

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch (position) {
                    case 0:
                        viewPager2.setCurrentItem(0, true);
                        Log.d("CHAD", "StatisticsFragment wird erzeugt ||| per swipe");
                        bottomNavigationView.setSelectedItemId(R.id.STATS_BUTTONMENU_BUTTON);
                        break;
                    case 1:
                        viewPager2.setCurrentItem(1, true);
                        Log.d("CHAD", "TimerFragment wird erzeugt ||| per swipe");
                        bottomNavigationView.setSelectedItemId(R.id.TIMER_BUTTONMENU_BUTTON);
                        break;
                    case 2:
                        viewPager2.setCurrentItem(2, true);
                        Log.d("CHAD", "RunningWorkoutFragment wird erzeugt ||| per swipe");
                        bottomNavigationView.setSelectedItemId(R.id.START_BUTTONMENU_BUTTON);
                        break;
                    case 3:
                        viewPager2.setCurrentItem(3, true);
                        Log.d("CHAD", "ExercisesFragment wird erzeugt ||| per swipe");
                        bottomNavigationView.setSelectedItemId(R.id.EXERCISES_BUTTONMENU_BUTTON);
                        break;
                    case 4:
                        viewPager2.setCurrentItem(4,  true);
                        Log.d("CHAD", "WorkoutsFragment wird erzeugt ||| per swipe");
                        bottomNavigationView.setSelectedItemId(R.id.WORKOUT_BUTTONMENU_BUTTON);
                        break;
                }
            }
        });

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText("Tab " + (position + 1))).attach();


        bottomNavigationView.setSelectedItemId(R.id.START_BUTTONMENU_BUTTON);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.STATS_BUTTONMENU_BUTTON) {
                if (isCurrentFragment(fragmentStatistics)) {
                    viewPager2.setCurrentItem(0, true);
                    Log.d("CHAD", "showStatisticsFragment(): StatisticsFragment wird erzeugt ||| per Bottommenü-Klick");
                }
                return true;
            } else if (itemId == R.id.TIMER_BUTTONMENU_BUTTON) {
                if (isCurrentFragment(fragmentTimer)) {
                    viewPager2.setCurrentItem(1, true);
                    Log.d("CHAD", "showTimerFragment(): TimerFragment wird erzeugt ||| per Bottommenü-Klick");
                }
                return true;
            } else if (itemId == R.id.START_BUTTONMENU_BUTTON) {
                if (isCurrentFragment(fragmentRunningWorkout)) {
                    viewPager2.setCurrentItem(2, true);
                    Log.d("CHAD", "RunningWorkoutFragment wird erzeugt ||| per Bottommenü-Klick");
                }
                return true;
            } else if (itemId == R.id.EXERCISES_BUTTONMENU_BUTTON) {
                if (isCurrentFragment(fragmentExercises)) {
                    viewPager2.setCurrentItem(3, true);
                    Log.d("CHAD", "showExercisesFragment(): ExercisesFragment wird erzeugt ||| per Bottommenü-Klick");
                }
                return true;
            } else if (itemId == R.id.WORKOUT_BUTTONMENU_BUTTON) {
                if (isCurrentFragment(fragmentWorkouts)) {
                    viewPager2.setCurrentItem(4, true);
                    Log.d("CHAD", "showWorkoutsFragment(): WorkoutsFragment wird erzeugt ||| per Bottommenü-Klick");
                }
                return true;
            }
            return false;
        });
    }

    /**
     * Überprüft, ob das übergebene Fragment das aktuelle Fragment ist.
     *
     * @param fragment Das zu überprüfende Fragment.
     * @return `true`, wenn das Fragment das aktuelle Fragment ist oder `currentFragment` `null` ist.
     *         Ansonsten `false`.
     */
    private boolean isCurrentFragment(Fragment fragment) {
        return currentFragment == null || !currentFragment.getClass().equals(fragment.getClass());
    }

    /**
     * Wird aufgerufen, wenn die Aktivität gestartet wird. Zeigt an, dass die Aktivität bald vollständig sichtbar wird.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CHAD", "LIFE MAIN: onStart(): Die Activity wird bald komplett sichtbar");
    }

    /**
     * Wird aufgerufen, wenn die Aktivität wieder aufgenommen wird. Zeigt an, dass die Aktivität komplett sichtbar und aktiv ist.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CHAD", "LIFE MAIN: onResume(): Die Activity ist komplett sichtbar und aktiv");
    }

    /**
     * Wird aufgerufen, wenn die Aktivität pausiert wird. Zeigt an, dass eine andere Aktivität jetzt im Fokus ist.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CHAD", "LIFE MAIN: onPause(): Eine andere Activity ist jetzt im Fokus");
    }

    /**
     * Wird aufgerufen, wenn die Aktivität gestoppt wird. Zeigt an, dass die Aktivität vollständig nicht mehr sichtbar ist.
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CHAD", "LIFE MAIN: onStop(): Activity ist vollständig nicht mehr sichtbar");
    }

    /**
     * Wird aufgerufen, wenn die Aktivität neu gestartet wird. Zeigt an, dass die Aktivität erneut gestartet wird.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("CHAD", "LIFE MAIN: onRestart(): Die Activity wird erneut gestartet");
    }

    /**
     * Wird aufgerufen, wenn die Aktivität zerstört wird. Zeigt an, dass dies der letzte Aufruf der Aktivität vor der Zerstörung ist.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CHAD", "LIFE MAIN: onDestroy(): Letzter Aufruf der Activity vor der Zerstörung ");
    }
}
