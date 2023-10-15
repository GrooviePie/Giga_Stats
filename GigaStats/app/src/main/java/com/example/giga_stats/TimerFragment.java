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

public class TimerFragment extends Fragment {

    //TODO: Hardcoded Texte bearbeiten

    public TimerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in TimerFragment.java aufgerufen");
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("CHAD", "onCreateOptionsMenu() in TimersFragment.java aufgerufen YUPDUBIDUP-YUPDUBIDUP");
        inflater.inflate(R.menu.menu_option_timer, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in TimerFragment.java aufgerufen");
        if (itemId == R.id.option_menu_play_timer) {
            // TODO: Aktion für "Play" ausführen
            return true;
        } else if (itemId == R.id.option_menu_pause_timer) {
            // TODO: Aktion für "Pause" ausführen
            return true;
        } else if (itemId == R.id.option_menu_stop_timer) {
            // TODO: Aktion für "Stop" ausführen
            return true;
        } else if (itemId == R.id.option_menu_restart_timer) {
            // TODO: Aktion für "Restart" ausführen
            return true;
        } else if (itemId == R.id.option_menu_tutorial_timer) {
            // TODO: Tutorial für TimerFragment erstellen
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
            toolbar.setTitle("Timer"); // Setzen Sie den Titel für die Toolbar
            toolbar.setTitleTextColor(Color.WHITE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }
}