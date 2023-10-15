package com.example.giga_stats;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class WorkoutsFragment extends Fragment {

    //TODO: LongClickEventHandler erstellen für
    // - Bearbeiten eines Workouts
    // - Löschen eines Workouts

    //TODO: Fenster für Hinzufügen eines Workouts

    //TODO: Fenster für Bearbeiten eines Workouts

    //TODO: Hardcoded Texte bearbeiten


    public WorkoutsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in WorkoutsFragment.java aufgerufen");
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("CHAD", "onCreateOptionsMenu() in WorkoutsFragment.java aufgerufen YEAHYEAHYEAH");
        inflater.inflate(R.menu.menu_option_workouts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in ExerciseFragment.java aufgerufen");
        if (itemId == R.id.option_menu_add_workouts) {
            //TODO: Aktion für "Hinzufügen" in der Toolbar innerhalb des Fragments WorkoutsFragment
            return true;
        } else if (itemId == R.id.option_menu_tutorial_workouts) {
            //TODO: Aktion für "Tutorial" in der Toolbar innerhalb des Fragments WorkoutsFragment
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            // Konfigurieren Sie die Toolbar nach Bedarf
            toolbar.setTitle("Workouts"); // Setzen Sie den Titel für die Toolbar
            toolbar.setTitleTextColor(Color.WHITE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workouts, container, false);
    }

}