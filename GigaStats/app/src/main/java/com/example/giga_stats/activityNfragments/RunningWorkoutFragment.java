package com.example.giga_stats.activityNfragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.giga_stats.DB.MANAGER.AppDatabase;
import com.example.giga_stats.R;

public class RunningWorkoutFragment extends Fragment {

    //TODO: LongClickEventHandler für und "Löschen eines Workoutsmuster aus der Ansicht" einbauen

    //TODO: Hardcoded Texte bearbeiten

    AppDatabase appDatabase;

    public RunningWorkoutFragment() {
        // Required empty public constructor
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in RunningWorkoutsFragment.java aufgerufen");
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("CHAD", "onCreateOptionsMenu() in RunningWorkoutsFragment.java aufgerufen UTZUTZUTZUTZ");
        inflater.inflate(R.menu.menu_option_runningworkouts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in RunnningWorkoutsFragment.java aufgerufen");

        if (itemId == R.id.option_menu_add_runningworkouts) {
            // TODO: Aktion für "Hinzufügen" ausführen
            return true;
        } else if (itemId == R.id.option_menu_tutorial_runningworkouts ) {
            // TODO: Tutorial für RunningWorkoutsFragment erstellen
            openTutorialDialog();
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {

            toolbar.setTitle("Workout beginnen"); // Setzen Sie den Titel für die Toolbar
            toolbar.setTitleTextColor(Color.WHITE);
        }
    }

    private void openTutorialDialog() {
        // Der Textinhalt, den du anzeigen möchtest
        //TODO: Tutorial schreiben für Fragment "Workout beginnen"
        String textContent = "Hier ist der Tutorial-Text, den du anzeigen möchtest.";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Tutorial");

        // Erstellen Sie ein TextView, um den Textinhalt anzuzeigen
        final TextView textView = new TextView(requireContext());
        textView.setText(textContent);

        // Fügen Sie das TextView zum Dialog hinzu
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(textView);
        builder.setView(layout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Schließen Sie den Dialog
                dialog.dismiss();
            }
        });

        builder.create().show();
    }


}